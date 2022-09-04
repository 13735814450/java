package com.box.controller;

import com.box.service.EmployeeService;
import com.box.service.HospitalService;
import com.box.vo.base.R;
import com.box.vo.req.EmployeeReq;
import com.box.vo.req.HospitalReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public Object add(@RequestBody EmployeeReq req){
        int result = employeeService.add(req);
        return R.ok(result);
    }
}
