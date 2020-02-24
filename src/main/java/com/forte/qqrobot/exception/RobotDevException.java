package com.forte.qqrobot.exception;

/**
 *
 * 面象开发者的异常，一般来讲这类异常应当在开发阶段就应该全部处理掉
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class RobotDevException extends RobotRuntimeException {

    @Override
    public String getExceptionTag(){
        return "dev";
    }

    public RobotDevException() {
    }

    public RobotDevException(Throwable cause) {
        super(cause);
    }
    public RobotDevException(String message) {
        super(message);
    }

    public RobotDevException(String message, Throwable cause) {
        super(message, cause);
    }

    public RobotDevException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public RobotDevException(String message, Object... format) {
        super(message, format);
    }

    public RobotDevException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public RobotDevException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    //********************************//


    public RobotDevException(int pointless, String message) {
        super(pointless, message);
    }

    public RobotDevException(int pointless, String message, Throwable cause) {
        super(pointless, message, cause);
    }

    public RobotDevException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
