package com.jim.shirotest.shiro;

import com.jim.shirotest.mapper.RoleMapper;
import com.jim.shirotest.mapper.UserMapper;
import com.jim.shirotest.model.Role;
import com.jim.shirotest.model.User;
import com.jim.shirotest.util.SpringContextHolder;
import com.jim.shirotest.util.ToolUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
	private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);



	public static ConstantFactory me(){
		return SpringContextHolder.getBean("constantFactory");
	}

	/**
	 * 根据用户id获取用户名称
	 */
	@Override
	public String getUserNameById(Integer userId) {
		User user = userMapper.selectById(userId);
		if(user!=null){
			return user.getName();
		} else {
			return "--";
		}
	}

	/**
	 * 根据用户id获取用户账号
	 */
	@Override
	public String getUserAccountById(Integer userId) {
		User user = userMapper.selectById(userId);
		if (user != null) {
			return user.getAccount();
		} else {
			return "--";
		}
	}

	/**
	 * 通过角色ids获取角色名称
	 */
	@Override
	public String getRoleName(String roleIds) {
//		Long[] roles = Convert.toLongArray(true, roleIds);
		Long[] roles = {3L,4L};

		StringBuilder sb = new StringBuilder();
		for (long role : roles) {
			Role roleObj = roleMapper.selectById(role);
			if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
				sb.append(roleObj.getName()).append(",");
			}
		}
//		return StrKit.removeSuffix(sb.toString(), ",");
		return sb.toString();
	}
	
	/**
	 * 通过角色id获取角色名称
	 */
	@Override
	public String getSingleRoleName(Integer roleId) {
		if(0 == roleId){
			return "--";
		}
		Role role = roleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(role) && ToolUtil.isNotEmpty(role.getName())) {
			return role.getName();
		}
		return "";
	}
	
	/**
	 * 通过角色id获取角色英文名称
	 */
	@Override
	public String getSingleRoleTip(Integer roleId) {
		if (0 == roleId) {
			return "--";
		}
		Role roleObj = roleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
			return roleObj.getTips();
		}
		return "";
	}
	


}