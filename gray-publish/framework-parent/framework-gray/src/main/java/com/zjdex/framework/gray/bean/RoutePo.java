package com.zjdex.framework.gray.bean;

import lombok.Data;

@Data
public class RoutePo {

    private Integer id;

    private String clientIp;

    private String targetIp;

    private Integer targetPort;

    private String serviceType;
}
