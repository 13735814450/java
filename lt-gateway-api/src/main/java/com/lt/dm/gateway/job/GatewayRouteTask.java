package com.lt.dm.gateway.job;


import com.lt.dm.gateway.biz.GatewayService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author laiyulong
 * @since 2021/1/18
 */
@Slf4j
@Component
public class GatewayRouteTask {

    @Resource
    private GatewayService gatewayService;

    @Scheduled(fixedRate = 60 * 1_000)
    public void refreshRoutesTask() {
        log.info("定时刷新路由信息...");
        gatewayService.refresh();
    }

}
