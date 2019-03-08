package com.forte.client;

import com.forte.qqrobot.socket.QQWebSocketClient;

import java.net.URI;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 15:22
 * @since JDK1.8
 **/
public class MyRobotApplication extends QQWebSocketClient {
    /**
     * 构造
     *
     * @param serverURI
     */
    public MyRobotApplication(URI serverURI) {
        super(serverURI);
    }

}
