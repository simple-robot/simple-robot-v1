package com.forte.qqrobot.timetask;

import com.forte.qqrobot.timetask.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/7 16:41
 * @since JDK1.8
 **/
public class TimeTasks {

    public void run() throws SchedulerException {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("myJob").build();
        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger")
                .startNow()
                //每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?")).build();
        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
