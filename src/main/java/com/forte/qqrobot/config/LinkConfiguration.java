package com.forte.qqrobot.config;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.socket.QQWebSocketClient;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;

import java.util.*;

/**
 * 连接前的配置类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 15:24
 * @since JDK1.8
 **/
public final class LinkConfiguration {

    /** 连接地址，默认为localhost */
    private String linkIp = "localhost";

    /** 连接端口号，默认为25303 */
    private Integer port = 25303;

    /** 连接用的客户端class对象 */
    private Class<? extends QQWebSocketClient> socketClient = QQWebSocketClient.class;

    /** 全部监听器 */
    private Set<SocketListener> listenerSet = new HashSet<>();


    /**
     * 注册监听器
     * @param listeners 监听器
     */
    public void registerListeners(SocketListener... listeners){
        listenerSet.addAll(Arrays.asList(listeners));
    }

    /**
     * 获取所有监听器，如果没有注册监听器则使用默认的监听器
     * @return 获取所有注册的监听器
     */
    public Set<SocketListener> getListeners(){
        return Optional.of(listenerSet).filter(s -> s.size() > 0).orElseGet(() -> {
                    Set<SocketListener> set = new HashSet<>();
                    set.add(ResourceDispatchCenter.getDefaultWholeListener());
                    return set;
                });
    }

    /** 获取连接的完整地址 */
    public String getLinkUrl(){
        return "ws://"+ linkIp +":" + port;
    }



    public String getLinkIp() {
        return linkIp;
    }

    public void setLinkIp(String linkIp) {
        this.linkIp = linkIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Class<? extends QQWebSocketClient> getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Class<? extends QQWebSocketClient> socketClient) {
        this.socketClient = socketClient;
    }
}
