package com.forte.qqrobot.exception;

import com.forte.lang.Language;
import com.forte.qqrobot.log.QQLog;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/2 15:56
 * @since JDK1.8
 **/
public class RobotRuntimeException extends RuntimeException {

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
        return Language.format(RUNTIME_ERROR_HEAD , message, format);
    }

    public RobotRuntimeException() {
    }

    public RobotRuntimeException(String message, Object... format) {
        super(getMessage(message, format));
    }

    public RobotRuntimeException(String message) {
        super(getMessage(message));
    }

    public RobotRuntimeException(String message, Throwable cause, Object... format) {
        super(getMessage(message, format), cause);
    }

    public RobotRuntimeException(String message, Throwable cause) {
        super(getMessage(message), cause);
    }

    public RobotRuntimeException(Throwable cause) {
        super(cause);
    }

    public RobotRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(getMessage(message), cause, enableSuppression, writableStackTrace);
    }

    public RobotRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
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
    public RobotRuntimeException(int pointless, String message) {
        super(message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public RobotRuntimeException(int pointless, String message, Throwable cause) {
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
    public RobotRuntimeException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
