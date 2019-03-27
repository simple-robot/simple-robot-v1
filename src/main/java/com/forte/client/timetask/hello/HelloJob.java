package com.forte.client.timetask.hello;

import com.forte.client.timetask.MyJob;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 打招呼的任务，每天中午12点报时
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/19 10:33
 * @since JDK1.8
 **/
public class HelloJob implements MyJob {

    /**
     * 群号
     */
    private static final String GROUP_CODE = "581250423";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        QQWebSocketMsgSender sender = (QQWebSocketMsgSender) jobExecutionContext.getMergedJobDataMap().get("sender");
        CQCodeUtil cqCodeUtil = (CQCodeUtil) jobExecutionContext.getMergedJobDataMap().get("cqCodeUtil");

        String[] arrs = new String[]{
                "中午啦！！！",
                "",
                "",
                "",
                "",
                "",
                "",
        };

        String str = RandomUtil.getRandomElement(arrs);

        sender.sendGroupMsg(GROUP_CODE, str);

    }



}
