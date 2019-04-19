package com.forte.qqrobot.configuration;

import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.socket.QQWebSocketClient;

/**
 * 连接前的配置类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 15:24
 * @since JDK1.8
 **/
public final class LinkConfiguration extends BaseConfiguration {

    /** 连接地址，默认为localhost */
    private String linkIp = "localhost";

    /** 连接端口号，默认为25303 */
    private Integer port = 25303;

    /** 服务器监听端口 for HTTP API */
    private Integer listenerPort = 15514;


    /** 连接用的客户端class对象，默认为{@link QQWebSocketClient} */
    private Class<? extends QQWebSocketClient> socketClient = QQWebSocketClient.class;

    /*  ————————————————  参数配置 ———————————————— */

    /** 获取连接的完整地址 */
    public String getLinkUrl(){
        return "ws://"+ linkIp +":" + port;
    }



    /* —————————————— getter & setter —————————————— */

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

    public Integer getListenerPort() {
        return listenerPort;
    }

    public void setListenerPort(Integer listenerPort) {
        this.listenerPort = listenerPort;
    }

}
