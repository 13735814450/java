package com.data4truth.pi.task;

import com.data4truth.pi.service.GrayRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class GrayTask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Lazy
    private GrayRouteService grayRouteService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void refresh(){
        grayRouteService.getServiceFromRedis();
    }
}
