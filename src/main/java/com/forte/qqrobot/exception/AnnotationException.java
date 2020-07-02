/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AnnotationException.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解方面出现的异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AnnotationException extends RobotRuntimeException {

    //****************  不应当使用注解+why  ****************//

    public AnnotationException(Class clz, Class<? extends Annotation> annotationType, String why) {
        super("class", clz, annotationType,  why);
    }

    public AnnotationException(Class clz, Method method, Class<? extends Annotation> annotationType, String why) {
        super("method", clz, method, annotationType, why);
    }

    public AnnotationException() {
    }

    public AnnotationException(String message, Object... format) {
        super(message, format);
    }

    public AnnotationException(String message) {
        super(message);
    }

    public AnnotationException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public AnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationException(Throwable cause) {
        super(cause);
    }

    public AnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    public AnnotationException(int pointless, String message) {
        super(pointless, message);
    }

    public AnnotationException(int pointless, String message, Throwable cause) {
        super(pointless, message, cause);
    }

    public AnnotationException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
