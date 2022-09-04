package com.zjdex.framework.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.zjdex.framework.model.SysConstant;
import com.zjdex.framework.service.SysConstantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SysConstantConfig {
    private static final Logger logger = LoggerFactory.getLogger(SysConstantConfig.class);
    @Autowired
    private SysConstantService sysConstantService;
    @Resource(
            name = "scheduledExecutorService"
    )
    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    private ParamsConfig paramsConfig;
    private LoadingCache<String, String> readWriteCacheMap;

    public SysConstantConfig() {
    }

    @PostConstruct
    private void init() {
        this.readWriteCacheMap = CacheBuilder.newBuilder().initialCapacity(this.paramsConfig.getWriteMapSize()).expireAfterWrite(this.paramsConfig.getWriteMapExpire(), TimeUnit.SECONDS).removalListener((notification) -> {
            logger.info("{} was removed, cause is {}", notification.getKey(), notification.getCause());
        }).build(new CacheLoader<String, String>() {
            public String load(String key) throws Exception {
                SysConstant sysConstant = SysConstantConfig.this.sysConstantService.getContent(key);
                return sysConstant != null ? sysConstant.getContent() : null;
            }
        });
        this.scheduledExecutorService.scheduleAtFixedRate(this.getReadOnlyMapUpdateTask(), this.paramsConfig.getReadMapExpire(), this.paramsConfig.getReadMapExpire(), TimeUnit.SECONDS);
    }

    private Runnable getReadOnlyMapUpdateTask() {
        return () -> {
            ConcurrentHashMap<String, String> readOnlyMap = SysConstantConfig.SingletonEnum.INSTANCE.getReadMap();
            KeySetView<String, String> keySetView = readOnlyMap.keySet();
            Iterator iterator = keySetView.iterator();

            while(iterator.hasNext()) {
                try {
                    String key = (String)iterator.next();
                    String readValue = (String)readOnlyMap.get(key);
                    String writeValue = (String)this.readWriteCacheMap.get(key);
                    if (!readValue.equals(writeValue)) {
                        readOnlyMap.put(key, writeValue);
                    }
                } catch (ExecutionException var7) {
                    var7.printStackTrace();
                }
            }

        };
    }

    public String getValue(String name) {
        String value = SysConstantConfig.SingletonEnum.INSTANCE.getContent(name);
        if (StringUtils.isEmpty(value)) {
            try {
                value = (String)this.readWriteCacheMap.get(name);
            } catch (ExecutionException var4) {
                var4.printStackTrace();
            }

            SysConstantConfig.SingletonEnum.INSTANCE.getReadMap().put(name, value);
        }

        return value;
    }

    private static enum SingletonEnum {
        INSTANCE;

        private ConcurrentHashMap<String, String> readOnlyMap = new ConcurrentHashMap();

        private SingletonEnum() {
        }

        public ConcurrentHashMap<String, String> getReadMap() {
            return this.readOnlyMap;
        }

        public String getContent(String name) {
            return (String)this.readOnlyMap.get(name);
        }
    }
}
