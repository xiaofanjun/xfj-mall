package com.xfj.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @CLassNmae ServiceLog
 * @Description Service日志打印
 * <p>
 * 加入此注解打印入参，出参，有异常时的日志，并保存日志信息到数据库中
 * @Author ZQ
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLog {
    /**
     * @return java.lang.String
     * @Author ZQ
     * @Description 该方法的作用描述
     * @Date 2019/11/30 17:36
     * @Param []
     **/
    String value() default "";
}
