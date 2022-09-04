package com.box.config;


import com.box.constant.ErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
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
                .apiInfo(apiInfo())
                .groupName("1.X")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.box.controller"))
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
        api.title("RESTful APIS")
                .description(sb.toString())
                .termsOfServiceUrl("http://www.163.com")//网站url等
                .version("1.0");
        return api.build();
    }
}

