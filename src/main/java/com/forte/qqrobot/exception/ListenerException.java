package com.forte.qqrobot.exception;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ListenerException extends RobotRuntimeException {
    public ListenerException() {
    }

    public ListenerException(String message, Object... format) {
        super(message, format);
    }

    public ListenerException(String message) {
        super(message);
    }

    public ListenerException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public ListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListenerException(Throwable cause) {
        super(cause);
    }

    public ListenerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ListenerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     */
    public ListenerException(int pointless, String message) {
        super(pointless, message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public ListenerException(int pointless, String message, Throwable cause) {
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
    public ListenerException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
