package com.data4truth.pi.limit;

import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentInterceptorHandler;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LimitInterceptorHandler implements CurrentInterceptorHandler {
    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "90000");
        writer.print(JSON.toJSONString(map));
        writer.close();
        response.flushBuffer();
    }

}