package com.forte.qqrobot.log;

import java.time.LocalDateTime;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:34
 * @since JDK1.8
 **/
public class QQLog {

    private static final String INFO_HEAD = "INFO";

    private static final String DEBUG_HEAD = "DEBUG";

    public static void info(String msg){
        System.out.println(getHead(INFO_HEAD) + " : " + msg);
    }

    public static void debug(String msg){
        System.err.println(getHead(DEBUG_HEAD) + " : " + msg);
    }

    private static String getHead(String head){
        return "["+LocalDateTime.now().toString()+"] " + "["+ head+"]";
    }

}
