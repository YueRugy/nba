package com.yue.annotation;

import java.lang.annotation.*;

/**
 * Created by yue on 2018/5/30
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLogAnnotation {
    String description() default "";
}
