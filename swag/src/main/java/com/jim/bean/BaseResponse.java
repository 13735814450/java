package com.jim.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseResponse<T> {

    @ApiModelProperty(value = "100")
    private Integer code;

    @ApiModelProperty(value = "success" )
    private String msg;

    private T data;
}
