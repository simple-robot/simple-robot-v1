package com.forte.qqrobot.log;

import com.forte.lang.Language;
import com.forte.plusutils.consoleplus.FortePlusPrintStream;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.console.ColorsBuilder;
import com.forte.plusutils.consoleplus.console.colors.BackGroundColorTypes;
import com.forte.plusutils.consoleplus.console.colors.ColorTypes;
import com.forte.plusutils.consoleplus.console.colors.FontColorTypes;
import com.forte.plusutils.consoleplus.system.ColorSystem;
import com.forte.qqrobot.utils.RandomUtil;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * log类, 继承ColorSystem，彩色打印
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:34
 * @since JDK1.8
 **/
public class QQLog extends ColorSystem {

    /** warning 类型 */
    public static final PrintStream warning;

    /** success类型 */
    public static final PrintStream success;

    /**
     * 全局日志级别，先优先使用此日志级别进行筛选，默认info
     */
    private static int globalLevel = LogLevel.INFO.getLevel();

    /**
     * 日志的连接信息
     */
    private static QQLogBack qqLogBack = (m, l, e) -> true;


    static {
        //设置err为红色字体
        setErrTextFunction(str -> Colors.builder().add(str, Colors.FONT.RED).build().toString());
        //设置警告类型输出
        PrintStream warningPrintStream;
        try {
            warningPrintStream = FortePlusPrintStream.getInstance(System.out, obj -> {
                Colors timeColors = Colors.builder().add("[" + LocalDateTime.now().toString() + "]", Colors.FONT.BLUE).build();
                Colors typeColors = Colors.builder()
                        .add("[", Colors.FONT.BLUE)
                        .add("WARN", Colors.FONT.YELLOW)
                        .add("]", Colors.FONT.BLUE)
                        .build();
                Colors msgColors = Colors.builder().add(obj, Colors.FONT.YELLOW).build();
                return timeColors + " " + typeColors + " " + msgColors;
            });
        } catch (IllegalAccessException | UnsupportedEncodingException e) {
            warningPrintStream = null;
        }
        warning = warningPrintStream;

        //设置成功类型输出
        PrintStream successPrintStream;
        try {
            successPrintStream = FortePlusPrintStream.getInstance(System.out, obj -> {
                Colors timeColors = Colors.builder().add("[" + LocalDateTime.now().toString() + "]", Colors.FONT.BLUE).build();
                Colors typeColors = Colors.builder()
                        .add("[", Colors.FONT.BLUE)
                        .add("SUC ", Colors.FONT.DARK_GREEN)
                        .add("]", Colors.FONT.BLUE)
                        .build();
                Colors msgColors = Colors.builder().add(obj, Colors.FONT.DARK_GREEN).build();
                return timeColors + " " + typeColors + " " + msgColors;
            });
        } catch (IllegalAccessException | UnsupportedEncodingException e) {
            successPrintStream = null;
        }
        success = successPrintStream;
    }


    /**
     * 更换日志阻断，同{@link #setLogBack(QQLogBack)}
     */
    public static void changeQQLogBack(QQLogBack logBack) {
        QQLog.qqLogBack = logBack;
    }

    public static QQLogBack getLogBack() {
        return qqLogBack;
    }

    public static void setLogBack(QQLogBack qqLogBack) {
        QQLog.qqLogBack = qqLogBack;
    }

    /**
     * 判定日志级别是否可以输出
     * @param logLevel 日志级别
     */
    public static boolean ifCan(LogLevel logLevel){
        return ifCan(logLevel.getLevel());
    }

    /**
     * 判定日志级别是否可以输出
     * @param logLevel 日志级别
     */
    public static boolean ifCan(int logLevel){
        return logLevel >= globalLevel;
    }


    public static int getGlobalLevel() {
        return globalLevel;
    }

    public static void setGlobalLevel(LogLevel globalLevel) {
        QQLog.globalLevel = globalLevel.getLevel();
    }

    /**
     * 日志输出
     * @param msg   消息
     * @param level 日志等级
     * @param logStream 输出流
     * @param e 异常，可以为null
     */
    public static void log(Object msg, LogLevel level, PrintStream logStream, Throwable e, Object... formatArgs){
        String msgStr = msg.toString();
        // 判断语言是否已经初始化完成
        msgStr = Language.format(msgStr, formatArgs);
        if(ifCan(level)){
            if(qqLogBack.onLog(msgStr, level)){
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
    public static void log(Object msg, LogLevel level, PrintStream logStream, Object... format){
        log(msg, level, logStream, null, format);
    }

    public static void info(Object msg, Object... format) {
        log(msg, LogLevel.INFO, info, null, format);
    }

    public static void info(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.INFO, info, e, format);
    }

    public static void success(Object msg, Object... format) {
        log(msg, LogLevel.SUCCESS, success, null, format);
    }

    public static void success(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.SUCCESS, success, e, format);
    }

    public static void debug(Object msg, Object... format) {
        log(msg, LogLevel.DEBUG, debug, null, format);
    }

    public static void debug(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.DEBUG, debug, e, format);
    }

    public static void warning(Object msg, Object... format) {
        log(msg, LogLevel.WARNING, warning, null, format);
    }

    public static void warning(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.WARNING, warning, e, format);
    }

    public static void error(Object msg, Object... format) {
        log(msg, LogLevel.ERROR, err, null, format);
    }

    public static void error(Object msg, Throwable e, Object... format) {
        log(msg, LogLevel.ERROR, err, e, format);
    }






}
