package com.forte.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 用来给那些没下载源码的人看注释用的
 * @author ForteScarlet <ForteScarlet@163.com>
 * @date 2020/8/1
 */
@Retention(RetentionPolicy.CLASS)
@Target({FIELD, TYPE, METHOD, PARAMETER, PACKAGE, CONSTRUCTOR})
public @interface Comment {
    String[] value() default {};
}
