package com.forte.qqrobot.exception;

import com.forte.lang.Language;
import com.forte.qqrobot.log.QQLog;

import java.security.PrivilegedActionException;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 16:55
 * @since JDK1.8
 **/
public class RobotException extends Exception {

    /**
     * 对于异常类，所有的lang均以exception.开头
     */
    private static final String RUNTIME_ERROR_HEAD = "exception";

    /**
     * 异常会通过QQLog.err抛出而不是System.err
     * 实验性质功能
     */
    @Override
    public void printStackTrace() {
        printStackTrace(QQLog.err);
    }

    private static String getMessage(String message, Object... format){
        return Language.format(RUNTIME_ERROR_HEAD, message, format);
    }

    public RobotException() {
        super();
    }

    public RobotException(String message) {
        super(getMessage(message));
    }

    public RobotException(String message, Object... format) {
        super(getMessage(message, format));
    }

    public RobotException(String message, Throwable cause) {
        super(getMessage(message), cause);
    }

    public RobotException(String message, Throwable cause, Object... format) {
        super(getMessage(message, format), cause);
    }

    public RobotException(Throwable cause) {
        super(cause);
    }

    public RobotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(getMessage(message), cause, enableSuppression, writableStackTrace);
    }

    public RobotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(getMessage(message, format), cause, enableSuppression, writableStackTrace);
    }
}
