package com.forte.qqrobot.exception;

/**
 *
 * 枚举工厂实例注册异常，作为父类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class EnumFactoryException extends RobotRuntimeException {
    public EnumFactoryException() {
    }

    public EnumFactoryException(String message) {
        super(message);
    }

    public EnumFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumFactoryException(Throwable cause) {
        super(cause);
    }

    public EnumFactoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
