package com.data4truth.pi.filter;

import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentInterceptorHandler;
import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentRuleHandler;
import cn.yueshutong.springbootstartercurrentlimiting.properties.CurrentProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LimitFilter extends ZuulFilter {

    @Autowired(required = false)
    private DefaultLimitRule defaultLimitRule;

    @Autowired(required = false)
    private CustomLimitRule customLimitRule;

    @Autowired(required = false)
    private CurrentRuleHandler rule;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //1.获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //2.获取Request对象
        HttpServletRequest request = currentContext.getRequest();

        HttpServletResponse response = currentContext.getResponse();

        //是否自定义规则
        try{
            if (rule == null) {
                defaultLimitRule.preHandle(request,response,null);
            } else {
                customLimitRule.preHandle(request,response,null);
            }
        }catch (Exception e){
            throw new ZuulException(e, 500, e.getMessage());
        }

        return null;
    }
}
