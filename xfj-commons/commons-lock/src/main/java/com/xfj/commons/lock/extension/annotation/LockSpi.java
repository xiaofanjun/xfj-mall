package com.xfj.commons.lock.extension.annotation;

import java.lang.annotation.*;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/24 19:21
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LockSpi {

    /**
     * default extension name
     */
    String value() default "";
}
