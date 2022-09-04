package com.data4truth.pi.service;


import com.data4truth.pi.mapper.GrayServiceMapper;
import com.data4truth.pi.model.GrayService;
import com.zjdex.framework.bean.GrayServiceDto;
import com.zjdex.framework.service.RedissonService;
import com.zjdex.framework.util.GrayUtil;
import com.zjdex.framework.util.data.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrayRouteService {
    private static Logger logger = LoggerFactory.getLogger(GrayRouteService.class);

    @Autowired
    private GrayServiceMapper serviceMapper;

    @Autowired
    private RedissonService redissonService;

    public boolean refresh() {
        logger.info("begin---" + DateUtil.getDateString(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
        List<GrayService> services = getAllService();
        List<GrayServiceDto> dtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(services)) {
           services.forEach(item->{
               GrayServiceDto dto = new GrayServiceDto();
               dto.setIp(item.getIp());
               dto.setType(item.getType());
               dto.setPort(item.getPort());
               dto.setClientIp(item.getClientip());
               dtoList.add(dto);
           });
        }
        logger.info("redis.set---" + DateUtil.getDateString(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
        redissonService.set(GrayUtil.GRAYSERVICE,dtoList);
//        redissonService.set(ConstantUtil.GRAYSERVICE,dtoList,10, TimeUnit.SECONDS);
        logger.info("end---" + DateUtil.getDateString(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
        return true;
    }
    public boolean test() {
        redissonService.set("gray", "hi");
        return true;
    }


    public List<GrayService> getAllService() {
        List<GrayService> list = serviceMapper.selectByExample(null);
        return list;
    }


    public Object getServiceFromRedis() {
        return redissonService.get(GrayUtil.GRAYSERVICE, List.class);
    }

    public boolean clearFromRedis() {
        redissonService.delete(GrayUtil.GRAYSERVICE);
        return true;
    }
}
