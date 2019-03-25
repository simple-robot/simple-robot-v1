package com.forte.client.timetask.hello;

import com.forte.client.timetask.MyJob;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/18 18:24
 * @since JDK1.8
 **/
public class MorningJob implements MyJob {


    /**
     * 早上打招呼
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        QQWebSocketMsgSender sender = (QQWebSocketMsgSender) jobExecutionContext.getMergedJobDataMap().get("sender");
        CQCodeUtil cqCodeUtil = (CQCodeUtil) jobExecutionContext.getMergedJobDataMap().get("cqCodeUtil");


        String morningHello = "7点了！" + getMorningHelloStr();

        //发送消息
        sender.sendGroupMsg(GROUP_CODE, morningHello);

    }

    /**
     * 获取早上打招呼的字符串集
     * @return
     */
    private static String[] getMorningArr(){
        return new String[]{
                "早上好啊小骚货们",
                "该起床了！",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
        };
    }

    private static String getMorningHelloStr(){
        return RandomUtil.getRandomElement(getMorningArr());
    }
}
