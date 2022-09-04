package com.jim.shirotest.shiro;


import com.jim.shirotest.mapper.UserMapper;
import com.jim.shirotest.model.User;
import com.jim.shirotest.util.SpringContextHolder;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Autowired
    private UserMapper userMapper;


    public static IShiro me(){
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public User user(String account) {
        User user = userMapper.getByAccount(account);
        // 账号不存在
        if(null == user){
            throw new CredentialsException();
        }
        // 账号被冻结
        if(user.getStatus() != 1){
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId());
        shiroUser.setAccount(user.getAccount());
        shiroUser.setDeptId(user.getDeptId());
                shiroUser.setName(user.getName());

//        Integer[] roleArray = Convert.toIntArray(user.getRoleId());
//        List<Integer> roleList = new ArrayList<Integer>();
//        List<String> roleNameList = new ArrayList<String>();
//        for (Integer roleId:roleArray) {
//            roleList.add(roleId);
//            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
//        }
//        shiroUser.setRoleList(roleList);
//        shiroUser.setRoleNames(roleNameList);
        return shiroUser;
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();

        // 密码加盐处理
        String source = user.getSalt();
//        ByteSource credentialsSalt = new ByteSource(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, null, realmName);
    }
}
