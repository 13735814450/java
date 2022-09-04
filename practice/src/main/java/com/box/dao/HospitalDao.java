package com.box.dao;

import com.box.vo.req.HospitalReq;
import com.box.vo.rsp.HospitalRsp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HospitalDao {

    int add(HospitalReq req);

    HospitalRsp getById(@Param("id") Integer id);
}
