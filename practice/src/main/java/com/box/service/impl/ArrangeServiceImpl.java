package com.box.service.impl;

import com.box.dao.ArrangeDao;
import com.box.service.ArrangeService;
import com.box.vo.req.ArrangeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArrangeServiceImpl implements ArrangeService {

    @Autowired
    private ArrangeDao arrangeDao;

    @Override
    public int add(Integer taskId, Integer employeeId) {
        ArrangeReq req = new ArrangeReq();
        req.setTaskId(taskId);
        req.setEmployeeId(employeeId);
        arrangeDao.add(req);
        return req.getId();
    }
}
