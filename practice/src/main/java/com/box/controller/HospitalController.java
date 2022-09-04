package com.box.controller;

import com.box.service.HospitalService;
import com.box.vo.base.R;
import com.box.vo.req.HospitalReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/add")
    public Object add(@RequestBody HospitalReq req) {
        int result = hospitalService.add(req);
        return R.ok(result);
    }
}
