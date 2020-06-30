/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ScannerManager.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.scanner;


import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeTaskManager;
import com.forte.qqrobot.utils.FieldUtils;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 扫描管理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ScannerManager implements Register {

    /** 扫描出来的class类对象集 */
    private final Set<Class<?>> classes;

    /** 私有构造 */
    private ScannerManager(Set<Class<?>> classes){
        this.classes = classes;
    }

    /**
     * 进行扫描
     */
    public static ScannerManager scanner(Collection<String> packages){
        //获取扫描器
        FileScanner fileScanner = new FileScanner();

        //扫描
        for (String p : packages) {
            fileScanner.find(p);
        }

        //获取扫描结果
        Set<Class<?>> classes = fileScanner.get();

        //返回实例对象
       return new ScannerManager(classes);
    }



    /**
     * 直接获取实例
     */
    public static ScannerManager getInstance(Set<Class<?>> classes){
        return new ScannerManager(classes);
    }

    @Override
    public void addClasses(Collection<Class<?>> plusClasses) {
        if(plusClasses != null){
            this.classes.addAll(plusClasses);
        }
    }

    /**
     * 注册其中的监听器
     */
    private void registerListener(ListenerMethodScanner scanner, Set<Class<?>> classes){
        //遍历并加载
        for (Class<?> c : classes) {
            // 如果c是个接口或者抽象类，则直接跳过
            int modifiers = c.getModifiers();
            if(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)){
                continue;
            }
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(c);
                if(!scanSet.isEmpty()){
                    scanSet.forEach(lm -> QQLog.info("run.listen.load.success", lm.getUUID() + " " + Arrays.toString(lm.getTypes())));
                }
            } catch (Exception e) {
                throw new RobotRuntimeException(e);
            }
        }

    }

    /** 注册监听器 */
    @Override
    public void registerListener(ListenerMethodScanner scanner){
        registerListener(scanner, classes);
    }

    /**
     * 注册定时器
     */
    private void registerTimeTask(Set<Class<?>> classes, MsgSender sender, TimeTaskManager timeTaskManager, StdSchedulerFactory factory){
        //过滤出继承了Job接口的
        //遍历并尝试注册
        classes.stream().filter(c -> FieldUtils.isChild(c, Job.class)).forEach(c -> timeTaskManager.register((Class<? extends Job>)c, sender, factory));

        //全部注册完毕后，启动定时任务
        try {
            timeTaskManager.start(factory);
        } catch (SchedulerException e) {
            throw new TimeTaskException("startFailed", e);
        }
    }

    /**
     * 注册定时器
     */
    private void registerTimeTask(Set<Class<?>> classes, Supplier<MsgSender> senderSupplier, TimeTaskManager timeTaskManager, StdSchedulerFactory factory){
        //过滤出继承了Job接口的
        //遍历并尝试注册
        classes.stream().filter(c -> FieldUtils.isChild(c, Job.class)).forEach(c -> timeTaskManager.register((Class<? extends Job>)c, senderSupplier, factory));

        //全部注册完毕后，启动定时任务
        try {
            timeTaskManager.start(factory);
        } catch (SchedulerException e) {
            throw new TimeTaskException("startFailed", e);
        }
    }

    /**
     * 注册定时任务
     */
    @Override
    public void registerTimeTask(MsgSender sender, TimeTaskManager timeTaskManager, StdSchedulerFactory factory){
        registerTimeTask(classes, sender, timeTaskManager, factory);
    }

    /**
     * 注册定时任务
     */
    @Override
    public void registerTimeTask(Supplier<MsgSender> senderSupplier, TimeTaskManager timeTaskManager, StdSchedulerFactory factory){
        registerTimeTask(classes, senderSupplier, timeTaskManager, factory);
    }

    /**
     * 进行依赖注入
     */
    @Override
    public void registerDependCenter(DependCenter dependCenter) {
        //通过注解加载依赖
        dependCenter.load(classes);
    }

    /**
     * 不需要@Beans注解的依赖注入
     */
    @Override
    public void registerDependCenterWithoutAnnotation(Beans beans) {
        DependCenter dependCenter = ResourceDispatchCenter.getDependCenter();
        //通过注解加载依赖
        //注入依赖，不再需要携带@Beans
        dependCenter.load(beans, c -> (!c.isInterface()) && (!Modifier.isAbstract(c.getModifiers())), classes.toArray(new Class[0]));
    }




    /**
     * 执行一些其他的，可能是自定义的任务
     * @param filter    过滤器，根据需求获取到你所需要的class类型，不会为空
     * @param task      你要执行的任务。参数为过滤好的Class数组
     */
    @Override
    public <T> T performingTasks(Predicate<? super Class<?>> filter,
                         Function<Class<?>[], T> task){

        return task.apply(classes.stream().filter(filter).toArray(Class<?>[]::new));
    }


}
