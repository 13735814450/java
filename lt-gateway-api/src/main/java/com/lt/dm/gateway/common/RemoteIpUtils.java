package com.lt.dm.gateway.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;
import java.util.List;

import cn.hutool.core.collection.CollUtil;

public class RemoteIpUtils {

    public static String getRequestIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> realIps = headers.get("X-Forwarded-For");
        if (CollUtil.isNotEmpty(realIps)) {
            return realIps.get(0);
        }
        realIps = headers.get("X-Real-IP");
        if (CollUtil.isNotEmpty(realIps)) {
            return realIps.get(0);
        }
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getHostString();
        }
        return "";
    }
}
