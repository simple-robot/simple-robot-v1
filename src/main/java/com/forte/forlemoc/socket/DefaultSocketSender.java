package com.forte.forlemoc.socket;

import com.forte.forlemoc.SocketResourceDispatchCenter;
import com.forte.forlemoc.beans.inforeturn.InfoReturn;

import java.util.function.Supplier;

/**
 * 继承Socket，作为不使用Socket连接的时候对空指针的处理
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/30 11:27
 * @since JDK1.8
 **/
public class DefaultSocketSender extends QQWebSocketMsgSender {

    /**
     * 私有构造
     */
    DefaultSocketSender(QQWebSocketClient client, QQJSONMsgCreator creator) {
        super(client, creator);
    }

    /**
     * 构造
     */
    public DefaultSocketSender(QQWebSocketClient client){
       super(client, SocketResourceDispatchCenter.getQQJSONMsgCreator());
    }

    /**
     * 发送消息 - 在一条新的线程中发送消息
     * 重写此方法，使其无法发送信息
     * @param text
     */
    @Override
    public void send(String text) { }

    /**
     * 获取信息 - 对消息的获取统一管理
     * 重写此方法，使其无法获取信息
     * @param act
     * @param sendStr
     * @param clazz
     */
    @Override
    public <T extends InfoReturn> T get(int act, Supplier<String> sendStr, Class<T> clazz) {
        return null;
    }
}
