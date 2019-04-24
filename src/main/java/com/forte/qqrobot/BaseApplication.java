package com.forte.qqrobot;

import com.alibaba.fastjson.util.TypeUtils;
import com.forte.qqrobot.listener.DefaultWholeListener;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.utils.BaseLocalThreadPool;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 启动类总抽象类，在此实现部分通用功能
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:18
 * @since JDK1.8
 **/
public abstract class BaseApplication<CONFIG extends BaseConfiguration> {

    /**
     * 线程工厂初始化
     */
    protected void threadPoolInit(){
        BaseLocalThreadPool.setTimeUnit(TimeUnit.SECONDS);
        //空线程存活时间
        BaseLocalThreadPool.setKeepAliveTime(60);
        //线程池工厂
        AtomicLong nums = new AtomicLong(0);
        BaseLocalThreadPool.setDefaultThreadFactory(r -> new Thread(r, "ROBOT-" + nums.addAndGet(1) + "-Thread"));
        //核心池数量，可同时执行的线程数量
        BaseLocalThreadPool.setCorePoolSize(500);
        //线程池最大数量
        BaseLocalThreadPool.setMaximumPoolSize(1200);
        //对列策略
        BaseLocalThreadPool.setWorkQueue(new LinkedBlockingQueue<>());
    }

    /**
     * 公共资源初始化
     */
    protected void baseResourceInit(){
        //将CQCodeUtil放入资源调度中心
        ResourceDispatchCenter.saveCQCodeUtil(CQCodeUtil.build());
        //将DefaultWholeListener放入资源调度中心
        ResourceDispatchCenter.saveDefaultWholeListener(new DefaultWholeListener());
        //将ListenerMethodScanner放入资源调度中心
        ResourceDispatchCenter.saveListenerMethodScanner(new ListenerMethodScanner());

        //将ListenerFilter放入资源调度中心
        ResourceDispatchCenter.saveListenerFilter(new ListenerFilter());
    }

    /**
     * 开发者实现的资源初始化
     */
    protected abstract void resourceInit();

    /**
     * 初始化
     */
    public void init(){
        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
        TypeUtils.compatibleWithJavaBean = true;
        //公共资源初始化
        baseResourceInit();
        //资源初始化
        resourceInit();
        //线程工厂初始化
        threadPoolInit();
    }

    /**
     * 开发者实现的
     */
    protected abstract void start();



}
