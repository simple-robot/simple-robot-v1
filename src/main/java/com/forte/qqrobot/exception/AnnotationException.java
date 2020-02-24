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

    public AnnotationException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public AnnotationException(Throwable cause) {
        super(cause);
    }

    public AnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }
}
