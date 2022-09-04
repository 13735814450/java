package com.box.service.impl;

import com.box.constant.ErrorCode;
import com.box.dao.TaskDao;
import com.box.service.ArrangeService;
import com.box.service.EmployeeService;
import com.box.service.TaskService;
import com.box.vo.base.BusinessException;
import com.box.vo.req.TaskAddReq;
import com.box.vo.req.TaskReq;
import com.box.vo.rsp.EmployeeRsp;
import com.box.vo.rsp.TaskRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ArrangeService arrangeService;

    @Override
    public int add(TaskAddReq req) {
        if(!checkEmployee(req.getEmployeeId())){
            throw new BusinessException(ErrorCode.EMPLOYEE_NOT_EXIST);
        }
        taskDao.add(req);
        //insert arrange history
        if(req.getEmployeeId() != null && req.getEmployeeId() > 0){
            arrangeService.add(req.getId(),req.getEmployeeId());
        }
        return req.getId();
    }

    @Override
    public boolean mod(Integer id, TaskReq req) {
        req.setId(id);
        taskDao.mod(req);
        return true;
    }

    @Override
    public boolean arrange(Integer id, Integer employeeId) {
        if(!checkEmployee(employeeId)){
            throw new BusinessException(ErrorCode.EMPLOYEE_NOT_EXIST);
        }
        taskDao.arrange(id, employeeId);
        //insert arrange history
        arrangeService.add(id,employeeId);
        return true;
    }

    @Override
    public TaskRsp getById(Integer id) {
        return taskDao.getById(id);
    }

    @Override
    public List<TaskRsp> listByEmployeeId(Integer employeeId) {
        return taskDao.listByEmployeeId(employeeId);
    }

    @Override
    public List<TaskRsp> listByHospitalId(Integer hospitalId) {
        List<EmployeeRsp> empList = employeeService.listByHospitalId(hospitalId);

        if(CollectionUtils.isEmpty(empList)){
            return new ArrayList<>();
        }else{
            List<Integer> ids = empList.stream().map(EmployeeRsp::getId).collect(Collectors.toList());
            return taskDao.listByEmployeeIds(ids);
        }
    }

    private boolean checkEmployee(Integer employeeId){
        EmployeeRsp employeeRsp = employeeService.getById(employeeId);
        if(employeeRsp == null){
            return false;
        }else{
            return true;
        }
    }
}
