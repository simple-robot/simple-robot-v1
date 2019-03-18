package com.forte.client.timetask;

import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 定时任务-每天0点禁言随机任务
 */
public class MyJob implements Job {

    /** 群号 */
    private final String GROUP_CODE = "581250423";

    /**
     * 任务逻辑
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        QQWebSocketMsgSender sender = (QQWebSocketMsgSender) jobExecutionContext.getMergedJobDataMap().get("sender");
        CQCodeUtil cqCodeUtil = (CQCodeUtil) jobExecutionContext.getMergedJobDataMap().get("cqCodeUtil");
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
        String at = cqCodeUtil.getCQCode_at(qqCode);

        sender.sendGroupMsg(GROUP_CODE, "是"+ at +" 哒！");
        //设置禁言
        sender.setGroupMemberBanned(qqCode, GROUP_CODE, 60L);
    }
}