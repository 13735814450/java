package com.box.vo.base;

import com.box.constant.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ApiModel("Response")
public class R<T> {

    @ApiModelProperty(value = "status code")
    private int code = 200;

    @ApiModelProperty(value = "message")
    private String message = "SUCCESS";

    @ApiModelProperty(value = "response data")
    private T data;

    public R(){
    }

    public R(T data){
        this.data = data;
    }

    public static <T> R<T> ok(){
        return new R<>();
    }

    public static <T> R<T> ok(T data){
        return new R<>(data);
    }

    public static <T> R<T> fromException(Exception ex){
        R<T> rsp = new R<>();
        if(ex instanceof BusinessException){
            BusinessException be = (BusinessException) ex;
            rsp.setCode(be.getCode());
            rsp.setMessage(be.getMessage());
        }else{
            rsp.setCode(ErrorCode.INTERNAL_ERROR.code);
            rsp.setMessage(ErrorCode.INTERNAL_ERROR.msg);
        }
        return rsp;
    }
}
