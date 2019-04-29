package com.forte.qqrobot;

import com.alibaba.fastjson.util.TypeUtils;
import com.forte.qqrobot.listener.DefaultWholeListener;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
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
    private void threadPoolInit(){
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
    private void baseResourceInit(){
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
     * 对fastJson进行配置
     */
    private void fastJsonInit(){
        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
        TypeUtils.compatibleWithJavaBean = true;
    }

    /**
     * 开发者实现的资源初始化
     */
    protected abstract void resourceInit();

    //**************** 获取三种送信器 ****************//

    /**
     * 获取消息发送接口, 将会在连接成功后使用
     */
    protected abstract SenderSendList getSender();

    /**
     * 获取事件设置接口, 将会在连接成功后使用
     */
    protected abstract SenderSetList getSetter();

    /**
     * 获取资源获取接口, 将会在连接成功后使用
     */
    protected abstract SenderGetList getGetter();

    /**
     * 开发者实现的启动方法
     * @param manager 监听管理器，用于分配获取到的消息
     */
    protected abstract void start(ListenerManager manager);

    /**
     * 开发者实现的获取Config对象的方法,对象请保证每次获取的时候都是唯一的
     */
    protected abstract CONFIG getConfiguration();

    /**
     * 初始化
     */
    private void init(){
        //配置fastJson
        fastJsonInit();
        //公共资源初始化
        baseResourceInit();
        //资源初始化
        resourceInit();
        //线程工厂初始化
        threadPoolInit();
    }

    /**
     * 配置结束后的方法
     */
    private void afterConfig(CONFIG config, Application<CONFIG> app){
        //配置完成后，如果没有进行扫描，则默认扫描启动类同级包且排除此启动类
        String packageName = app.getClass().getPackage().getName();
        //如果没有扫描过，扫描本包全部
        config.scannerIfNotScanned(packageName, c -> !c.equals(app.getClass()));


        //构建监听函数管理器等扫描器所构建的
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();

        //根据配置类的扫描结果来构建监听器管理器和阻断器
        ListenerManager manager = scanner.buildManager();
        Plug plug = scanner.buildPlug();

        //保存
        ResourceDispatchCenter.saveListenerManager(manager);
        ResourceDispatchCenter.savePlug(plug);
    }

    /**
     * 执行的主程序
     */
    public void run(Application<CONFIG> app){
        //先初始化
        init();

        //获取配置对象
        CONFIG configuration = getConfiguration();
        //开始之前
        app.before(configuration);

        //配置结束
        afterConfig(configuration, app);

//        ListenerManager manager = ResourceDispatchCenter.getListenerMethodScanner().buildManager();
//        ListenerPlug plug = ResourceDispatchCenter.getListenerPlug();

        //获取管理器
        ListenerManager manager = ResourceDispatchCenter.getListenerManager();

        //开始连接
        start(manager);

        //获取CQCodeUtil实例
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();

        //构建没有监听函数的送信器
        MsgSender sender = MsgSender.build(getSender(), getSetter(), getGetter());

        //连接之后
        app.after(cqCodeUtil, sender);

    }


}
