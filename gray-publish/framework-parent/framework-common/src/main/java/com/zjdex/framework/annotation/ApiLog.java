package com.zjdex.framework.annotation;


import com.zjdex.framework.enums.ApiTypeEnum;

import java.lang.annotation.*;

/**
 * @author wxh
 * @date 2019/5/23 0023
 * @description
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {

    /**
     * 操作类型
     *
     * @return
     */
    ApiTypeEnum type() default ApiTypeEnum.OTHER;

    /**
     * 操作模块
     *
     * @return
     */
    public String module() default "";

    /**
     * spel表达式
     *
     * @return
     */
    String resource() default "";

    /**
     * 接口名称
     *
     * @return
     */
    String name() default "";

}
