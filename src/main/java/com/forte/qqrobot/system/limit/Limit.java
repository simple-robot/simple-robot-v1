/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Limit.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.system.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 标记一个监听函数为限流函数。
 *
 * 限流函数会限制监听函数在一段时间内的执行次数。
 *
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Limit {

    /**
     * 监听函数隔多久才能触发一次
     */
    long value();

    /**
     * 时间类型，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 如果可以区分账号，是否区分账号
     * 例如不同的人的触发时间不同
     * 默认 true
     */
    boolean code() default true;

    /**
     * 如果可以区分群号，是否区分群号
     * 默认 true
     */
    boolean group() default true;

    /**
     * 如果可以区分bot，是否区分bot
     * 默认 false
     */
    boolean bot() default false;

}
