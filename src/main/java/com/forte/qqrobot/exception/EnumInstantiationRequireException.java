package com.forte.qqrobot.exception;

/**
 *
 * 枚举工厂实例化参数判断异常
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class EnumInstantiationRequireException extends EnumFactoryException {


    public <E extends Enum<E>> EnumInstantiationRequireException(Class<E> enumType, Throwable e){
        super("enum type '"+ enumType +"' new instance required failed ", e);
    }

    public <E extends Enum<E>> EnumInstantiationRequireException(Class<E> enumType, String message, Throwable e){
        super("enum type '"+ enumType +"' new instance required failed: " + message, e);
    }


    public EnumInstantiationRequireException() {
    }

    public EnumInstantiationRequireException(String message) {
        super(message);
    }

    public EnumInstantiationRequireException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumInstantiationRequireException(Throwable cause) {
        super(cause);
    }

    public EnumInstantiationRequireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
