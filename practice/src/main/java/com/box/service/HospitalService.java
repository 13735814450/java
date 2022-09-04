package com.box.service;

import com.box.vo.req.HospitalReq;
import com.box.vo.rsp.HospitalRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * func: hospital manager
 */
public interface HospitalService {
    int add(HospitalReq req);

    HospitalRsp getById(Integer id);
}
