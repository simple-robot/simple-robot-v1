package com.forte.qqrobot.log;

import com.forte.lang.Language;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;

import static com.forte.qqrobot.log.QQLog.ifCan;
import static com.forte.qqrobot.log.QQLog.info;
import static com.forte.qqrobot.log.QQLog.debug;
import static com.forte.qqrobot.log.QQLog.warning;
import static com.forte.qqrobot.log.QQLog.err;

/**
 * 用于整合{@link com.forte.lang.Language}
 * 主要就是提供一个日志标签头的统一
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 * @version 1.7
 */
public class QQLogLang {

    /** 标签头部 */
    private String tagHead;
    /** 其对应的char数组, 结尾是有一个'.'的 */
    private char[] tagHeadChars;

    public QQLogLang(String tagHead){
        if(tagHead.trim().length() == 0){
            tagHead = null;
        }
        // if null, throw null
        Objects.requireNonNull(tagHead, "can not be null or empty.");
        this.tagHead = tagHead;
        tagHeadChars = (tagHead + '.').toCharArray();
    }

    /**
     * 为msg添加tag的开头.
     * @param msg msg， 或者language
     * @return {@code headTag.msg}
     */
    private String getTag(String msg){
        /*
            这么写主要是尝试避免使用 + 或者stringBuilder, 尽可能做个优化.
            相对于Language的format的方法的重载，这个方法中的headTag是固定的char，获取可以在某种意义上更加效率吧。
            大概吧
         */
        int tagHeadLength = tagHeadChars.length;
        int msgLength = msg.length();
        char[] chars = Arrays.copyOf(tagHeadChars, tagHeadLength + msgLength);
        msg.getChars(0, msgLength, chars, tagHeadLength);
        return new String(chars);
    }
    
    /**
     * 日志输出
     * @param msg   消息
     * @param level 日志等级
     * @param logStream 输出流
     * @param e 异常，可以为null
     */
    public void log(Object msg, LogLevel level, PrintStream logStream, Throwable e, Object... formatArgs){
        String msgStr = msg.toString();
        // 判断语言是否已经初始化完成
        msgStr = Language.format(getTag(msgStr), formatArgs);
        if(ifCan(level)){
            if(QQLog.getLogBack().onLog(msgStr, level)){
                logStream.println(msgStr);
                if(e != null){
                    e.printStackTrace(logStream);
                }
            }
        }
    }

    /**
     * 日志输出
     * @param msg   消息
     * @param level 日志等级
     * @param logStream 输出流
     */
    public void log(Object msg, LogLevel level, PrintStream logStream, Object... format){
        log(msg, level, logStream, null, format);
    }

    public void info(Object msg, Object... format) {
        log(msg, LogLevel.INFO, QQLog.info, null, format);
    }

    public void info(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.INFO, info, e, format);
    }

    public void debug(Object msg, Object... format) {
        log(msg, LogLevel.DEBUG, debug, null, format);
    }

    public void debug(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.DEBUG, debug, e, format);
    }

    public void warning(Object msg, Object... format) {
        log(msg, LogLevel.WARNING, warning, null, format);
    }

    public void warning(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.WARNING, warning, e, format);
    }

    public void error(Object msg, Object... format) {
        log(msg, LogLevel.ERROR, err, null, format);
    }

    public void error(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.ERROR, err, e, format);
    }

}
