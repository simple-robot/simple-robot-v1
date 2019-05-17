package com.forte.qqrobot.beans.types;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

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
