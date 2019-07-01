package com.forte.qqrobot.log;

/**
 * 日志的回调函数，拦截日志输出并进行各种操作
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface QQLogBack {

    /**
     * 连接QQLog日志的输出，当返回值为true的时候才会输出到控制台。
     * @param msg   信息msg，可以直接String.valueOf()
     * @param level 日志级别
     * @param e     如果存在异常，此为异常
     * @return      是否输出此日志
     */
    boolean onLog(Object msg, LogLevel level, Throwable e);

    /**
     * 没有异常的拦截，默认直接使用null
     * @param msg       消息类型
     * @param logLevel  日志界别
     * @return          是否输出此日志
     */
    default boolean onLog(Object msg, LogLevel logLevel){
        return onLog(msg, logLevel, null);
    }
}
