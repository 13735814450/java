package com.jim.shirotest.shiro;


import java.util.List;

public interface IConstantFactory {

	/**
	 * 根据用户id获取用户名称
	 *
	 * @author
	 * @Date 2017/5/9 23:41
	 */
	String getUserNameById(Integer userId);
	
	/**
	 * 根据用户id获取用户账号
	 *
	 * @author
	 * @date 2017年5月16日21:55:371
	 */
	String getUserAccountById(Integer userId);
	
	/**
	 * 通过角色ids获取角色名称
	 */
	String getRoleName(String roleIds);
	/**
	 * 通过角色id获取角色名称
	 */
	String getSingleRoleName(Integer roleId);
	
	/**
	 * 通过角色id获取角色英文名称
	 */
	String getSingleRoleTip(Integer roleId);


}