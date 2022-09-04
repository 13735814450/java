package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.listener.ConfigListener;
import com.test.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2021-03-26 16:21
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test1")
    public Object test1(){
        String cmd = "Display interface transceiver verbose";
        String request = request(cmd);
        log.info(request);
        return  request;
    }


    @RequestMapping("/test2")
    public Object test2(){
        String cmd = "display cpu";
        String request = request(cmd);
        log.info(request);
        return  request;
    }

    @RequestMapping("/test3")
    public Object test3(){
        String cmd = "display alarm active";
        String request = request(cmd);
        log.info(request);
        return  request;
    }

    @RequestMapping("/test4")
    public Object test4(){
        String cmd = "display device";
        String request = request(cmd);
        log.info(request);
        return  request;
    }


    @RequestMapping("/test5")
    public Object test5(){
        String cmd = "display dfs-group 1 m-lag";
        String request = request(cmd);
        log.info(request);
        return  request;
    }

    @RequestMapping("/test6")
    public Object test6(){
        String cmd = "display dfs-group 1 m-lag brief";
        String request = request(cmd);
        log.info(request);
        return  request;
    }

    @RequestMapping("/test7")
    public Object test7(){
        String cmd = "display stp brief";
        String request = request(cmd);
        log.info(request);
        return  request;
    }

    @RequestMapping("/test8")
    public Object test8(){
        String cmd = "display ospfv3 peer";
        String request = request(cmd);
        log.info(request);
        return  request;
    }


    /**
     * 交换机设备 2409:8086:8511:0080::21 请求
     * @param cmd
     * @return
     */
    @SuppressWarnings("all")
    private String request(String cmd){
        String token = ConfigListener.getConf().get("token").toString();
        String postIp = "http://10.76.148.240:8002";
        String ip = "2409:8086:8511:0080::21";
        String url = postIp + "/cmd/v1/exec/"+ip;
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Auth-Token", token);

        //请求体
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("Device_IP", ip);
        param.put("isTOR", true);
        param.put("cmd", cmd);
        param.put("is_continue", false);
        param.put("pattern_str", "");
        param.put("cmd_type", "cmd");
        JSONObject parameter = JSONUtils.obj2JsonObject(param);
        //请求体
        HttpEntity<String> formEntity = new HttpEntity<String>(parameter.toString(), headers);
        String jsonResult = restTemplate.postForObject(url, formEntity, String.class);
        log.info("请求结果："+jsonResult);
        return jsonResult;
    }

    /**
     * 测试x86主机请求
     * @return
     */
    @RequestMapping("/test")
    public Object test(){
        String token = ConfigListener.getConf().get("token").toString();
        String postIp = "http://10.76.148.240:8002";
        String ip = "188.103.185.35";
        String url = postIp + "/cmd/v1/exec/"+ip;
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Auth-Token", token);

        //请求体
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("Device_IP", ip);
        param.put("isTOR", false);
        param.put("cmd", "date");
        param.put("is_continue", false);
        param.put("pattern_str", "");
        param.put("cmd_type", "cmd");
        JSONObject parameter = JSONUtils.obj2JsonObject(param);
        log.info(parameter.toString());
        //请求体
        HttpEntity<String> formEntity = new HttpEntity<String>(parameter.toString(), headers);
        String jsonResult = restTemplate.postForObject(url, formEntity, String.class);
        log.info("请求结果："+jsonResult);
        return jsonResult;
    }

}