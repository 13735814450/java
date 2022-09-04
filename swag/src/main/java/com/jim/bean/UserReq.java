package com.jim.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserReq {

    @JsonProperty
    @ApiModelProperty(value="user name",position = 1)
    private String name;

    @JsonProperty
    @ApiModelProperty(position = 2)
    private Integer age;

    @JsonProperty
    @ApiModelProperty(value = "user title",position = 3)
    private String title;

    @JsonProperty(value = "email_id")
    @ApiModelProperty(value = "user email id",position = 4)
    private String emailId;
}
