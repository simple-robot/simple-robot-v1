package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.exception.RobotRuntimeException;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SenderInterceptException extends RobotRuntimeException {

    public static final SenderInterceptException DEFAULT = new SenderInterceptException("API被拦截。");

    public SenderInterceptException() {
    }

    public SenderInterceptException(String message) {
        super(message);
    }

    public SenderInterceptException(String message, Throwable cause) {
        super(message, cause);
    }

    public SenderInterceptException(Throwable cause) {
        super(cause);
    }

    public SenderInterceptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
