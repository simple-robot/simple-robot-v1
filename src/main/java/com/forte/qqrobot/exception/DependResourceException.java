package com.forte.qqrobot.exception;

/**
 * 依赖资源错误
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DependResourceException extends RobotRuntimeException {

//    private static final String LANG_TAG_HEAD = "dependResource";

    public DependResourceException() {
    }

    public DependResourceException(String message, Object... format) {
        super(message, format);
    }

    public DependResourceException(String message) {
        super(message);
    }

    public DependResourceException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public DependResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependResourceException(Throwable cause) {
        super(cause);
    }

    public DependResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DependResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     */
    public DependResourceException(int pointless, String message) {
        super(pointless, message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public DependResourceException(int pointless, String message, Throwable cause) {
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
    public DependResourceException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
