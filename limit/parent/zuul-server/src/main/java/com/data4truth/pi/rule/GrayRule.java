package com.data4truth.pi.rule;

import com.alibaba.fastjson.JSONObject;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.zuul.context.RequestContext;
import com.zjdex.framework.bean.GrayServiceDto;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedissonService;
import com.zjdex.framework.util.GrayUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * wxh 2019.12.20
 * 根据灰度服务，开关，操作redis,缓存，路由表
 * true : 灰度
 * false: 不灰度
 */
public class GrayRule extends AbstractLoadBalancerRule {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonService redisService;

    @Value("${gray.enabled}")
    private String grayEnable;

    private List<GrayServiceDto> listService;

    private List<String> member;

    private RoundRobinRule roundRobinRule;

    public GrayRule() {
        super();
        roundRobinRule = new RoundRobinRule();
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        String serviceType = null;
        if (lb instanceof BaseLoadBalancer) {
            //获取服务类型
            serviceType = ((BaseLoadBalancer) lb).getName();
        }
        if(StringUtils.isEmpty(serviceType)){
            return null;
        }
        logger.info("key=" + key + "  serviceType:" + serviceType);
        List<Server> upList = lb.getReachableServers();
        if (!CollectionUtils.isEmpty(upList)) {
            List<Server> servers = new ArrayList<>();
            //获取可用服务
            upList.forEach(s -> {
                if (s.isAlive()) {
                    servers.add(s);
                    getServerName(s);
                }
            });
            Server server = null;
            logger.info("grayConfig.getGrayEnable()=" + this.grayEnable);
            if ("true".equalsIgnoreCase(this.grayEnable)) {
                //从redis刷新数据
                refresh();
                //获取灰度服务
                List<Server> serverList = unionServer(serviceType, servers);
                if (!CollectionUtils.isEmpty(serverList)) {
                    //随机取服务
                    server = getRandomServer(serverList);
                    logger.info("gray server：" + server);
                }else{
                    server = getRandomServer(getNormalServer(serviceType,servers));
                    logger.info("normal server：" + server);
                }
                return server;
            }
            //灰度关闭，则请求正常服务
            roundRobinRule.setLoadBalancer(lb);
            server = roundRobinRule.choose(key);
            logger.info("no gray server：" + server);
            return server;
        }
        return null;
    }

    @Override
    public Server choose(Object key) {
        Server server = choose(getLoadBalancer(), key);
        return server;
    }


    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    /**
     * 取服务名
     */
    private String getServerName(Server server) {
        String value = server.getMetaInfo().getAppName();
        logger.info(value);
        return value;
    }

    /**
     * 随机获取服务
     *
     * @param servers
     * @return
     */
    private Server getRandomServer(List<Server> servers) {
        int count = new Random().nextInt(servers.size());
        Server server = servers.get(count);
        return server;
    }

    /**
     * 合并灰度服务，可调用服务
     * @param serviceType
     * @param servers
     * @return
     */
    private List<Server> unionServer(String serviceType, List<Server> servers) {
        logger.info("serviceType:" + serviceType + "  servers：" + servers);
        List<Server> result = new ArrayList<>();
        //获取允许灰度的IP
        List<GrayServiceDto> grayService = getGrayService(serviceType);
        if (!CollectionUtils.isEmpty(grayService)) {
            grayService.forEach(item -> {
                servers.forEach(server -> {
                    if (item.getIp().equals(server.getHost()) && item.getPort() == server.getPort()) {
                        result.add(server);
                    }
                });
            });
        }
        logger.info("unionServer：" + result);
        return result;
    }

