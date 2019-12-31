package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;


/**
 *
 * 消息接收者接口，用于向外界用户提供消息接收接口
 * 返回值一般都是监听回执{@link com.forte.qqrobot.listener.result.ListenResult[]}数组
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MsgReceiver {

    /**
     * 接收到一条消息, 获取所有的监听回执
     * @param msgGet MsgGet
     * @param sender 送信器
     * @return 监听回执
     */
    ListenResult[] onMsg(MsgGet msgGet, MsgSender sender);

    /**
     * 接收到一条消息, 获取所有的监听回执
     * @param msgGet MsgGet
     * @param sender 送信器三接口总列表
     * @return 监听回执
     */
    ListenResult[] onMsg(MsgGet msgGet, SenderList sender);

    /**
     * 接收到一条消息, 获取所有的监听回执
     * @param msgGet 接收到的消息
     * @param send   消息发送器
     * @param set    状态设置器
     * @param get    信息获取器
     * @return 监听回执
     */
    ListenResult[] onMsg(MsgGet msgGet, SenderSendList send, SenderSetList set, SenderGetList get);

}
