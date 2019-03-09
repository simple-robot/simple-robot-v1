package com.forte.qqrobot.socket;

/**
 * QQWebSocket对儿，即一对相互对应的client 和 sender
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 16:01
 * @since JDK1.8
 **/
public class QQWebSocketPair {

    private final QQWebSocketClient client;
    private final QQWebSocketMsgSender sender;


    public QQWebSocketClient getClient() {
        return client;
    }

    public QQWebSocketMsgSender getSender() {
        return sender;
    }

    /**
     * 构造
     * @param client
     * @param sender
     */
    public QQWebSocketPair(QQWebSocketClient client, QQWebSocketMsgSender sender) {
        this.client = client;
        this.sender = sender;
    }
}
