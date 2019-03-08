package com.forte.qqrobot;

import com.forte.qqrobot.config.LinkConfiguration;
import com.forte.qqrobot.socket.QQWebSocketClient;
import com.forte.qqrobot.socket.QQWebSocketLinker;
import com.forte.qqrobot.socket.QQWebSocketManager;
import com.forte.qqrobot.utils.SingleFactory;
import org.quartz.SchedulerException;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:10
 * @since JDK1.8
 **/
public abstract class RobotApplication {

    /** socket对象 */
    public static QQWebSocketManager manager = new QQWebSocketManager();

    /** 主客户端 */
    public static QQWebSocketClient mainClient;

    /** 自身对象 */
    private static RobotApplication application;

    /** 连接时的配置 */
    private static final LinkConfiguration configuration = new LinkConfiguration();

    /**
     * 获取配置
     * @return
     */
    public static LinkConfiguration getLinkConfiguration(){
        return configuration;
    }

    /**
     * 连接socket
     */
    private static QQWebSocketManager lineSocket(){
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
        //连接之前
        application.beforeLink(configuration);
        //连接socket
        QQWebSocketManager qqWebSocketManager = lineSocket();
        //连接之后
        application.afterLink(qqWebSocketManager);
    }

    /** socket连接之前 */
    public abstract void beforeLink(LinkConfiguration configuration);
    /** socket连接之后 */
    public abstract void afterLink(QQWebSocketManager manager);


}
