package com.forte.qqrobot.log;

import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.system.ColorSystem;

import java.io.PrintStream;
import java.time.LocalDateTime;

/**
 * log类, 继承ColorSystem，彩色打印
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:34
 * @since JDK1.8
 **/
public class QQLog extends ColorSystem {

//    public static final PrintStream INFO;
//    public static final PrintStream DEBUG;
//    public static final PrintStream ERROR;
//    private static final String INFO_HEAD = "INFO";
//    private static final String DEBUG_HEAD = "DEBUG";
//    private static final String ERROR_HEAD = "ERROR";

    static{
        //设置err为红色字体
        setErrTextFunction(str -> Colors.builder().add(str, Colors.FONT.RED).build().toString());
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

//    private static String getHead(String head){
//        return "["+LocalDateTime.now().toString()+"] " + "["+ head+"]";
//    }

}
