package com.forte.client.listener;

import com.forte.client.timetask.ban.AdvanceBanJob;
import com.forte.client.timetask.ban.BanJob;
import com.forte.client.timetask.hello.HelloJob;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 15:16
 * @since JDK1.8
 **/
public class TimeTaskListener implements InitListener {

    /** 群号 */
    private final String GROUP_CODE = "581250423";

    /**
     * 初始化方法,
     * 用于创建定时任务
     */
    @Override
    public void init(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        try {
            run(cqCodeUtil, sender);
        } catch (SchedulerException e) {
            QQLog.error("定时任务创建失败！", e);
        }
    }

    /**
     * 创建定时任务
     */
    public void run(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) throws SchedulerException {

        /* ———————— 创造工作类 ———————— */

        //创建一个jobDetail的实例，将该实例与Ban Class绑定,每日执行禁言
        JobDetail BanJob = JobBuilder.newJob(BanJob.class).withIdentity("banJob").build();
        //向工作类传递参数
        BanJob.getJobDataMap().put("sender", sender);
        BanJob.getJobDataMap().put("cqCodeUtil", cqCodeUtil);


        //创建一个jobDetail的实例，将该实例与AdvanceBanJob绑定
        JobDetail advanceBanJob = JobBuilder.newJob(AdvanceBanJob.class).withIdentity("advanceBanJob").build();
        //向工作类传递参数
        advanceBanJob.getJobDataMap().put("sender", sender);
        advanceBanJob.getJobDataMap().put("cqCodeUtil", cqCodeUtil);


        //创建一个JobDetail的实例，与HelloJob绑定
        JobDetail helloJob = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob").build();
        //向工作类传递参数
        advanceBanJob.getJobDataMap().put("sender", sender);
        advanceBanJob.getJobDataMap().put("cqCodeUtil", cqCodeUtil);



        /* ———————— 创建Trigger触发器的实例 ———————— */


        //创建一个Trigger触发器的实例，每天12点执行
        CronTrigger cronTrigger_00_00 = TriggerBuilder.newTrigger()
                .withIdentity("banTrigger")
                .startNow()
                //每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ? *")).build();
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 11 * * ? *")).build();


        //创建一个Trigger触发器的实例，每天差5分钟12点执行
        CronTrigger cronTrigger_23_55 = TriggerBuilder.newTrigger()
                .withIdentity("advanceBanTrigger")
                .startNow()
                //每天11点55执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 55 23 * * ? *")).build();
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 55 10 * * ? *")).build();

        //创建一个Trigger触发器的实例，每天差5分钟12点执行
        CronTrigger cronTrigger_12_00 = TriggerBuilder.newTrigger()
                .withIdentity("helloTrigger")
                .startNow()
                //每天12点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ? *")).build();


        /* ———————— 创建schedule实例 ———————— */

        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        //23点55执行的任务
        scheduleJob(scheduler, advanceBanJob, cronTrigger_23_55);
        //00点执行ban任务
        scheduleJob(scheduler, BanJob, cronTrigger_00_00);
        //中午12点执行任务
        scheduleJob(scheduler, helloJob, cronTrigger_12_00);

        //执行任务
        scheduler.start();
    }

    /**
     * 加载定时任务
     */
    private void scheduleJob(Scheduler scheduler, JobDetail job, CronTrigger cronTrigger){
        try {
            scheduler.scheduleJob(job, cronTrigger);
            QQLog.info("加载定时任务["+ job.getJobClass() +"]成功");
        } catch (SchedulerException e) {
            QQLog.debug("加载定时任务["+ job.getJobClass() +"]失败！");
            e.printStackTrace();
        }
    }

}
