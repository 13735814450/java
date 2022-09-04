package com.box.vo.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskRsp implements Serializable {

    private Integer id;

    private String title;

    private String description;

    private String priority;

    private String status;

//    @JsonProperty("employee_id")
    private Integer employeeId;
}
