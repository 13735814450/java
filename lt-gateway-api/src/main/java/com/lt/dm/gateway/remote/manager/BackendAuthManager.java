package com.lt.dm.gateway.remote.manager;

import com.alibaba.fastjson.JSONObject;
import com.lt.dm.admin.sdk.api.BackendAuthApi;
import com.lt.dm.admin.sdk.req.MenuQuery;
import com.lt.dm.admin.sdk.resp.ResourceDTO;
import com.lt.platform.common.utils.CommonUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackendAuthManager {

    @DubboReference
    private BackendAuthApi authApi;

    public List<ResourceDTO> selectUserResource(MenuQuery query) {
        return CommonUtils.unWrapper(authApi.selectUserResource(query));
    }

    public List<ResourceDTO> selectAllResource(Integer channel) {
        return CommonUtils.unWrapper(authApi.listAllResource(channel));
    }

    public JSONObject verifyToken(String token, boolean refresh, Long tokenTimeOut) {
        return CommonUtils.unWrapper(authApi.verifyToken(token, refresh, tokenTimeOut));
    }
}
