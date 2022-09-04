package com.jim.bean;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    OK(200,"ok","ok",HttpStatus.OK),
    on(4092,"on2","go",HttpStatus.CONFLICT),
    go1(4041,"not found","go",HttpStatus.NOT_FOUND),
    go2(4042,"not go","go",HttpStatus.NOT_FOUND),
    come(5001," server error ","come",HttpStatus.INTERNAL_SERVER_ERROR);

    public Integer code;
    public String msg;
    public String desc;
    public HttpStatus httpStatus;


    ErrorCode(Integer code, String msg, String desc, HttpStatus httpStatus){
        this.code = code;
        this.msg = msg;
        this.desc = desc;
        this.httpStatus = httpStatus;
    }

}
