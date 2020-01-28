package com.forte.qqrobot.log;

/**
 * 日志级别
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum LogLevel {

    /** error级 */
    ERROR(3),
    /** warning级 */
    WARNING(2),
    /** info级 */
    INFO(1),
    /** debug级 */
    DEBUG(0)
    ;

    /** 日志级别 */
    private final int level;

    /** 构造 */
    LogLevel(int level){
        this.level = level;
    }

    /** 获取日志级别 */
    public int getLevel(){
        return level;
    }

}
