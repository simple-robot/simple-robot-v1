package com.forte.qqrobot.exception;

/**
 *
 * 枚举实例实例化异常
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class EnumInstantiationException extends EnumFactoryException {

    public <E extends Enum<E>> EnumInstantiationException(Class<E> enumType, Throwable e){
        super("enum type '"+ enumType +"' create new instance failed ", e);
    }

    public <E extends Enum<E>> EnumInstantiationException(Class<E> enumType, String message, Throwable e){
        super("enum type '"+ enumType +"' create new instance failed: " + message, e);
    }


    public EnumInstantiationException() {
    }

    public EnumInstantiationException(String message) {
        super(message);
    }

    public EnumInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumInstantiationException(Throwable cause) {
        super(cause);
    }

    public EnumInstantiationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
