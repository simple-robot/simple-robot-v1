package com.forte.qqrobot.scanner;


import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.exception.RobotRuntionException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.timetask.TimeTaskManager;
import com.forte.qqrobot.utils.FieldUtils;
import org.quartz.Job;

import java.util.Collection;
import java.util.Set;

/**
 * 扫描管理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ScannerManager {

    /**
     * 进行扫描
     */
    public static <CONFIG extends BaseConfiguration> void scanner(Collection<String> packages, CONFIG configuration){
        //获取扫描器
        FileScanner fileScanner = new FileScanner();

        //扫描
        for (String p : packages) {
            fileScanner.find(p);
        }

        //获取扫描结果
        Set<Class<?>> classes = fileScanner.get();

        //进行各类注册
        //加载监听器
        registerListener(classes);

        //加载定时任务
        registerTimeTask(classes);
    }

    /**
     * 注册其中的监听器
     */
    private static void registerListener(Set<Class<?>> classes){
        //获取监听器注册器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        //遍历并加载
        for (Class<?> c : classes) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(c);
                QQLog.info("加载["+ c +"]的监听函数成功：");
                scanSet.forEach(lm -> QQLog.info(">>>" + lm.getMethodToString()));
            } catch (Exception e) {
                throw new RobotRuntionException(e);
            }
        }

    }

    /**
     * 注册定时器
     */
    private static void registerTimeTask(Set<Class<?>> classes){
        //获取定时任务管理器
        TimeTaskManager timeTaskManager = ResourceDispatchCenter.getTimeTaskManager();

        //过滤出继承了Job接口的
        //遍历并尝试注册
        classes.stream().filter(c -> FieldUtils.isChild(c, Job.class)).forEach(c -> timeTaskManager.register((Class<? extends Job>)c));

    }



}
