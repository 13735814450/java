package com.jim.shirotest.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2020-11-19 10:03
 */
@Data
@TableName("sys_user")
public class User extends Model<User> {

    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 名字
     */
    private String name;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 保留字段
     */
    private Integer version;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
}