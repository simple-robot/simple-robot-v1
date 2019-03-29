package com.forte.qqrobot;

import com.forte.qqrobot.socket.QQWebSocketMsgSender;

/**
 * 启动器的接口
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:16
 * @since JDK1.8
 **/
public interface SimpleApplication {

    void before(LinkConfiguration configuration);

    void after(QQWebSocketMsgSender sender);

    default String author(){
        return "@ForteScarlet";
    }

}
