package com.jim.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.jim.bean.ErrorCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;

import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;


//@Configuration
//@EnableSwagger2
//@EnableKnife4j
//@EnableOpenApi
public class SwagConfig {

//        @Bean
//    public Docket createRestApi(){
//        List<ResponseMessage> responseMessageList = new ArrayList();
//        Arrays.stream(ErrorCode.values()).forEach(errorEnums -> {
//            int code = errorEnums.httpStatus.value() + errorEnums.code;
//            responseMessageList.add(
//                    new ResponseMessageBuilder().code(errorEnums.code).message(errorEnums.msg).responseModel(
//                            new ModelRef(errorEnums.msg)).build()
//            );
//        });
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.jim.controller"))
//                .paths(PathSelectors.any())
//                .build();
//        //版本类型是swagger2
////        return new Docket(DocumentationType.OAS_30)
////                .globalResponseMessage(RequestMethod.GET, responseMessageList)
////                .globalResponseMessage(RequestMethod.POST, responseMessageList)
////                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
////                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
////                //通过调用自定义方法apiInfo，获得文档的主要信息
////                .useDefaultResponseMessages(false)
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.jim.controller"))
////                .paths(PathSelectors.any())
////                .build()
////                .enableUrlTemplating(false)
////                .forCodeGeneration(true);
////                .apiInfo(apiInfo())
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.jim.controller"))//扫描该包下面的API注解
////                .paths(PathSelectors.any())
////                .build();
////        Docket docker = new Docket(DocumentationType.SWAGGER_2);
////        docker.apiInfo(apiInfo())
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.jim.controller"))//扫描该包下面的API注解
////                .paths(PathSelectors.any());
////        docker.forCodeGeneration(true);
////        return docker;
//    }
    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        ApiInfoBuilder api = new ApiInfoBuilder();
        StringBuffer sb = new StringBuffer("HttpCode | code | msg\n");
        for (ErrorCode value : ErrorCode.values()) {
            sb.append(value.httpStatus.value()).append(" | ").append(value.code).append(" | ").append(value.msg).append("\n");
        }
        api.title("使用Swagger2 构建RESTful APIS") //接口管理文档首页显示
                .description(sb.toString())//API的描述
                .termsOfServiceUrl("http://www.163.com")//网站url等
                .version("1.0");
        return api.build();
    }
//    private ApiInfo apiInfo() {
//        StringBuffer sb = new StringBuffer("HttpCode | code | msg <br>");
//        for (ErrorCode value : ErrorCode.values()) {
//            sb.append(value.httpStatus.value()).append(" | ").append(value.code).append(" | ").append(value.msg).append("<br>");
//        }
////        System.out.println(sb);
//        return new ApiInfoBuilder()
//                .title("Swagger2接口文档")
//                .description(sb.toString())
////                .contact(new Contact("作者", "作者地址", "作者邮箱"))
//                .version("1.0")
//                .build();
//    }
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .forCodeGeneration(true).groupName("指定group的名称，groupName不能重复")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.jim.controller"))
//                //过滤生成链接
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }

    /**
     * the api info
     *
     * @return api info
     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .license("Apache License Version 2.0")
//                .title("blogspot")
//                .description("api docs")
//                .contact(new Contact("tangzedong",
//                        "https://www.cnblogs.com/HackerBlog/",
//                        "tangzedong.programmer@gmail.com"))
//                .version("1.0")
//                .build();
//    }
}
