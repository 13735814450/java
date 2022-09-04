package com.zjdex.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(
        prefix = "constants"
)
public class ParamsConfig {
    private Long writeMapExpire;
    private Long readMapExpire;
    private Integer writeMapSize;
    private Map<String, Object> threadPool;

    public ParamsConfig() {
    }

    public Long getWriteMapExpire() {
        return this.writeMapExpire;
    }

    public void setWriteMapExpire(Long writeMapExpire) {
        this.writeMapExpire = writeMapExpire;
    }

    public Long getReadMapExpire() {
        return this.readMapExpire;
    }

    public void setReadMapExpire(Long readMapExpire) {
        this.readMapExpire = readMapExpire;
    }

    public Integer getWriteMapSize() {
        return this.writeMapSize;
    }

    public void setWriteMapSize(Integer writeMapSize) {
        this.writeMapSize = writeMapSize;
    }

    public Map<String, Object> getThreadPool() {
        return this.threadPool;
    }

    public void setThreadPool(Map<String, Object> threadPool) {
        this.threadPool = threadPool;
    }
}

