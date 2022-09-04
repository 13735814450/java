package com.jim.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonReq {

    @JsonProperty
    @ApiModelProperty(value="user name")
    private String name;

    @JsonProperty
    @ApiModelProperty()
    private Integer age;

    @JsonProperty
    @ApiModelProperty(value = "user title")
    private String title;

    @JsonProperty(value = "email_id")
    @ApiModelProperty(value = "user email id")
    private String emailId;
}
