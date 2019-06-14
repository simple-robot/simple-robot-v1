package com.forte.qqrobot.timetask;

import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * {@link TimeJob} 有时候去实现会出现问题，无法正确覆盖{@link Job}中的方法，故此提供一个新的抽象类以代替原本的TimeJob接口。
 * @author ForteScarlet <ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseTimeJob implements Job {

    /** 同名方法，获取并注入两个定时任务参数 */
    public abstract void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil);

    /** 仅用于获取定时任务参数 */
    @Override
    public void execute(JobExecutionContext context) {
       try{
           CQCodeUtil cqCodeUtil = TimeTaskContext.getCQCodeUtil(context);
           MsgSender msgSender = TimeTaskContext.getMsgSender(context);
           execute(msgSender, cqCodeUtil);
       }catch (Exception e){
           throw new TimeTaskException(e);
       }
    }

}
