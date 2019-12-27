package com.forte.qqrobot.log;

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

    private static final PrintStream warning;

    /**
     * 全局日志级别，先优先使用此日志级别进行筛选，默认debug
     */
    private static LogLevel globalLevel = LogLevel.DEBUG;

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
                return timeColors + " " + typeColors + " " + obj;
            });
        } catch (IllegalAccessException | UnsupportedEncodingException e) {
            warningPrintStream = null;
        }
        warning = warningPrintStream;

        String oh_hi_is_me = "_(^w^)L~~ by simple-robot@ForteScarlet ~~";
        int length = oh_hi_is_me.length();
        char line = '~';
        /* QQLog初始化的时候输出个东西~ */
        ColorsBuilder hi_i_am_builder_HEAD = Colors.builder();
        for (int i = 0; i < length; i++) {
            hi_i_am_builder_HEAD.add(line, wowThatIsRainbowToo$());
        }

        System.out.println(hi_i_am_builder_HEAD.build().toString());

        ColorsBuilder hi_i_am_builder = Colors.builder();
        oh_hi_is_me.chars().forEach(ic -> hi_i_am_builder.add((char) ic, wowThatIsRainbow$()));
        System.out.println(hi_i_am_builder.build().toString());
        ColorsBuilder hi_i_am_builder_END = Colors.builder();
        for (int i = 0; i < length; i++) {
            hi_i_am_builder_END.add(line, wowThatIsRainbowToo$());
        }

        System.out.println(hi_i_am_builder_END.build().toString());
    }

    private static ColorTypes wowThatIsRainbow$(){
        return RandomUtil.getRandomElement(FontColorTypes.values());
    }
    private static ColorTypes wowThatIsRainbowToo$(){
        return RandomUtil.getRandomElement(BackGroundColorTypes.values());
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
    private static boolean ifCan(LogLevel logLevel){
        return logLevel.getLevel() >= globalLevel.getLevel();
    }


    public static LogLevel getGlobalLevel() {
        return globalLevel;
    }

    public static void setGlobalLevel(LogLevel globalLevel) {
        QQLog.globalLevel = globalLevel;
    }

    /**
     * 日志输出
     * @param msg   消息
     * @param level 日志等级
     * @param logStream 输出流
     * @param e 异常，可以为null
     */
    public static void log(Object msg, LogLevel level, PrintStream logStream, Throwable e){
        if(ifCan(level)){
            if(qqLogBack.onLog(msg, level)){
                logStream.println(msg);
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
    public static void log(Object msg, LogLevel level, PrintStream logStream){
        log(msg, level, logStream, null);
    }

    public static void info(Object msg) {
        log(msg, LogLevel.INFO, info);
    }

    public static void info(Object msg, Throwable e) {
        log(msg, LogLevel.INFO, info, e);
    }

    public static void debug(Object msg) {
        log(msg, LogLevel.DEBUG, debug);
    }

    public static void debug(Object msg, Throwable e) {
        log(msg, LogLevel.DEBUG, debug, e);
    }

    public static void warning(Object msg) {
        log(msg, LogLevel.WARNING, warning);
    }

    public static void warning(Object msg, Throwable e) {
        log(msg, LogLevel.WARNING, warning, e);
    }

    public static void error(Object msg) {
        log(msg, LogLevel.ERROR, err);
    }

    public static void error(Object msg, Throwable e) {
        log(msg, LogLevel.ERROR, err, e);
    }



}
