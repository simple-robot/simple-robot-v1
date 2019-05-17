package com.forte.qqrobot.timetask;

import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface TimeJob extends Job {

    /** 同名方法，获取并注入两个定时任务参数 */
    void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil);

    /** 仅用于获取定时任务参数 */
    default void execute(JobExecutionContext context) throws JobExecutionException{
       try{
           CQCodeUtil cqCodeUtil = TimeTaskContext.getCQCodeUtil(context);
           MsgSender msgSender = TimeTaskContext.getMsgSender(context);
           execute(msgSender, cqCodeUtil);
       }catch (Exception e){
           throw new TimeTaskException(e);
       }
    }

}
