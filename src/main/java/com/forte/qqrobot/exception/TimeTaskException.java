package com.forte.qqrobot.exception;

/**
 * 加载定时任务出错时候的异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TimeTaskException extends RobotRuntimeException {

//    private static final String LANG_TAG_HEAD = "timeTask";

    public TimeTaskException() {
    }
    public TimeTaskException(Throwable cause) {
        super(cause);
    }


    public TimeTaskException(String message) {
        super(message);
    }

    public TimeTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public TimeTaskException(String message, Object... format) {
        super(message, format);
    }

    public TimeTaskException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public TimeTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }
}
