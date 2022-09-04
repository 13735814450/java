package com.jim.shirotest;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@MapperScan("com.jim.shirotest.mapper")
public class App {

    public static void main(String[] args){
        SpringApplication.run(App.class);
        log.info("app started");
    }
}
