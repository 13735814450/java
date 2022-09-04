package com.box.dao;

import com.box.vo.req.EmployeeReq;
import com.box.vo.req.HospitalReq;
import com.box.vo.rsp.EmployeeRsp;
import com.box.vo.rsp.HospitalRsp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EmployeeDao {

    int add(EmployeeReq req);

    EmployeeRsp getById(@Param("id") Integer id);

    List<EmployeeRsp> listByHospitalId(@Param("hospitalId") Integer hospitalId);
}
