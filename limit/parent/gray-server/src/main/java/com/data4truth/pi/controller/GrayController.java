package com.data4truth.pi.controller;

import com.data4truth.pi.service.GrayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GrayController {

    @Autowired
    private GrayRouteService service;

    @GetMapping(value = "/refresh", name="刷新")
    public Object refresh(){
        return service.refresh();
    }

    @GetMapping(value = "/test", name="刷新")
    public Object test(){
        return service.test();
    }

    @GetMapping(value = "/getAllService", name = "获取所有灰度服务")
    public Object getAllService(){
        return  service.getAllService();
    }

    @GetMapping(value = "/getServiceFromRedis", name = "获取缓存中的灰度服务")
    public Object getServiceFromRedis(){
        return  service.getServiceFromRedis();
    }

    @GetMapping(value = "/clearFromRedis", name = "清除缓存中的路由")
    public Object clearFromRedis(){
        return  service.clearFromRedis();
    }
}