    /**
     * 从所有服务中剔除灰度服务
     * @param serviceType
     * @param servers
     * @return
     */
    private List<Server> getNormalServer(String serviceType, List<Server> servers) {
        logger.info("serviceType:" + serviceType + "  servers：" + servers);
        List<Server> result = new ArrayList<>();
        //获取允许灰度的IP
        if (!CollectionUtils.isEmpty(listService)) {
            List<GrayServiceDto> grayService = listService.stream().filter(item -> serviceType.equalsIgnoreCase(item.getType())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(grayService)) {
                return servers;
            }else{
                grayService.forEach(item -> {
                    servers.forEach(server -> {
                        if (!item.getIp().equals(server.getHost()) || item.getPort()!= server.getPort()) {
                            result.add(server);
                        }
                    });
                });
            }
        }
        logger.info("getNormalServer：" + result);
        return result;
    }
    /**
     * 获取客户端IP
     *
     * @return
     */
    private Map<String, String> getClientIp() {
        Map<String, String> map = new HashMap<>();
        String ip = RequestHolder.getHeader(GrayUtil.X_REAL_IP);
        String ipForward = RequestHolder.getHeader(GrayUtil.X_FORWARDED_FOR);
        logger.info("X_REAL_IP：" + ip);
        logger.info("X_FORWARDED_FOR：" + ipForward);
        if (!StringUtils.isEmpty(ip)) {
            map.put(ip, ip);
        }

        if (!StringUtils.isEmpty(ipForward)) {
            String[] ips = ipForward.split(",");
            if (ArrayUtils.isNotEmpty(ips)) {
                map.putAll(Arrays.stream(ips).collect(Collectors.toMap(e -> e, e -> e)));
            }
        }
        logger.info("clientIp：" + map.toString());
        return map;
    }

    private List<GrayServiceDto> getGrayService(String serviceType) {
        List<GrayServiceDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(listService)) {
            //1:灰度用户，则直接取灰度服务
            //2：不是灰度用户，则按灰度IP取灰度服务
            if (checkUser()) {
                listService.stream().forEach(item -> {
                    //服务类型匹配
                    if (item.getType().equalsIgnoreCase(serviceType)) {
                        result.add(item);
                    }
                });
            } else {
                //获取客户端IP
                Map<String, String> clientIp = getClientIp();
                listService.stream().forEach(item -> {
                    //服务类型匹配
                    if (item.getType().equalsIgnoreCase(serviceType)) {
                        for (String ip : clientIp.keySet()) {
                            //客户端IP匹配
                            if (item.getClientIp().contains(ip)) {
                                result.add(item);
                            }
                        }
                    }
                });
            }
        }
        logger.info("result：" + result);
        return result;
    }

    /**
     * 获取灰度服务
     */
    public void refresh() {
        try {
            logger.info("refresh begin ");
            listService = redisService.get(GrayUtil.GRAYSERVICE, List.class);
            member = redisService.get(GrayUtil.MEMBER_ID, List.class);
            logger.info("listService：" + listService);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public boolean checkUser() {
        //zuul专用获取request
        HttpServletRequest request = getRequest();
        // 获取请求参数name
        try {
            // 请求方法
            String method = request.getMethod();
            logger.info(request.getParameter("memberID"));
            logger.info(String.format("%s >>> %s", method, request.getRequestURL().toString()));
            // 获取请求的输入流
            InputStream in = request.getInputStream();
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            // 如果body为空初始化为空json
            logger.info("body:" + body);
            if (StringUtils.isEmpty(body)) {
                return false;
            }
            // 转化成json
            JSONObject json = JSONObject.parseObject(body);
            String memberId = json.getString("memberID");
            if (!CollectionUtils.isEmpty(member)) {
                if (member.contains(memberId)) {
                    return true;
                }
            }
            logger.info("memberId: " + memberId);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 微服务使用
     */
//    private HttpServletRequest getRequest(){
//        HttpServletRequest request = RequestHolder.getRequest();
//        if(request instanceof  HttpCacheRequestFilter.MyHttpServletRequestWrapper){
//            logger.info("Myhttpservletrequestwrapper");
//        }
//        return request;
//    }

    /**
     * zuul专用
     * @return
     */
    private HttpServletRequest getRequest(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
//        if(request instanceof RequestFacade){
//            logger.info("RequestFacade");
//        }
        return request;
    }


}