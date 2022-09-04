package com.data4truth.pi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.data4truth.pi.mapper")
@ComponentScan(value = {"com.zjdex", "com.data4truth"})
@EnableScheduling
@EnableFeignClients
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
