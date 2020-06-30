/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     TimeType.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.types;

import org.quartz.SimpleScheduleBuilder;

import java.util.function.Function;

/**
 * 定时任务的时间类型:毫秒、秒、分、小时
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum TimeType {

    /** 毫秒 */
    millisecond(t -> SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(t)),
    /** 秒 */
    second(t -> SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(t.intValue())),
    /** 分钟 */
    minute(t -> SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(t.intValue())),
    /** 小时 */
    hour(t -> SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(t.intValue()));

    /** 通过传入的参数获取Trigger */
    private final Function<Long, SimpleScheduleBuilder> getTriggerByTime;

    /** 构造 */
    TimeType(Function<Long, SimpleScheduleBuilder> function){
        this.getTriggerByTime = function;
    }

    /**
     * 通过时间获取SinpleScheduleBuilder对象
     */
    public SimpleScheduleBuilder getSimpleScheduleBuilder(Long t){
        return getTriggerByTime.apply(t);
    }


}
