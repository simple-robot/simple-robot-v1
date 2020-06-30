/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BotVerifyException.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.exception;

/**
 * 账号验证失败异常, 不可避免异常
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotVerifyException extends RobotRuntimeException {
    public BotVerifyException() {
    }

    public BotVerifyException(String message) {
        super(message);
    }

    public BotVerifyException(String message, Object... format) {
        super(message, format);
    }

    public BotVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotVerifyException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public BotVerifyException(Throwable cause) {
        super(cause);
    }

    public BotVerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BotVerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     */
    public BotVerifyException(int pointless, String message) {
        super(pointless, message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public BotVerifyException(int pointless, String message, Throwable cause) {
        super(pointless, message, cause);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless          无意义参数，填任意值 pointless param
     * @param message            信息正文
     * @param cause              异常
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     */
    public BotVerifyException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
