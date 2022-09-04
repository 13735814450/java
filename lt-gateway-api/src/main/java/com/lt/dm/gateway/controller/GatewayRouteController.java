package com.lt.dm.gateway.controller;


import com.lt.dm.gateway.biz.GatewayService;
import com.lt.platform.common.model.base.BaseResult;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author laiyulong
 * @since 2021/1/14
 */
@Slf4j
@RestController
@RequestMapping("/gateway")
public class GatewayRouteController {

    private final GatewayService gatewayService;

    private final RouteDefinitionLocator routeDefinitionLocator;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public GatewayRouteController(GatewayService gatewayService, RouteDefinitionLocator routeDefinitionLocator) {
        this.gatewayService = gatewayService;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @GetMapping("/route/refresh")
    public Mono<BaseResult<Object>> refresh() {
        try {
            executor.execute(gatewayService::refresh);
        } catch (Exception e) {
            log.error("路由刷新失败，errorMsg = {}", e.getMessage());
            return Mono.just(BaseResult.fail("路由刷新失败"));
        }
        return Mono.just(BaseResult.ok());
    }

    @GetMapping("/route/list")
    public Flux<RouteDefinition> getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions();
    }
}
