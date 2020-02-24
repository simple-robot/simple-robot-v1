package com.forte.qqrobot.exception;

/**
 *
 * 枚举工厂实例注册异常，作为父类
 * 枚举工厂中的异常一般来讲，无法规避
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class EnumFactoryException extends RobotException {

    /*
        一般在配置之前被触发，所以不进行语言化
     */

    /**
     * 不使用语义化
     */
    @Override
    public String getLocalizedMessage() {
        return super.getMessage();
    }

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
        super(1, message, cause, enableSuppression, writableStackTrace);
    }
}
