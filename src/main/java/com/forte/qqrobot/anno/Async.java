/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Async.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标定当前监听函数为异步函数。
 * 异步函数的函数返回值、break注解等解析均会失效，默认认为其执行成功。
 *
 * 目前来讲，异步任务暂时无法捕获异常。因此请注意异常处理。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Async {
    /**
     * 异步函数执行成功的默认值。
     * 默认认为其执行成功。
     *
     * 此值会直接作为返回值返回，因此请不要标记{@link ListenBody}注解
     *
     */
    boolean success() default true;

}
