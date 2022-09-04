package com.box.vo.base;

import com.box.constant.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BusinessException extends RuntimeException{

    private int code = ErrorCode.INTERNAL_ERROR.code;
    private String msg ="internal error";
    private HttpStatus httpStatus = HttpStatus.OK;

    public BusinessException(int code, String msg, HttpStatus httpStatus) {
        this.code = code;
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    public BusinessException(ErrorCode code) {
        super(code.msg);
        this.code = code.code;
        this.msg = code.msg;
        this.httpStatus = code.httpStatus;
    }
}
