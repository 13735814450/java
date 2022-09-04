package com.lt.dm.gateway.filter.global;

import com.lt.dm.gateway.common.RemoteIpUtils;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 全局日志记录filter
 *
 * @author laiyulong
 * @since 2021/1/15
 */
@Slf4j
@Component
public class LogGlobalFilter implements GlobalFilter, Ordered {

    private static final String START_TIME = "startTime";
    private static final String LOG_ID = "logId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uid = UUID.randomUUID().toString();

        String requestIp = RemoteIpUtils.getRequestIp(exchange.getRequest());
        String requestInfo = String.format("in:{%s} Path:{%s} Method:{%s} Host:{%s} Query:{%s} ip:{%s}",
                uid,
                exchange.getRequest().getURI().getPath(),
                exchange.getRequest().getMethod().name(),
                exchange.getRequest().getURI().getHost(),
                exchange.getRequest().getQueryParams(),
                requestIp);
        log.info(requestInfo);

        exchange.getRequest().mutate().header(LOG_ID, uid);
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        exchange.getAttributes().put(LOG_ID, uid);

        ServerHttpRequest request =
                exchange.getRequest()
                        .mutate()
                        .header("X-Real-IP", requestIp)
                        .build();
        return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            String logId = exchange.getAttribute(LOG_ID);
            if (startTime != null) {
                Long executeTime = (System.currentTimeMillis() - startTime);

                String requestEnd = String.format("out:{%s} Cost:{%s}ms status:{%s} Path:{%s} Method:{%s} Host:{%s} Query:{%s}",
                        logId,
                        executeTime,
                        exchange.getResponse().getStatusCode(),
                        exchange.getRequest().getURI().getPath(),
                        exchange.getRequest().getMethod().name(),
                        exchange.getRequest().getURI().getHost(),
                        exchange.getRequest().getQueryParams());
                log.info(requestEnd);
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
