package com.jim.shirotest.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShiroUser implements Serializable {

    public Integer id;
    public String account;
    public String name;
    public Integer deptId;
    public List<Integer> roleList;
    public String deptName;
    public List<String> roleNames;
}
