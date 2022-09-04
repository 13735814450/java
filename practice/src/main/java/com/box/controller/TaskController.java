package com.box.controller;

import com.box.service.TaskService;
import com.box.vo.base.R;
import com.box.vo.req.TaskEmployeeReq;
import com.box.vo.req.TaskAddReq;
import com.box.vo.req.TaskReq;
import com.box.vo.rsp.TaskRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public Object add(@RequestBody TaskAddReq req){
        int result = taskService.add(req);
        return R.ok(result);
    }

    @PutMapping("/mod/{id}")
    public Object mod(@PathVariable Integer id,@RequestBody TaskReq req){
        boolean result = taskService.mod(id, req);
        return R.ok(result);
    }

    @PutMapping("/arrange/{id}")
    public Object arrange(@PathVariable Integer id, @RequestBody TaskEmployeeReq req){
        boolean result = taskService.arrange(id, req.getEmployeeId());
        return R.ok(result);
    }

    @GetMapping("/listByEmployeeId")
    public Object listByEmployeeId(@RequestParam Integer id){
        List<TaskRsp> result = taskService.listByEmployeeId(id);
        return R.ok(result);
    }

    @GetMapping("/listByHospitalId")
    public Object listByHospitalId(@RequestParam Integer hospitalId){
        List<TaskRsp> result = taskService.listByHospitalId(hospitalId);
        return R.ok(result);
    }
}
