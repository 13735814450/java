package com.jim.shirotest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jim.shirotest.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2020-11-19 14:33
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

     /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);
}
