package com.forte.client.listener;

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
        Job job = jobExecutionContext -> {
            //群里除了群主和自己的全部qq号
            String codesStr = "1401333425,1770674146,1359589084,2209278137,1278568310,294657142,867604122,1341020356,826744761,875065714,1462974622,1224829665,1216231723,1064843896,1041946577,2857633943,2303723088,469207122,1510125787";
            String[] codes = codesStr.split(",");

            sender.sendGroupMsg(GROUP_CODE, "12点了！！！！");
            try {
                Thread.sleep(800);
            } catch (InterruptedException ignore) {
            }
            sender.sendGroupMsg(GROUP_CODE, "接下来我要挑选一个幸运儿禁言1分钟，是谁这么幸运呢？");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }

            //随机获取一个QQ号
            String qqCode = RandomUtil.getRandomElement(codes);
            //获取at的CQcode
            String atCode = cqCodeUtil.getCQCode_at(qqCode);
            sender.sendGroupMsg(GROUP_CODE, "是"+ atCode +" 哒！");
            //设置禁言
            sender.setGroupMemberBanned(qqCode, GROUP_CODE, 60L);
        };

        try {
            run(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println("创建定时任务失败！");
        }

    }

    /**
     * 创建定时任务
     * @throws SchedulerException
     */
    public void run(Job job) throws SchedulerException {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity("banJob").build();
        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("banTrigger")
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
