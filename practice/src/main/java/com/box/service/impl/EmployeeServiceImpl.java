package com.box.service.impl;

import com.box.constant.ErrorCode;
import com.box.dao.EmployeeDao;
import com.box.dao.HospitalDao;
import com.box.service.EmployeeService;
import com.box.service.HospitalService;
import com.box.vo.base.BusinessException;
import com.box.vo.req.EmployeeReq;
import com.box.vo.req.HospitalReq;
import com.box.vo.rsp.EmployeeRsp;
import com.box.vo.rsp.HospitalRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private HospitalService hospitalService;

    @Override
    public int add(EmployeeReq req) {
        HospitalRsp hospitalRsp = hospitalService.getById(req.getHospitalId());
        if(hospitalRsp == null){
            throw new BusinessException(ErrorCode.HOSPITAL_NOT_EXIST);
        }
        employeeDao.add(req);
        return req.getId();
    }

    @Override
    public EmployeeRsp getById(Integer id) {
        return employeeDao.getById(id);
    }

    @Override
    public List<EmployeeRsp> listByHospitalId(Integer hospitalId) {
        return employeeDao.listByHospitalId(hospitalId);
    }
}
