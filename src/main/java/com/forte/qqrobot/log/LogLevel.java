package com.forte.qqrobot.log;

/**
 * 日志级别枚举
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum LogLevel {

    DEBUG(0),
    INFO(1),
    WARNING(2),
    ERROR(3)
    ;

    private final int level;


    LogLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

}
