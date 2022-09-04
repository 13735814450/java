package com.box.dao;

import com.box.vo.req.TaskAddReq;
import com.box.vo.req.TaskReq;
import com.box.vo.rsp.TaskRsp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskDao {

    int add(TaskAddReq req);

    int mod(TaskReq req);

    int arrange(@Param("id") Integer id,@Param("employeeId") Integer employeeId);

    TaskRsp getById(@Param("id") Integer id);

    List<TaskRsp> listByEmployeeId(@Param("employeeId") Integer employeeId);

    List<TaskRsp> listByEmployeeIds(@Param("employeeIds") List<Integer> employeeIds);
}
