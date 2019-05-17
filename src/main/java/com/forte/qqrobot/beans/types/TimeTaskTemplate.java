package com.forte.qqrobot.beans.types;

import org.quartz.CronScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 定时任务类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum TimeTaskTemplate {

    /** 每1毫秒触发一次 */
    INTERVAL_1_MILLISECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(1).repeatForever()).build()),

    /** 每100毫秒触发一次 */
    INTERVAL_100_MILLISECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(100).repeatForever()).build()),

    /** 每200毫秒触发一次 */
    INTERVAL_200_MILLISECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(200).repeatForever()).build()),

    /** 每500毫秒触发一次 */
    INTERVAL_500_MILLISECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(500).repeatForever()).build()),

    /** 每1秒触发一次 */
    INTERVAL_1_SECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build()),

    /** 每5秒触发一次 */
    INTERVAL_5_SECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build()),

    /** 每10秒触发一次 */
    INTERVAL_10_SECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build()),

    /** 每30秒触发一次 */
    INTERVAL_30_SECOND(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).build()),

    /** 每1分钟触发一次 */
    INTERVAL_1_MINUTE(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever()).build()),

    /** 每5分钟触发一次 */
    INTERVAL_5_MINUTE(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever()).build()),

    /** 每10分钟触发一次 */
    INTERVAL_10_MINUTE(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(10).repeatForever()).build()),

    /** 每30分钟触发一次 */
    INTERVAL_30_MINUTE(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(30).repeatForever()).build()),

    /** 每1小时触发一次 */
    INTERVAL_1_HOUR(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()).build()),

    /** 每2小时触发一次 */
    INTERVAL_2_HOUR(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(2).repeatForever()).build()),

    /** 每4小时触发一次 */
    INTERVAL_4_HOUR(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(4).repeatForever()).build()),

    /** 每6小时触发一次 */
    INTERVAL_6_HOUR(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(6).repeatForever()).build()),

    /** 每12小时触发一次 */
    INTERVAL_12_HOUR(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(12).repeatForever()).build()),

    /** 每24小时触发一次 */
    INTERVAL_24_HOUR(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).repeatForever()).build()),

    /** 每天00点触发 */
    EVERY_DAY_TIME_00_00(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1/1 * ?")).build()),

    /** 每天12点触发 */
    EVERY_DAY_TIME_12_00(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 1/1 * ?")).build()),

    /** 每天18点触发 */
    EVERY_DAY_TIME_18_00(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 18 1/1 * ?")).build()),

    /** 每周一0点触发 */
    EVERY_WEEK_MONDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 1 *")).build()),

    /** 每周二0点触发 */
    EVERY_WEEK_TUESDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 2 *")).build()),

    /** 每周三0点触发 */
    EVERY_WEEK_WEDNESDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 3 *")).build()),

    /** 每周四0点触发 */
    EVERY_WEEK_THURSDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 4 *")).build()),

    /** 每周五0点触发 */
    EVERY_WEEK_FRIDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 5 *")).build()),

    /** 每周六0点触发 */
    EVERY_WEEK_SATURDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 6 *")).build()),

    /** 每周天0点触发 */
    EVERY_WEEK_SUNDAY(name -> TriggerBuilder.newTrigger().withIdentity(name).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 ? 0 0 7 *")).build()),

    ;

    /** 创建一个trigger触发器实例 */
    private Function<String, Trigger> triggerCreater;

    /** 构造 */
    TimeTaskTemplate(Function<String, Trigger> triggerCreater){
        this.triggerCreater = triggerCreater;
    }

    /** 获取定时触发器 */
    public Trigger getTrigger(String name){
        return triggerCreater.apply(name);
    }

}
