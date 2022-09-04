package com.lt.dm.gateway.biz;

import com.lt.dm.gateway.remote.manager.GatewayManage;
import com.lt.dm.gateway.sdk.resp.FilterDefinitionResp;
import com.lt.dm.gateway.sdk.resp.PredicateDefinitionResp;
import com.lt.dm.gateway.sdk.resp.RouteDefinitionResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author laiyulong
 * @since 2021/1/14
 */
@Slf4j
@Service
public class GatewayServiceImpl implements GatewayService {

    private final GatewayManage gatewayManage;

    private final RouteDefinitionRepository definitionWriter;

    private final ApplicationEventPublisher publisher;

    public GatewayServiceImpl(GatewayManage gatewayManage, RouteDefinitionRepository definitionWriter, ApplicationEventPublisher publisher) {
        this.gatewayManage = gatewayManage;
        this.definitionWriter = definitionWriter;
        this.publisher = publisher;
    }

    @Override
    @PostConstruct
    public synchronized void refresh() {
        log.info("start refresh routes...");
        try {
            List<RouteDefinition> routeDefinitions = getRouteDefinitions();

            List<String> routeIds = routeDefinitions.stream().map(RouteDefinition::getId).collect(Collectors.toList());

            //删除已经下线的路由定义
            List<String> deleteRouteIds = new ArrayList<>();
            definitionWriter.getRouteDefinitions()
                    .subscribe(routeDefinition -> {
                        if (!routeIds.contains(routeDefinition.getId())) {
                            deleteRouteIds.add(routeDefinition.getId());
                        }
                    });
            deleteRouteIds.forEach(deleteRouteId -> definitionWriter.delete(Mono.just(deleteRouteId)).subscribe());

            //更新所有路由信息
            routeDefinitions.forEach(routeDefinition -> definitionWriter.save(Mono.just(routeDefinition)).subscribe());

            publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            log.error("refresh routes error, errorMsg = {}", e.getMessage());
        }
    }

    private List<RouteDefinition> getRouteDefinitions() {
        //从manage拉取路由定义信息
        List<RouteDefinitionResp> routes = gatewayManage.getRouteDefinitions();
        //转换为RouteDefinition
        return routes.stream().map(routeDefinitionTo -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            routeDefinition.setId(routeDefinitionTo.getId().toString());
            routeDefinition.setOrder(routeDefinitionTo.getRouteOrder());
            routeDefinition.setUri(buildUri(routeDefinitionTo.getUrl()));
            //设置断言
            List<PredicateDefinitionResp> predicates = routeDefinitionTo.getPredicates();
            for (PredicateDefinitionResp predicate : predicates) {
                PredicateDefinition predicateDefinition = new PredicateDefinition();
                predicateDefinition.setName(predicate.getName());
                predicateDefinition.setArgs(predicate.getArgs());
                routeDefinition.getPredicates().add(predicateDefinition);
            }
            //设置过滤器
            if (!CollectionUtils.isEmpty(routeDefinitionTo.getFilters())) {
                List<FilterDefinitionResp> filters = routeDefinitionTo.getFilters();
                for (FilterDefinitionResp filter : filters) {
                    FilterDefinition filterDefinition = new FilterDefinition();
                    filterDefinition.setName(filter.getName());
                    filterDefinition.setArgs(filter.getArgs());
                    routeDefinition.getFilters().add(filterDefinition);
                }
            }

            //设置元数据
            if(!CollectionUtils.isEmpty(routeDefinitionTo.getMetadata())){
                routeDefinition.setMetadata(routeDefinitionTo.getMetadata());
            }
            return routeDefinition;
        }).collect(Collectors.toList());
    }

    private URI buildUri(String url) {
        return url.startsWith("http") ? UriComponentsBuilder.fromHttpUrl(url).build().toUri()
                : UriComponentsBuilder.fromUriString(url).build().toUri();
    }
}
