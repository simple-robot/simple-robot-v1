/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     RobotApiException.java
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
 * 当Api无法使用的时候将会抛出此异常
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class RobotApiException extends RobotRuntimeException {

    @Override
    public String getExceptionTag(){
        return "api";
    }

    public static RobotApiException byApiName(String api){
        return new RobotApiException("notSupport", api);
    }

    public static RobotApiException byFrom(){
        String apiName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return byApiName(apiName);
    }

    public RobotApiException() {
    }

    public RobotApiException(String message) {
        super(message);
    }
    public RobotApiException(String message, Object... format) {
        super(message, format);
    }

    public RobotApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RobotApiException(Throwable cause) {
        super(cause);
    }

    public RobotApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
