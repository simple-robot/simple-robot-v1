package com.forte.qqrobot;

import com.alibaba.fastjson.util.TypeUtils;
import com.forte.qqrobot.configuration.LinkConfiguration;
import com.forte.qqrobot.listener.DefaultWholeListener;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.socket.*;
import com.forte.qqrobot.utils.BaseLocalThreadPool;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:10
 * @since JDK1.8
 **/
public abstract class RobotApplication extends BaseApplication<LinkConfiguration> {

    /**
     * 初始化
     */
    public static void init(){
        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
        TypeUtils.compatibleWithJavaBean = true;
        //资源初始化
        resourceInit();
        //线程工厂初始化
        threadPoolInit();
    }

    /**
     * 线程工厂初始化
     */
    private static void threadPoolInit(){
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
     * 资源初始化
     */
    private static void resourceInit(){
        //将连接管理器加入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketManager(new QQWebSocketManager());
        //将连接时的配置对象加入资源调度中心
        SocketResourceDispatchCenter.saveLinkConfiguration(new LinkConfiguration());
        //将CQCodeUtil放入资源调度中心
        SocketResourceDispatchCenter.saveCQCodeUtil(CQCodeUtil.build());
        //将QQWebSocketMsgCreator放入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketMsgCreator(new QQJSONMsgCreator());
        //将DefaultWholeListener放入资源调度中心
        SocketResourceDispatchCenter.saveDefaultWholeListener(new DefaultWholeListener());
        //将ListenerMethodScanner放入资源调度中心
        SocketResourceDispatchCenter.saveListenerMethodScanner(new ListenerMethodScanner());

        //将ListenerFilter放入资源调度中心
        SocketResourceDispatchCenter.saveListenerFilter(new ListenerFilter());
        //将QQWebSocketInfoReturnManager放入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketInfoReturnManager(new QQWebSocketInfoReturnManager());
    }

    /**
     * 连接socket
     */
    private static QQWebSocketClient lineSocket(){
        //连接配置对象
        LinkConfiguration configuration = SocketResourceDispatchCenter.getLinkConfiguration();
        //主要的socket连接，如果无法结果精准获取请求后的返回值的话，后期会同时连接多个socket
        //由于这里用到了config对象，需要
        return QQWebSocketLinker.link(configuration.getSocketClient(), configuration.getLinkUrl());
    }


    /**
     * 执行用主程序
     */
    public static void run(RobotApplication application) {
        //初始化方法
        init();
        //连接之前
        LinkConfiguration linkConfiguration = SocketResourceDispatchCenter.getLinkConfiguration();
        application.before(linkConfiguration);
        //连接之后，如果没有进行扫描，则默认扫描启动类同级包且排除此启动类
        String packageName = application.getClass().getPackage().getName();

        //如果没有扫描过，扫描本包全部
        linkConfiguration.scannerIfNotScanned(packageName, c -> !c.equals(application.getClass()));


        //连接socket
        QQWebSocketClient client = lineSocket();
//        //获取client
//        QQWebSocketClient mainSocket = manager.getMainSocket();

        //获取CQCodeUtil实例
        CQCodeUtil cqCodeUtil = SocketResourceDispatchCenter.getCQCodeUtil();

        //连接之后
        application.after(cqCodeUtil, QQWebSocketMsgSender.build(client));
    }


    /** socket连接或服务器启动 之前 */
//    public abstract void before(LinkConfiguration configuration);
    /** socket连接或服务器启动 之后 */
//    public abstract void after();


}
