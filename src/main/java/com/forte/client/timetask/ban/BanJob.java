package com.forte.client.timetask.ban;

import com.forte.client.timetask.MyJob;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 定时任务-每天0点禁言随机任务
 */
public class BanJob implements MyJob {

    /**
     * 任务逻辑
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        QQWebSocketMsgSender sender = (QQWebSocketMsgSender) jobExecutionContext.getMergedJobDataMap().get("sender");
        CQCodeUtil cqCodeUtil = (CQCodeUtil) jobExecutionContext.getMergedJobDataMap().get("cqCodeUtil");
        //群里除了群主和自己的全部qq号
        String codesStr = "1401333425,1770674146,1359589084,2209278137,1278568310,294657142,867604122,1341020356,826744761,875065714,1462974622,1224829665,1216231723,1064843896,1041946577,2857633943,2303723088,469207122,1510125787";
//        String codesStr = "2209278137, 1770674146";
        String[] codes = codesStr.split(",");

        sender.sendGroupMsg(GROUP_CODE, "12点了！！！！");

        sleep(1000);

        //获取禁言时间
        long banLong = BanUtils.getBanLong();

        sender.sendGroupMsg(GROUP_CODE, "接下来我要挑选一个幸运儿禁言" + banLong + "分钟，是谁这么幸运呢？");

        sleep(1000);

        //随机获取一个QQ号
        String qqCode = RandomUtil.getRandomElement(codes);
        String at = cqCodeUtil.getCQCode_at(qqCode);
        //更新禁言记录
        BanUtils.updateBanSave(qqCode);

        sender.sendGroupMsg(GROUP_CODE, "是" + at + " 哒！");

        sleep(1100);

        //设置禁言
        sender.setGroupMemberBanned(qqCode, GROUP_CODE, banLong * 60);
    }

    /**
     * 线程睡眠
     */
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
    }
}