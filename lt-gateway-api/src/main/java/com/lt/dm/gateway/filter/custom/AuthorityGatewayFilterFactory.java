package com.lt.dm.gateway.filter.custom;

import com.lt.dm.admin.sdk.req.MenuQuery;
import com.lt.dm.admin.sdk.resp.ResourceDTO;
import com.lt.dm.gateway.remote.manager.BackendAuthManager;
import com.lt.platform.common.model.enums.BusinessExceptionEnum;
import com.lt.platform.common.model.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 权限校验 filter
 *
 * @author longcw
 * @since 2021/4/25
 */
@Slf4j
public class AuthorityGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AuthorityGatewayFilterFactory.Config> {
    //[{"type":"Authority","strongAuth":"true","order":"-100","ignoreURL":["/**/doc.html","/**/favicon.ico","/**/webjars/js/*","/**/webjars/css/*","/**/swagger-resources","/**/v2/api-docs","/**/auth/**"]}]
    private final BackendAuthManager authManager;

    private static final String USER_ID_KEY = "userId";
    private static final String LOGIN_CHANNEL = "channel";
    private static final String REQUEST_METHOD_ALLOW_ALL = "ALL";
    private static final String STRONG_AUTH_KEY = "strongAuth";
    private static final String ORDER_KEY = "order";
    private static final String IGNORE_URL_KEY = "ignoreURL";
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public AuthorityGatewayFilterFactory(BackendAuthManager authManager) {
        super(Config.class);
        this.authManager = authManager;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(STRONG_AUTH_KEY, ORDER_KEY, IGNORE_URL_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter gatewayFilter = (exchange, chain) -> {
            log.info("AuthorityGateway start");
            ServerHttpRequest serverHttpRequest = exchange.getRequest();

            String path = serverHttpRequest.getPath().toString();

            if (!shouldFilter(config.getIgnoreURL(), path)) {
                return chain.filter(exchange);
            }

            String userId = serverHttpRequest.getHeaders().getFirst(USER_ID_KEY);

            if (StringUtils.isEmpty(userId)) {
                log.warn("userId 为空");
                return Mono.error(new BusinessException(50000003, "未登录"));
            }

            log.trace("**Authority** userId:{}", userId);

            Integer channel = Integer.parseInt(serverHttpRequest.getHeaders().getFirst(LOGIN_CHANNEL));

            List<ResourceDTO> allResourceList = authManager.selectAllResource(channel);
            if (CollectionUtils.isEmpty(allResourceList)) {
                return chain.filter(exchange);
            }

            //遍历所有资源
            for (ResourceDTO resourceDTO : allResourceList) {

                //如果当前请求在资源数据控制范围内
                if (pathMatcher.match(resourceDTO.getUrl(), path)) {
                    log.info("渠道{};资源地址{};匹配成功请求地址{}", channel, resourceDTO.getUrl(), path);
                    //校验用户权限
                    MenuQuery query = new MenuQuery();
                    query.setUserId(Long.parseLong(userId));
                    query.setChannel(channel);
                    List<ResourceDTO> userResourceList = authManager.selectUserResource(query);
                    if (!checkPermission(userResourceList, serverHttpRequest)) {
                        return Mono.error(new BusinessException(BusinessExceptionEnum.NO_OPERATE_PERMISSION,
                                BusinessExceptionEnum.NO_OPERATE_PERMISSION.message()));
                    }
                }
            }
            return chain.filter(exchange);
        };
        return new OrderedGatewayFilter(gatewayFilter, config.getOrder());
    }


    private boolean shouldFilter(List<String> ignoreUrl, String requestPath) {
        if (CollectionUtils.isEmpty(ignoreUrl)) {
            return true;
        }
        for (String pathPattern : ignoreUrl) {
            if (pathMatcher.match(pathPattern, requestPath)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPermission(List<ResourceDTO> resourceList, ServerHttpRequest serverHttpRequest) {
        if (CollectionUtils.isEmpty(resourceList)) {
            return false;
        }
        String method = serverHttpRequest.getMethodValue();
        String path = serverHttpRequest.getPath().toString();

        for (ResourceDTO resourceDTO : resourceList) {
            if (pathMatcher.match(resourceDTO.getUrl(), path)) {
                if (REQUEST_METHOD_ALLOW_ALL.equalsIgnoreCase(resourceDTO.getMethod())
                        || resourceDTO.getMethod().equalsIgnoreCase(method)) {
                    return true;
                } else {
                    log.info("请求方法匹配不成功:资源为:{},实际为{}", resourceDTO.getMethod(), method);
                }
            }
        }
        return false;
    }


    @Data
    public static class Config {

        // 强验证开关，默认强认证
        private boolean strongAuth = true;
        //filter的排序值
        private int order;
        //忽略的Url
        private List<String> ignoreURL;
    }
}
