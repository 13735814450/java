package com.zjdex.framework.gray.config;

import com.netflix.loadbalancer.IRule;
import com.zjdex.framework.gray.rule.GrayRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrayConfig {
    @Bean
    public IRule grayRule(){
        return new GrayRule();
    }
}
