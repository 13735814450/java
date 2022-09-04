package com.jim.rpc.service.biz.impl;

import com.jim.rpc.sdk.api.xxApi;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class xxxServiceImpl implements xxApi {
    @Override
    public String test() {
        return "test";
    }
}
