package com.forte.qqrobot.sender.senderlist;

import java.util.Map;

/**
 * 消息发送总控接口，提供一个消息发送的总控接口。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderSend {

    /**
     * 消息发送
     * @param params 参数键值对
     * @return  是否成功
     */
    boolean send(Map<String, String> params);


}
