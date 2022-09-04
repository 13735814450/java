package com.data4truth.pi.config;

import com.data4truth.pi.rule.GrayRule;
import com.netflix.loadbalancer.IRule;
import lombok.Data;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Data
//@RibbonClients(defaultConfiguration = GrayConfig.class)
public class GrayConfig {

//    @RibbonClientName
//    private String name = "client";

//    @Autowired
//    private PropertiesFactory propertiesFactory;

//    @Bean
//    public IClientConfig ribbonClientConfig() {
//        DefaultClientConfigImpl config = new DefaultClientConfigImpl();
//        config.loadProperties(name);
//        return config;
//    }

//    @Bean
//    public IRule grayRule(IClientConfig config) {
//        if (this.propertiesFactory.isSet(IRule.class, name)) {
//            return this.propertiesFactory.get(IRule.class, config, name);
//        }
//        GrayRule rule = new GrayRule();
//        return rule;
//    }

//    @Bean
//    public IRule grayRule() {
//        GrayRule rule = new GrayRule();
//        return rule;
//    }
}
