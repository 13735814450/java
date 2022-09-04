package com.box.service;

import com.box.vo.req.EmployeeReq;
import com.box.vo.rsp.EmployeeRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * func: employee manager
 */
public interface EmployeeService {
    int add(EmployeeReq req);

    EmployeeRsp getById(Integer id);

    List<EmployeeRsp> listByHospitalId(Integer hospitalId);
}
