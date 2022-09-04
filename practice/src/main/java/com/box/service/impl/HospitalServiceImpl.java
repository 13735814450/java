package com.box.service.impl;

import com.box.dao.HospitalDao;
import com.box.service.HospitalService;
import com.box.vo.req.HospitalReq;
import com.box.vo.rsp.HospitalRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalDao hospitalDao;

    @Override
    public int add(HospitalReq req) {
        hospitalDao.add(req);
        return req.getId();
    }

    @Override
    public HospitalRsp getById(Integer id) {
        return hospitalDao.getById(id);
    }
}
