package com.forte.qqrobot.log;

import com.forte.plusutils.consoleplus.FortePlusPrintStream;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.system.ColorSystem;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * log类, 继承ColorSystem，彩色打印
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:34
 * @since JDK1.8
 **/
public class QQLog extends ColorSystem {


    private static final PrintStream warning;

    static{
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
    }

    public static void info(Object msg){
        info.println(msg);
    }

    public static void debug(Object msg){
        debug.println(msg);
    }

    public static void error(Object msg, Throwable e){
        err.println(msg);
        e.printStackTrace();
    }

    public static void warning(Object msg){
        warning.println(msg);
    }



}
