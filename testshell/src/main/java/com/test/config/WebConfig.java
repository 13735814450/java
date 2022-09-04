package com.test.config;

import com.test.listener.ConfigListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2021-04-07 14:07
 */
@Configuration
public class WebConfig {

    @Bean
    public ServletListenerRegistrationBean<ConfigListener> configListenerRegistration(){
        return new ServletListenerRegistrationBean<>(new ConfigListener());
    }
}