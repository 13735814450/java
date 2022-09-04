package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2021-03-26 16:07
 */
@Configuration
public class ClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000 * 3);
        httpRequestFactory.setConnectTimeout(30 * 3000 * 2);
        httpRequestFactory.setReadTimeout(300 * 3000 * 3);
        return new RestTemplate(httpRequestFactory);
    }


    @Bean
    public ClientRequest clientRequest(){
        return new ClientRequest();
    }

}
