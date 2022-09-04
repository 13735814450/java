package com.box.service;

import com.box.vo.req.TaskAddReq;
import com.box.vo.req.TaskReq;
import com.box.vo.rsp.TaskRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * func: task manager
 */
public interface TaskService {

    int add(TaskAddReq req);

    boolean mod(Integer id, TaskReq req);

    boolean arrange(Integer id, Integer employeeId);

    TaskRsp getById(@Param("id") Integer id);

    List<TaskRsp> listByEmployeeId(Integer employeeId);

    List<TaskRsp> listByHospitalId(Integer hospitalId);
}
