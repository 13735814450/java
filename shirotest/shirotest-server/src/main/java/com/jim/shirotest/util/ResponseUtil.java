package com.jim.shirotest.util;


import com.jim.shirotest.dto.BaseResponse;

public class ResponseUtil {
    public ResponseUtil() {
    }

    public static BaseResponse success(Object object) {
        return new BaseResponse(object);
    }

    public static BaseResponse success() {
        return success((Object)null);
    }

    public static BaseResponse error(Integer code, String message) {
        return new BaseResponse(code, message);
    }

    public static BaseResponse error(CodeEnum codeEnum) {
        return new BaseResponse(codeEnum);
    }

    public static BaseResponse error(CodeException codeException) {
        return error(codeException.getCode(), codeException.getMessage());
    }
}