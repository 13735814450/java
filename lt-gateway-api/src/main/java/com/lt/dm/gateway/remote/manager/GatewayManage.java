package com.lt.dm.gateway.remote.manager;


import com.lt.dm.gateway.sdk.api.GatewayManageService;
import com.lt.dm.gateway.sdk.resp.ClientResp;
import com.lt.dm.gateway.sdk.resp.RouteDefinitionResp;
import com.lt.platform.common.utils.CommonUtils;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayManage {

    @DubboReference
    private GatewayManageService gatewayManageService;

    public List<ClientResp> getAllClients() {
        return CommonUtils.unWrapper(gatewayManageService.getAllClients());
    }

    public List<String> getRoutePermissionList(String clientId) {
        return CommonUtils.unWrapper(gatewayManageService.getRoutePermissionList(clientId));
    }

    public List<RouteDefinitionResp> getRouteDefinitions() {
        return CommonUtils.unWrapper(gatewayManageService.getRouteDefinitions());
    }
}
