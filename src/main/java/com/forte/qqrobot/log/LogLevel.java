/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     LogLevel.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.log;

/**
 * 日志级别
 * 每一个日志级别都有一个级别等级。等级数值越大，显示优先级越高。
 * 这个数据可能会经常变动，所以如果存在自定义的日志级别，请在判断的时候以此枚举中的元素作为参照物。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum LogLevel {

    /** error级 */
    ERROR(4),
    /** warning级 */
    WARNING(3),
    /** success级别 */
    SUCCESS(2),
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
