package com.forte.qqrobot.exception;

/**
 * config异常类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ConfigurationException extends RobotRuntimeException {

    /**
     * 重写本地化描述
     * 其暂时不适用语言化系统
     */
    @Override
    public String getLocalizedMessage(){
        return super.getMessage();
    }

    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
