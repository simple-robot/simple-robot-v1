package com.forte.qqrobot.log;

import java.io.PrintStream;
import java.time.LocalDateTime;

/**
 * 临时使用的log类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:34
 * @since JDK1.8
 **/
public class QQLog {

//    public static final PrintStream INFO;
//    public static final PrintStream DEBUG;
//    public static final PrintStream ERROR;

    private static final String INFO_HEAD = "INFO";

    private static final String DEBUG_HEAD = "DEBUG";

    private static final String ERROR_HEAD = "ERROR";

    public static void info(String msg){
        System.out.println(getHead(INFO_HEAD) + " : " + msg);
    }

    public static void debug(String msg){
        System.err.println(getHead(DEBUG_HEAD) + " : " + msg);
    }

    public static void error(String msg, Exception e){
        System.err.println(getHead(ERROR_HEAD) + " : " + msg);
        e.printStackTrace();
    }

    private static String getHead(String head){
        return "["+LocalDateTime.now().toString()+"] " + "["+ head+"]";
    }

}
