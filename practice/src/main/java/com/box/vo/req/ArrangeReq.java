package com.box.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArrangeReq implements Serializable {

    @JsonIgnore
    private Integer id;

    private Integer taskId;

    private Integer employeeId;
}
