package com.lt.dm.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * 跨域请求配置类
 *
 * @author laiyulong
 * @since 2021/1/22
 */
@Configuration(proxyBeanMethods = false)
public class CorsWebFilterConfig {

    @Bean
    @Order(0)
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setMaxAge(1800L);

        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
