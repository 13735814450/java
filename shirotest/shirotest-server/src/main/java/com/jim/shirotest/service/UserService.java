package com.jim.shirotest.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jim.shirotest.dto.UserDto;
import com.jim.shirotest.mapper.UserMapper;
import com.jim.shirotest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService  extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    public List<User> login(UserDto dto){
        List<User> users = userMapper.selectList(null);
        log.info(users.toString());
        return users;
    }

    public User findByName(String name){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    public User create(String name){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}
