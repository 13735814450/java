package com.jim.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class UserRsp {

    @JsonProperty
    @ApiModelProperty(position = 1)
    private String name;

    @JsonProperty
    @ApiModelProperty(position = 2)
    private Integer age;

    @JsonProperty
    @ApiModelProperty(name = "til",value = "ti",position = 3)
    private String title;

    @JsonProperty
    @ApiModelProperty(position = 4)
    private String phone;

}
