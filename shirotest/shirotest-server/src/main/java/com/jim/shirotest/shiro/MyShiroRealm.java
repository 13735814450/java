package com.jim.shirotest.shiro;

import com.jim.shirotest.model.Role;
import com.jim.shirotest.model.User;
import com.jim.shirotest.service.RoleService;
import com.jim.shirotest.service.UserService;
import com.jim.shirotest.util.ShiroConst;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.findByName(name);
        if (user == null) {
            // 这里返回后会报出对应异常
            return null;
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,
                    user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户名称
        User user = userService.findByName(name);
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String roleName = roleService.getName(user.getRoleId());
        List<String> role = new ArrayList();
        role.add(roleName);
        simpleAuthorizationInfo.addRoles(role);
        List<String> permission = new ArrayList();
        permission.add("login:add");
        simpleAuthorizationInfo.addStringPermissions(permission);
//        for (Role role : user.getRoles()) {
//            // 添加角色
//            for (Permission permission : role.getPermissions()) {
//                // 添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
//            }
//        }
        return simpleAuthorizationInfo;
    }
}
