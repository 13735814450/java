package com.jim.shirotest.shiro;

import com.alibaba.fastjson.JSON;
import com.jim.shirotest.dto.BaseResponse;
import com.jim.shirotest.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenFilter extends AccessControlFilter {

//    private MessageSource messageSource;
////
//    public TokenFilter(MessageSource messageSource) {
//        this.messageSource = messageSource;
//    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        boolean authenticated = subject.isAuthenticated();
        log.info("TokenFilter: current User is authenticated flag: {}", authenticated);
        return authenticated;
        // 只有完成认证的用户才允许访问
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        /*
          当不允许访问时，通过response会写错误信息
         */
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        //设置Http响应头控制UTF-8
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        BaseResponse commonResult = ResponseUtil.error(Integer.valueOf(101), "invalid_token");
        response.getWriter().write(JSON.toJSONString(commonResult));
        return false;
    }

//    private String getLocaleMessage(String code, Object... objects) {
//        return messageSource.getMessage(code, objects, LocaleContextHolder.getLocale());
//    }
}