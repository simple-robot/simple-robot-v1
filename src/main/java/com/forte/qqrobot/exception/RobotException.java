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
    //**************************************
    //*  提供一部分不会经过消息转化的方法
    //*  这部分方法的第一个参数存在一个int类型的参数，此参数无意义，仅用于标记用以区分其他方法
    //**************************************


    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     */
    public RobotException(int pointless, String message) {
        super(message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public RobotException(int pointless, String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless             无意义参数，填任意值 pointless param
     * @param message               信息正文
     * @param cause                 异常
     * @param enableSuppression     whether or not suppression is enabled
     *                                or disabled
     * @param writableStackTrace    whether or not the stack trace should
     *                                 be writable
     */
    public RobotException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
