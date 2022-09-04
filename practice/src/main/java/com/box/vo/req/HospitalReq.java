package com.box.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;

@Data
public class HospitalReq implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Integer id;

    private String name;

}
