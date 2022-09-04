package com.data4truth.pi.model;

/**
 * @author: null
 * @date: 2019-12-16 15:32:23
 * @description:
 */
public class GrayService {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 服务类型
     */
    private String type;

    /**
     * 服务ip
     */
    private String ip;

    /**
     * 客户端ip，用分号分割
     */
    private String clientip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 主键
     *
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 服务类型
     *
     * @return type 服务类型
     */
    public String getType() {
        return type;
    }

    /**
     * 服务类型
     *
     * @param type 服务类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 服务ip
     *
     * @return ip 服务ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 服务ip
     *
     * @param ip 服务ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 客户端ip，用分号分割
     *
     * @return clientIp 客户端ip，用分号分割
     */
    public String getClientip() {
        return clientip;
    }

    /**
     * 客户端ip，用分号分割
     *
     * @param clientip 客户端ip，用分号分割
     */
    public void setClientip(String clientip) {
        this.clientip = clientip == null ? null : clientip.trim();
    }

    /**
     * 端口
     *
     * @return port 端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 端口
     *
     * @param port 端口
     */
    public void setPort(Integer port) {
        this.port = port;
    }
}