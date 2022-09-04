package com.jim.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import com.jim.bean.ErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
//@EnableKnife4j
@EnableSwagger2WebMvc
public class Swagger2 {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        List<ResponseMessage> responseMessageList = new ArrayList();
        Arrays.stream(ErrorCode.values()).forEach(errorEnums -> {
            int code = errorEnums.httpStatus.value() + errorEnums.code;
            responseMessageList.add(
                    new ResponseMessageBuilder().code(errorEnums.code).message(errorEnums.msg).responseModel(
                            new ModelRef(errorEnums.msg)).build()
            );
        });
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
//                        .description("# swagger-bootstrap-ui-demo RESTful APIs")
//                        .termsOfServiceUrl("http://www.xx.com/")
//                        .contact("xx@qq.com")
//                        .version("1.0")
//                        .build())
                .apiInfo(apiInfo())
                //分组名称
                .groupName("2.X版本")

                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.jim.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder api = new ApiInfoBuilder();
        StringBuffer sb = new StringBuffer("HttpCode | code | msg<br>\n");
        for (ErrorCode value : ErrorCode.values()) {
            sb.append(value.httpStatus.value()).append(" | ").append(value.code).append(" | ").append(value.msg).append("<br>\n");
        }
        api.title("使用Swagger2 构建RESTful APIS") //接口管理文档首页显示
                .description(sb.toString())//API的描述
                .termsOfServiceUrl("http://www.163.com")//网站url等
                .version("1.0");
        return api.build();
    }
}

