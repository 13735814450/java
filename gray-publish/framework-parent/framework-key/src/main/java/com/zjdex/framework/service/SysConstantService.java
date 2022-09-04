package com.zjdex.framework.service;

import com.zjdex.framework.mapper.SysConstantMapper;
import com.zjdex.framework.model.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConstantService {
    @Autowired
    private SysConstantMapper sysConstantMapper;

    public SysConstantService() {
    }

    public SysConstant getContent(String name) {
        return this.sysConstantMapper.selectByName(name);
    }
}
