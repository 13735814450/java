package com.jim.shirotest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jim.shirotest.mapper.RoleMapper;
import com.jim.shirotest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Autowired
    private RoleMapper roleMapper;

    public String getName(Integer id){
        Role role = super.getById(id);
        if(role != null){
            return role.getTips();
        }
        return "";
    }

}
