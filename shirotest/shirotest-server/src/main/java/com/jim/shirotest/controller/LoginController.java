package com.jim.shirotest.controller;

import com.jim.shirotest.dto.UserDto;
import com.jim.shirotest.service.UserService;
import com.jim.shirotest.util.ShiroConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", name = "")
    public Object login(@RequestBody UserDto req) {
        log.info(req.toString());
        // 添加用户认证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(req.getName(), req.getPwd());
        // 进行验证，这里可以捕获异常，然后返回对应信息
        Subject user = SecurityUtils.getSubject();
        user.login(usernamePasswordToken);
        log.info(user.getSession().getId().toString());
        return "login ok!=" + user.getSession().getId().toString();
//        return userService.login(req);
    }

    @GetMapping(value = "/logout", name = "")
    public Object logout() {
         // 进行验证，这里可以捕获异常，然后返回对应信息
        SecurityUtils.getSubject().logout();
        return "loginout ok!";
//        return userService.login(req);
    }

    @PostMapping(value = "/add", name = "")
    @RequiresPermissions("login:add")
    public Object add(@RequestBody UserDto req) {
        log.info(req.toString());
        return "add";
    }

    @PostMapping(value = "/mod", name = "")
    @RequiresPermissions("login:mod")
    public Object mod(@RequestBody UserDto req) {
        log.info(req.toString());
        return "mod";
    }

//    @RequiresRoles("administrator")
    @RequiresPermissions("create")
    @GetMapping(value = "/create")
    public String create() {
        userService.create("dd");
        return "Create success!";
    }
}
