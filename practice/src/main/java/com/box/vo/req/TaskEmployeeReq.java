package com.box.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskEmployeeReq implements Serializable {

    @JsonIgnore
    private Integer id;

    private Integer employeeId;
}
