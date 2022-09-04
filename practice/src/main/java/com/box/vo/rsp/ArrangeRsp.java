package com.box.vo.rsp;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArrangeRsp implements Serializable {

    private Integer id;

    private Integer taskId;

    private Integer employeeId;
}
