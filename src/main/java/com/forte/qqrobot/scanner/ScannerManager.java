package com.forte.qqrobot.scanner;


import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.RobotRuntionException;
import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeTaskManager;
import com.forte.qqrobot.utils.FieldUtils;
import org.quartz.Job;
import org.quartz.SchedulerException;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
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

    /**
     * 注册其中的监听器
     */
    private void registerListener(Set<Class<?>> classes){
        //获取监听器注册器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        //遍历并加载
        for (Class<?> c : classes) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(c);
                if(!scanSet.isEmpty()){
                    QQLog.info("加载["+ c +"]的监听函数成功：");
                    scanSet.forEach(lm -> QQLog.info(">>>" + lm.getMethodToString()));
                }
            } catch (Exception e) {
                throw new RobotRuntionException(e);
            }
        }

    }

    /** 注册监听器 */
    @Override
    public void registerListener(){
        registerListener(classes);
    }

    /**
     * 注册定时器
     */
    private void registerTimeTask(Set<Class<?>> classes, MsgSender sender){
        //获取定时任务管理器
        TimeTaskManager timeTaskManager = ResourceDispatchCenter.getTimeTaskManager();

        //过滤出继承了Job接口的
        //遍历并尝试注册
        classes.stream().filter(c -> FieldUtils.isChild(c, Job.class)).forEach(c -> timeTaskManager.register((Class<? extends Job>)c, sender));

        //全部注册完毕后，启动定时任务
        try {
            timeTaskManager.start();
        } catch (SchedulerException e) {
            throw new TimeTaskException("定时任务启动失败！", e);
        }
    }

    /**
     * 注册定时器
     */
    private void registerTimeTask(Set<Class<?>> classes, Supplier<MsgSender> senderSupplier){
        //获取定时任务管理器
        TimeTaskManager timeTaskManager = ResourceDispatchCenter.getTimeTaskManager();

        //过滤出继承了Job接口的
        //遍历并尝试注册
        classes.stream().filter(c -> FieldUtils.isChild(c, Job.class)).forEach(c -> timeTaskManager.register((Class<? extends Job>)c, senderSupplier));

        //全部注册完毕后，启动定时任务
        try {
            timeTaskManager.start();
        } catch (SchedulerException e) {
            throw new TimeTaskException("定时任务启动失败！", e);
        }
    }

    /**
     * 注册定时任务
     */
    @Override
    public void registerTimeTask(MsgSender sender){
        registerTimeTask(classes, sender);
    }

    /**
     * 注册定时任务
     */
    @Override
    public void registerTimeTask(Supplier<MsgSender> senderSupplier){
        registerTimeTask(classes, senderSupplier);
    }

    /**
     * 进行依赖注入
     */
    @Override
    public void registerDependCenter() {
        DependCenter dependCenter = ResourceDispatchCenter.getDependCenter();
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
}
