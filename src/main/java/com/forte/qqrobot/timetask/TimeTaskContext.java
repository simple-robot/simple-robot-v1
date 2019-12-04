package com.forte.qqrobot.timetask;

import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * 此类仅用于定义定时任务注册时，注入的参数的key值以及其对应的数据类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TimeTaskContext {

    private static final String MSG_SENDER_KEY = "msg_sender";

    private static final String CQ_CODE_UTIL_KEY = "cq_code_util";

    private static final String DEPEND_CENTER = "depend_center";


    /** 从一个Context中取出MsgSender */
    public static MsgSender getMsgSender(JobExecutionContext context){
        return get(context, MSG_SENDER_KEY, MsgSender.class);
    }

    /** 为一个JobDataMap赋值 */
    public static void giveMsgSender(JobDataMap jobDataMap, MsgSender sender){
        give(jobDataMap, MSG_SENDER_KEY, sender);
    }

    /** 获取一个CQCodeUtil */
    public static CQCodeUtil getCQCodeUtil(JobExecutionContext context){
        return get(context, CQ_CODE_UTIL_KEY, CQCodeUtil.class);
    }

    /** 保存一个CQCodeUtil */
    public static void giveCQCodeUtil(JobDataMap jobDataMap, CQCodeUtil cqCodeUtil){
        give(jobDataMap, CQ_CODE_UTIL_KEY, cqCodeUtil);
    }

    /** 从一个Context中取出DependCenter */
    public static DependCenter getDependCenter(JobExecutionContext context){
        return get(context, DEPEND_CENTER, DependCenter.class);
    }

    /** 保存一个DependCenter */
    public static void giveDependCenter(JobDataMap jobDataMap, DependCenter dependCenter){
        give(jobDataMap, DEPEND_CENTER, dependCenter);
    }


    //**************************************
    //**************************************


    /** 获取一个数据 */
    private static <T> T get(JobExecutionContext context, String key, Class<T> type){
        return (T) context.getMergedJobDataMap().get(key);
    }

    /** 储存一个数据 */
    private static <T> void give(JobDataMap jobDataMap, String key, T value){
        jobDataMap.put(key, value);
    }

}
