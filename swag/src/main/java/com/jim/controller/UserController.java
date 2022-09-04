package com.jim.controller;

import com.jim.bean.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("swag")
@Api(value = "user", tags = "用户相关接口", description = "提供用户相关的 Rest API")
//@ApiOperation(value = "user api", tags = "dd")
@ApiResponses({
        @ApiResponse(code = 4001, message = "4001"),
        @ApiResponse(code = 4002, message = "42"),
        @ApiResponse(code = 500, message = "500")
})
public class UserController {

    @GetMapping("/get/{id}")
    @ApiModelProperty(name = "get user", position = 1)
    @ApiResponses({
            @ApiResponse(code = 4001, message = "ok"),
            @ApiResponse(code = 4003, message = "go")
    })
    public BaseResponse<UserRsp> get(@PathVariable String id) {
        System.out.println(11);
        return getRsp();
    }

    @GetMapping("/list")
    @ApiModelProperty(name = "list user", position = 5)
    public BaseResponse<UserRsp> list(UserReq req) {
        System.out.println(11);
        return getRsp();
    }

    @GetMapping("/search")
    @ApiModelProperty(name = "search user")
    public BaseResponse<UserRsp> search(@RequestBody UserReq req) {
        System.out.println(11);
        return getRsp();
    }

    @PostMapping(value = "/add", name = "ad")
    @ApiModelProperty(name = "add user", value = "a", notes = "ad", position = 2)
    public BaseResponse<UserRsp> add(@RequestBody UserReq req) {
        System.out.println(11);
        return getRsp();
    }

    @PostMapping("/addPerson")
    @ApiModelProperty(name = "add user", position = 5)
    public BaseResponse<UserRsp> addPerson(@RequestBody PersonReq req) {
        System.out.println(11);
        return getRsp();
    }

    @PutMapping("/mod")
    @ApiModelProperty(name = "mod user", position = 3)
    public BaseResponse<Boolean> mod(@RequestBody UserReq req) {
        System.out.println(11);
        return getRsp();
    }


    @DeleteMapping("/del/{id}")
    @ApiModelProperty(name = "del user", position = 4)
    public BaseResponse<Boolean> delete(@PathVariable Integer id) {
        System.out.println(11);
        BaseResponse<Boolean> result = new BaseResponse();
        return result;
    }

    @GetMapping("/test")
    @ApiModelProperty(name = "test")
    public ErrorCode test(@RequestBody UserReq req) {
        System.out.println(11);
        ErrorCode errorCode = ErrorCode.OK;
        return errorCode;
    }

    @GetMapping("/getCode")
    @ApiModelProperty(name = "test")
    public BaseResponse<ErrorCode> getCode() {
        System.out.println(11);
//        ErrorCode errorCode = ErrorCode.OK;
        BaseResponse<ErrorCode> result = new BaseResponse();
        return result;
    }

    private BaseResponse getRsp() {
        UserRsp rsp = new UserRsp();
        rsp.setAge(11);
        rsp.setName("jim");
        rsp.setPhone("13");
        rsp.setTitle("dev");
        BaseResponse<UserRsp> result = new BaseResponse();
        result.setData(rsp);
        result.setCode(1);
        result.setMsg("ok");
        return result;
    }
}
