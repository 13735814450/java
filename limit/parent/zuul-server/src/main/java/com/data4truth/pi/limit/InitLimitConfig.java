package com.data4truth.pi.limit;

import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentInterceptorHandler;
import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentRuleHandler;
import cn.yueshutong.springbootstartercurrentlimiting.properties.CurrentProperties;
import com.data4truth.pi.filter.CustomLimitRule;
import com.data4truth.pi.filter.DefaultLimitRule;
import com.data4truth.pi.filter.LimitFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitLimitConfig {
    @Bean
    @ConditionalOnProperty(prefix = "current.limiting", name = "enabled", havingValue = "true")
    public DefaultLimitRule defaultLimitRule(CurrentProperties properties, CurrentInterceptorHandler handler){
        return new DefaultLimitRule(properties,handler);
    }

//    @Bean
//    @ConditionalOnProperty(prefix = "current.limiting", name = "enabled", havingValue = "true")
//    public CustomLimitRule customLimitRule(CurrentProperties properties, CurrentInterceptorHandler handler, CurrentRuleHandler limiterRule){
//        return new CustomLimitRule(properties,handler,limiterRule);
//    }

    @Bean
    @ConditionalOnProperty(prefix = "current.limiting", name = "enabled", havingValue = "true")
    public ZuulFilter limitFilter(){
        return new LimitFilter();
    }

}
