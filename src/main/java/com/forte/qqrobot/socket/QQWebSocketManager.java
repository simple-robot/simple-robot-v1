package com.forte.qqrobot.socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * QQWebSocket管理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:26
 * @since JDK1.8
 **/
public class QQWebSocketManager {

    /** 用于保存全部的socketClient客户端 */
    private final Map<String, QQWebSocketClient> clientMap = new ConcurrentHashMap<>(3);

    /** 主socket的名称 */
    private static final String MAIN_SOCKET_NAME = "MAIN";

    /**
     * 保存一个socket
     * @param name
     * @param client
     */
    public void saveSocket(String name, QQWebSocketClient client){
        clientMap.putIfAbsent(name, client);
    }

    /**
     * 保存一个主客户端
     * @param client
     */
    public void saveMainSocket(QQWebSocketClient client){
        clientMap.putIfAbsent(MAIN_SOCKET_NAME, client);
    }

    /**
     * 获取一个socket
     * @param name
     * @return
     */
    public QQWebSocketClient getSocket(String name){
        return clientMap.get(name);
    }

    /**
     * 获取主socket
     * @return
     */
    public QQWebSocketClient getMainSocket(){
        return clientMap.get(MAIN_SOCKET_NAME);
    }


}
