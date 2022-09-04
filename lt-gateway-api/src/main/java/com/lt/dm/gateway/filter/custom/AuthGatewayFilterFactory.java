package com.lt.dm.gateway.filter.custom;

import com.alibaba.fastjson.JSONObject;
import com.lt.dm.admin.sdk.constants.enums.AdminExceptionCodeEnum;
import com.lt.dm.gateway.remote.manager.BackendAuthManager;
import com.lt.platform.common.constant.AuthConstants;
import com.lt.platform.common.model.exception.BusinessException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * token 认证filter
 *
 * @author laiyulong
 * @since 2021/1/15
 */
@Slf4j
public class AuthGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    private final BackendAuthManager authManager;

    private static final String TOKEN_HEADER_NAME_KEY = "tokenHeaderName";

    private static final String STRONG_AUTH_KEY = "strongAuth";
    private static final String ORDER_KEY = "order";
    private static final String IGNORE_URL_KEY = "ignoreURL";
    private static final String TOKEN_TIME_OUT_KEY = "tokenTimeOut";
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public AuthGatewayFilterFactory(BackendAuthManager authManager) {
        super(Config.class);
        this.authManager = authManager;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(TOKEN_HEADER_NAME_KEY, STRONG_AUTH_KEY, ORDER_KEY, IGNORE_URL_KEY, TOKEN_TIME_OUT_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter gatewayFilter = (exchange, chain) -> {
            log.info("AuthGateway start");
            ServerHttpRequest serverHttpRequest = exchange.getRequest();

            String path = serverHttpRequest.getPath().value();
            log.info(Arrays.toString(config.getIgnoreURL().toArray()));
            if (!shouldFilter(config.getIgnoreURL(), path)) {
                return chain.filter(exchange);
            }
            log.info("开始获取token信息");
            String token = getToken(config, exchange);

            if (StringUtils.isEmpty(token)) {
                log.warn("token为空");
                return Mono.error(new BusinessException(AdminExceptionCodeEnum.ADMIN_USER_NOT_LOGIN));
            }

            long timeout = 3600L;
            if (StringUtils.isNotEmpty(config.tokenTimeOut)) {
                try {
                    timeout = Long.parseLong(config.tokenTimeOut);
                } catch (NumberFormatException e) {
                    log.error("转换有效期异常", e);
                }
            }
            JSONObject userInfo = authManager.verifyToken(token, true, timeout);

            if (userInfo != null && !userInfo.isEmpty()) {

                ServerHttpRequest request =
                        exchange.getRequest()
                                .mutate()
                                .header("userName", urlEncode(userInfo.getString("userName")))
                                .header("orgName", urlEncode(userInfo.getString("orgName")))
                                .header("userId", userInfo.getLong("userId") == null ? null : userInfo.getLong("userId").toString())
                                .header("channel", userInfo.getInteger("channel") == null ? "901" : userInfo.getInteger("channel").toString())
                                .header("orgIds", userInfo.getJSONArray("orgIds") == null ? null : userInfo.getJSONArray("orgIds").toString())
                                .build();
                return chain.filter(exchange.mutate().request(request).build());
            }
            return Mono.error(new BusinessException(AdminExceptionCodeEnum.ADMIN_USER_NOT_LOGIN));
        };

        return new OrderedGatewayFilter(gatewayFilter, config.getOrder());
    }

    /**
     * 获取token，来源优先级:QueryParams > Headers > Cookie
     *
     * @return token
     */
    public String getToken(Config config, ServerWebExchange exchange) {

        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String token = serverHttpRequest.getQueryParams().getFirst(config.tokenHeaderName);

        if (StringUtils.isNotEmpty(token)) {
            return token;
        }

        token = serverHttpRequest.getHeaders().getFirst(config.tokenHeaderName);

        if (StringUtils.isNotEmpty(token)) {
            return token;
        }

        HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst(config.tokenHeaderName);

        if (tokenCookie != null) {
            token = tokenCookie.getValue();
        }

        if (StringUtils.isNotEmpty(token) && token.startsWith(AuthConstants.TOKEN_PREFIX)) {
            token = token.replace(AuthConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private boolean shouldFilter(List<String> ignoreUrl, String requestPath) {
        if (CollectionUtils.isEmpty(ignoreUrl)) {
            return true;
        }

        for (String pathPattern : ignoreUrl) {
            boolean result = pathMatcher.match(pathPattern, requestPath);
            if (result) {
                return false;
            }
        }
        return true;
    }

    @Data
    public static class Config {
        //携带token的请求头名称
        private String tokenHeaderName;
        // 强验证开关，默认强认证
        private boolean strongAuth = true;
        //filter的排序值
        private int order;
        //忽略的Url
        private List<String> ignoreURL;

        private String tokenTimeOut;
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(new AntPathMatcher().match("/**/auth/**", "/web/admin/auth/login"));
    }
}
