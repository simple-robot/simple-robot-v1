package com.forte.qqrobot.timetask;

import org.quartz.Job;

/**
 * {@link TimeJob} 有时候去实现会出现问题，无法正确覆盖{@link Job}中的方法，故此提供一个新的抽象类以代替原本的TimeJob接口。
 * <br>
 * 由于有人出现过default方法会报错的问题，所以试试在接口列表里写个job能不能解决这个问题
 * @author ForteScarlet <ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseTimeJob implements TimeJob, Job {

//    /** 同名方法，获取并注入两个定时任务参数 */
//    public abstract void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil);
//
//    /** 仅用于获取定时任务参数 */
//    @Override
//    public void execute(JobExecutionContext context) {
//       try{
//           CQCodeUtil cqCodeUtil = TimeTaskContext.getCQCodeUtil(context);
//           MsgSender msgSender = TimeTaskContext.getMsgSender(context);
//           execute(msgSender, cqCodeUtil);
//       }catch (Exception e){
//           throw new TimeTaskException(e);
//       }
//    }

}
