package com.lt.dm.gateway.config;


import com.lt.dm.gateway.filter.custom.AuthGatewayFilterFactory;
import com.lt.dm.gateway.filter.custom.AuthorityGatewayFilterFactory;
import com.lt.dm.gateway.remote.manager.BackendAuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义网关filter配置类
 *
 * @author laiyulong
 * @since 2021/1/15
 */
@Configuration(proxyBeanMethods = false)
public class GatewayFilterConfig {

    @Bean
    public AuthGatewayFilterFactory authGatewayFilterFactory(BackendAuthManager authManager) {
        return new AuthGatewayFilterFactory(authManager);
    }

    @Bean
    public AuthorityGatewayFilterFactory authorityGatewayFilterFactory(BackendAuthManager authManager) {
        return new AuthorityGatewayFilterFactory(authManager);
    }

}
