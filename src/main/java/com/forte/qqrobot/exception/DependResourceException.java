package com.forte.qqrobot.exception;

/**
 * 依赖资源错误
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DependResourceException extends RobotRuntimeException {

    private static final String LANG_TAG_HEAD = "dependResource";

    public DependResourceException(Throwable cause) {
        super(cause);
    }

    public DependResourceException() {
    }
    public DependResourceException(String message) {
        super(LANG_TAG_HEAD + '.' + message);
    }
    public DependResourceException(String message, Throwable cause) {
        super(LANG_TAG_HEAD + '.' + message, cause);
    }
    public DependResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(LANG_TAG_HEAD + '.' + message, cause, enableSuppression, writableStackTrace);
    }
    public DependResourceException(String message, Object... format) {
        super(LANG_TAG_HEAD + '.' + message, format);
    }
    public DependResourceException(String message, Throwable cause, Object... format) {
        super(LANG_TAG_HEAD + '.' + message, cause, format);
    }
    public DependResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(LANG_TAG_HEAD + '.' + message, cause, enableSuppression, writableStackTrace, format);
    }
}
