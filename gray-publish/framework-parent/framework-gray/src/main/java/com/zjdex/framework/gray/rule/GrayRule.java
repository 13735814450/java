package com.zjdex.framework.gray.rule;


import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.zjdex.framework.gray.bean.RoutePo;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedissonService;
import com.zjdex.framework.util.constant.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 根据微服务的metadata-map.gray控制灰度发布,
 * true : 灰度
 * false: 不灰度
 */
public class GrayRule extends AbstractLoadBalancerRule {

    @Autowired
    private RedissonService redisService;

    private List<RoutePo> route;

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String gray = request.getHeader("gray");
        String ip = request.getHeader("ip");

        List<Server> upList = lb.getReachableServers();
        if (!CollectionUtils.isEmpty(upList)) {
            List<Server> servers = new ArrayList<>();
            upList.forEach(s -> {
                if (s.isAlive()) {
                    if (!StringUtils.isEmpty(ip)) {
                        //根据ip进行过滤，优先于灰度
                        if (s.getHost().equals(ip)) {
                            servers.add(s);
                        }
                        return;
                    }

                }
            });
            //没有服务则取所有服务之一
            Server server;
            int count;
            if (servers.size() > 0) {
                count = new Random().nextInt(servers.size());
                server = servers.get(count);
            } else {
                count = new Random().nextInt(upList.size());
                server = upList.get(count);
            }
            return server;
        }
        return null;
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
//        return chooseByIp();
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

//    @PostConstruct
//    private List<RoutePo> locateRoutes(){
//        route = jdbcTemplate.query("select * from route",new BeanPropertyRowMapper<>(RoutePo.class));
//        return route;
//    }
//
    private Server chooseByIp(){
        Server server = null;
        if(CollectionUtils.isEmpty(route)){
            return server;
        }
        String ip = RequestHolder.getHeader("X-Real-IP");
        String ipForward = RequestHolder.getHeader("X-Forwarded-For");
//        redisService.get(ConstantUtil.GRAYIP,String.class);
//        redissonClient.getBucket(ConstantUtil.GRAYSERVICE);
        if(StringUtils.isEmpty(ip)){
            return server;
        }
        for (RoutePo item:route) {
            if(ip.equals(item.getClientIp())){
                server = new Server(item.getTargetIp(),item.getTargetPort());
                return  server;
            }
        }
        return server;
    }
}