package com.forte.qqrobot.timetask;

import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.RobotApplication;
import com.forte.qqrobot.utils.RandomUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务-每天0点禁言随机任务
 */
public class MyJob implements Job{

    /** 群号 */
    private final String GROUP_CODE = "581250423";

    /**
     * 任务逻辑
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //群里除了群主和自己的全部qq号
        String codesStr = "1401333425,1770674146,1359589084,2209278137,1278568310,294657142,867604122,1341020356,826744761,875065714,1462974622,1224829665,1216231723,1064843896,1041946577,2857633943,2303723088,469207122,1510125787";
        String[] codes = codesStr.split(",");

        RobotApplication.mainClient.sendMsgToGroup("12点了！！！！", GROUP_CODE);
        try {
            Thread.sleep(800);
        } catch (InterruptedException ignore) {
        }
        RobotApplication.mainClient.sendMsgToGroup("接下来我要挑选一个幸运儿禁言1分钟，是谁这么幸运呢？", GROUP_CODE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }

        //随机获取一个QQ号
        String qqCode = RandomUtil.getRandomElement(codes);

        //参数
        Map<String, Object> map = new HashMap<>(4);
//        121, 置群员禁言
//        QQID，groupid，duration
        map.put("act", "121");
        map.put("QQID", qqCode);
        map.put("groupid", GROUP_CODE);
        map.put("duration", 60);

        /*
        121, 置群员禁言
        QQID，groupid，duration
         */
        String jsonStr = JSON.toJSONString(map);
        RobotApplication.mainClient.send(jsonStr);
    }
}