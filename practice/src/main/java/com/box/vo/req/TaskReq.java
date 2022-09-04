package com.box.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskReq implements Serializable {

    @JsonIgnore
    private Integer id;

    private String title;

    private String description;

    private String priority;

    private String status;

}
