package com.forte.qqrobot;

import com.alibaba.fastjson.util.TypeUtils;
import com.forte.qqrobot.config.LinkConfiguration;
import com.forte.qqrobot.listener.DefaultWholeListener;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerInvoker;
import com.forte.qqrobot.socket.*;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:10
 * @since JDK1.8
 **/
public abstract class RobotApplication {

//    /** socket对象 */
//    public static QQWebSocketManager manager;

    /** 主客户端 */
    public static QQWebSocketClient mainClient;

    /**
     * 初始化
     */
    public static void init(){
        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
        TypeUtils.compatibleWithJavaBean = true;
        //资源初始化
        resourceInit();
    }



    /**
     * 资源初始化
     */
    private static void resourceInit(){
        //将连接管理器加入资源调度中心
        ResourceDispatchCenter.saveQQWebSocketManager(new QQWebSocketManager());
        //将连接时的配置对象加入资源调度中心
        ResourceDispatchCenter.saveLinkConfiguration(new LinkConfiguration());
        //将CQCodeUtil放入资源调度中心
        ResourceDispatchCenter.saveCQCodeUtil(new CQCodeUtil());
        //将QQWebSocketMsgCreator放入资源调度中心
        ResourceDispatchCenter.saveQQWebSocketMsgCreator(new QQWebSocketMsgCreator());
        //将DefaultWholeListener放入资源调度中心
        ResourceDispatchCenter.saveDefaultWholeListener(new DefaultWholeListener());
        //将ListenerInvoker放入资源调度中心
        ResourceDispatchCenter.saveListenerInvoker(new ListenerInvoker());
        //将ListenerFilter放入资源调度中心
        ResourceDispatchCenter.saveListenerFilter(new ListenerFilter());
    }

    /**
     * 获取配置
     * @return
     */
    public static LinkConfiguration getLinkConfiguration(){
        return ResourceDispatchCenter.getLinkConfiguration();
    }

    /**
     * 连接socket
     */
    private static QQWebSocketManager lineSocket(){
        //连接管理器
        QQWebSocketManager manager = ResourceDispatchCenter.getQQWebSocketManager();
        //连接配置对象
        LinkConfiguration configuration = ResourceDispatchCenter.getLinkConfiguration();
        //主要的socket连接，如果无法结果精准获取请求后的返回值的话，后期会同时连接多个socket
        //由于这里用到了config对象，需要
        mainClient = QQWebSocketLinker.link(configuration.getSocketClient(), configuration.getLinkUrl());
        manager.saveMainSocket(mainClient);

        return manager;
    }


    /**
     * 执行用主程序
     * @param application
     */
    public static void run(RobotApplication application) {
        //初始化方法
        init();
        //连接之前
        application.beforeLink(ResourceDispatchCenter.getLinkConfiguration());
        //连接socket
        lineSocket();
        //连接之后
        application.afterLink(/* 连接socket */);

    }


    /** socket连接之前 */
    public abstract void beforeLink(LinkConfiguration configuration);
    /** socket连接之后 */
    public abstract void afterLink();


}
