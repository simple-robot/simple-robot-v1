/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     TimeTaskManager.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.timetask;

import com.forte.lang.Language;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.anno.timetask.FixedRateTask;
import com.forte.qqrobot.anno.timetask.TypeTask;
import com.forte.qqrobot.beans.types.TimeTaskTemplate;
import com.forte.qqrobot.beans.types.TimeType;
import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 定时任务控制中心
 * 当一个类存在多个定时任务的注解的时候，将会按照类型全部加载
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TimeTaskManager {

    /** 与定时任务有关的注解列表 */
    private static final Class<Annotation>[] timeTaskAnnos = new Class[]{
            CronTask.class, FixedRateTask.class, TypeTask.class
    };

    /**触发器名称前缀 */
    private static final String TRIGGER_NAME  = "trigger_";

    private static final String JOBDETAIL_NAME = "job_";

    /** 已经注册了的数量。初始量为0 */
    private int registerNum = 0;

    /** 尝试加载一个疑似是定时任务的类 */
    public void register(Class<? extends Job> timeTaskClass, MsgSender sender, StdSchedulerFactory factory){
        List<Annotation> timeTaskAnnos = isTimeTask(timeTaskClass);
        if(timeTaskAnnos.size() > 0){
            //遍历
            timeTaskAnnos.forEach(a -> register(timeTaskClass, a, sender, factory));
            registerNum += timeTaskAnnos.size();
        }
    }

    /** 尝试加载一个疑似是定时任务的类 */
    public void register(Class<? extends Job> timeTaskClass, Supplier<MsgSender> senderSupplier, StdSchedulerFactory factory){
        List<Annotation> timeTaskAnnos = isTimeTask(timeTaskClass);
        if(timeTaskAnnos.size() > 0){
            //遍历
            timeTaskAnnos.forEach(a -> register(timeTaskClass, a, senderSupplier.get(), factory));
            registerNum += timeTaskAnnos.size();
        }
    }

    /**
     * 启动定时任务
     */
    public void start(StdSchedulerFactory factory) throws SchedulerException {
        // 只有注册量在0以上的时候才初始化定时任务框架
        if(registerNum > 0){
            factory.getScheduler().start();
        }
    }


    /**
     * 判断一个Class对象是否为需要加载的定时任务
     */
    private static List<Annotation> isTimeTask(Class<? extends Job> clazz){
        //存在的定时任务注解
        List<Annotation> annoList = new ArrayList<>();
        //首先判断是否存在注解
        for(Class<Annotation> tc : timeTaskAnnos){
            Annotation an = AnnotationUtils.getAnnotation(clazz, tc);
            if(an != null){
                annoList.add(an);
            }
        }

        return annoList;
    }


    /**
     * 注册至定时任务调度器
     */
    private static void register(Class<? extends Job> clazz, Annotation annotation, MsgSender sender, StdSchedulerFactory factory){
        //判断注解类型
        Scheduler scheduler;
        try {
            scheduler = factory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new TimeTaskException("schedulerInitFailed", e);
        }

        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = null;
        Trigger trigger = null;
        String name = null;

        //创建可以提供的参数集合


        if(annotation instanceof CronTask){
            //如果是cron定时任务
            CronTask cronTask = (CronTask)annotation;
            name = cronTask.id();
            name = name.trim().length() <= 0 ? clazz.getSimpleName() : name;
            //创建定时任务触发器与Job实例
            jobDetail = getJobDetail(clazz, name);
            //创建定时触发器
            trigger = getTrigger(cronTask, name);
        }else if(annotation instanceof FixedRateTask){
            //如果是时间周期定时任务
            FixedRateTask fixedRateTask = (FixedRateTask)annotation;
            name = fixedRateTask.id();
            name = name.trim().length() <= 0 ? clazz.getSimpleName() : name;
            //创建定时任务触发器与Job实例
            jobDetail = getJobDetail(clazz, name);
            //创建定时触发器
            trigger = getTrigger(fixedRateTask, name);

        }else if(annotation instanceof TypeTask){
            //如果是模板定时任务
            TypeTask typeTask = (TypeTask)annotation;
            name = typeTask.id();
            name = name.trim().length() <= 0 ? clazz.getSimpleName() : name;
            //创建定时任务触发器与Job实例
            jobDetail = getJobDetail(clazz, name);
            //创建定时触发器
            trigger = getTrigger(typeTask, name);
        }

        if(jobDetail != null && trigger != null){
            //注册定时任务
            try {
                //向Job中注入参数
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                //保存一个送信器实例
                TimeTaskContext.giveMsgSender(jobDataMap, sender);
                //保存一个cq工具类实例
                TimeTaskContext.giveCQCodeUtil(jobDataMap, CQCodeUtil.build());
                //保存一个依赖工厂，以实现定时任务内的依赖注入。
                TimeTaskContext.giveDependCenter(jobDataMap, ResourceDispatchCenter.getDependCenter());


                scheduler.scheduleJob(jobDetail, trigger);
                QQLog.info("run.timeTask.loadSuccess", name);
            } catch (SchedulerException e) {
                throw new TimeTaskException("registerFailed", e, name);
            }
        }else{
            String why = jobDetail == null ?
                    Language.format("exception.dependResource.initFailed", JobDetail.class)
                    :
                    Language.format("exception.dependResource.initFailed", Trigger.class);
            throw new TimeTaskException("registerWhyFailed", name, why);
        }

    }

    //**************** - ****************//


    /** 获取定时任务实例 */
    private static JobDetail getJobDetail(Class<? extends Job> clazz, String name){
        return JobBuilder.newJob(clazz).withIdentity(getJobName(name)).build();
    }

    /** 获取触发器 */
    private static Trigger getTrigger(CronTask cronTask, String name){
        String cron = cronTask.value();
        String identity = getTriggerName(name);
        return TriggerBuilder.newTrigger().withIdentity(identity).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
    }
    /** 获取触发器 */
    private static Trigger getTrigger(FixedRateTask fixedRateTask, String name){
        long time = fixedRateTask.value();
        TimeType timeType = fixedRateTask.timeType();
        int repeatCount = fixedRateTask.RepeatCount();
        repeatCount = Math.max(repeatCount, -1);
        String identity = getTriggerName(name);

        //获取Builder
        SimpleScheduleBuilder simpleScheduleBuilder = timeType.getSimpleScheduleBuilder(time).withRepeatCount(repeatCount);
        //返回结果
        return TriggerBuilder.newTrigger().withIdentity(identity).withSchedule(simpleScheduleBuilder).build();
    }
    /** 获取触发器 */
    private static Trigger getTrigger(TypeTask typeTask, String name){
        TimeTaskTemplate value = typeTask.value();
        return value.getTrigger(getTriggerName(name));
    }

    private static String getTriggerName(String name){
        return TRIGGER_NAME + name;
    }

    private static String getJobName(String name){
        return JOBDETAIL_NAME + name;
    }

}
