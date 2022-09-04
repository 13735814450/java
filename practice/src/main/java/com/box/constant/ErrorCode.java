package com.box.constant;


import org.springframework.http.HttpStatus;

public enum ErrorCode {

    OK(200,"ok",HttpStatus.OK),
    HOSPITAL_NOT_EXIST(1000,"hospital not found", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPLOYEE_NOT_EXIST(1001,"employee not found", HttpStatus.INTERNAL_SERVER_ERROR),
    TASK_NOT_EXIST(1002,"task not found", HttpStatus.INTERNAL_SERVER_ERROR),
    PARAM_INVALID(1003,"param invalid", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(1009,"Internal Server Error ", HttpStatus.INTERNAL_SERVER_ERROR);

    public Integer code;
    public String msg;
    public HttpStatus httpStatus;


    ErrorCode(Integer code, String msg, HttpStatus httpStatus){
        this.code = code;
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

}
