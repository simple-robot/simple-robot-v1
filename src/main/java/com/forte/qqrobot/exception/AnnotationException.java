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
        super("类["+ clz +"]不应当使用["+ annotationType +"]注解：" + why);
    }

    public AnnotationException(Class clz, Method method, Class<? extends Annotation> annotationType, String why) {
        super("类["+ clz +"]的方法["+ method +"]不应当使用["+ annotationType +"]注解：" + why);
    }

    public AnnotationException() {
    }


    public AnnotationException(String message) {
        super(message);
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
}
