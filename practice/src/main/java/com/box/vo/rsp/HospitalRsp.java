package com.box.vo.rsp;

import lombok.Data;
import java.io.Serializable;

@Data
public class HospitalRsp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

}
