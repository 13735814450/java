package com.data4truth.pi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lindj
 * @date 2019/6/13 0013
 * @description
 */
@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
@ComponentScan(value = {"com.zjdex", "com.data4truth"})
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
}