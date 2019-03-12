package com.forte.client.listener;

import com.forte.client.timetask.MyJob;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
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
            e.printStackTrace();
            System.err.println("定时任务创建失败！");
        }
    }

    /**
     * 创建定时任务
     * @throws SchedulerException
     */
    public void run(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) throws SchedulerException {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("banJob").build();

        //向工作类传递参数
        jobDetail.getJobDataMap().put("sender", sender);
        jobDetail.getJobDataMap().put("cqCodeUtil", cqCodeUtil);

        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("banTrigger")
                .startNow()
                //每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?")).build();
        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        //执行任务
        scheduler.start();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

}
