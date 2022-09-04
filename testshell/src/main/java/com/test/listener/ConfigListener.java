package com.test.listener;

import com.alibaba.fastjson.JSONObject;
import com.test.config.UserAuth;
import com.test.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2021-04-07 11:19
 */
@Slf4j
public class ConfigListener implements ServletContextListener {

    private static Map<String, String> conf = new HashMap<String, String>();

    public static Map<String, String> getConf(){
        return conf;
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("初始化数据");
        String token = authority();
        conf.put("token", token);
    }

    private String authority(){
        String postIp = "http://10.76.148.240:8002";
        String url = postIp+"/v1/api/login";
        UserAuth user = new UserAuth("zhongjianjian", "ChgPsw1stTime");
        JSONObject jsonObj = (JSONObject) JSONObject.toJSON(user);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //请求体
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        RestTemplate restTemplate = restTemplate();
        String jsonResult = restTemplate.postForObject(url, formEntity, String.class);
        log.info("认证结果："+jsonResult);
        String code = JSONUtils.getValue(jsonResult, "code");
        String data = JSONUtils.getValue(jsonResult, "token");
        if(code.equals("201")){
            log.info("message："+JSONUtils.getValue(jsonResult, "message"));
            log.info("message："+JSONUtils.getValue(jsonResult, "token"));
            return data;
        } else {
            log.info(data);
            return authority();
        }
    }

    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000 * 3);
        httpRequestFactory.setConnectTimeout(30 * 3000 * 2);
        httpRequestFactory.setReadTimeout(300 * 3000 * 3);
        return new RestTemplate(httpRequestFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("销毁数据");
        conf.clear();
    }
}
