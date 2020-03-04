package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.*;

import java.util.function.Function;


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
     * @param msgget MsgGet
     * @param sender 送信器
     * @return 监听回执
     */
    default ListenResult[] onMsg(MsgGet msgget, MsgSender sender){
        return onMsg(msgget,
                sender == null ? null : sender.SENDER,
                sender == null ? null : sender.SETTER,
                sender == null ? null : sender.GETTER);
    }

    /**
     * 接收到一条消息, 获取所有的监听回执
     * @param msgget MsgGet
     * @param sender 送信器三接口总列表
     * @return 监听回执
     */
    default ListenResult[] onMsg(MsgGet msgget, RootSenderList sender){
        return onMsg(msgget,
                sender.isSenderList() ? sender : null,
                sender.isSetterList() ? sender: null,
                sender.isGetterList() ? sender : null);
    }

    /**
     * 接收到一条消息, 获取所有的监听回执
     * @param msgGet 接收到的消息
     * @param send   消息发送器
     * @param set    状态设置器
     * @param get    信息获取器
     * @return 监听回执
     */
    ListenResult[] onMsg(MsgGet msgGet, SenderSendList send, SenderSetList set, SenderGetList get);


    /**
     * 根据函数构建三大送信器
     * @param msgget     消息接收器
     * @param senderFunc 送信器sender获取函数
     * @param setterFunc 送信器setter获取函数
     * @param getterFunc 送信器getter获取函数
     * @return 相应结果列表
     */
    default ListenResult[] onMsg(MsgGet msgget,
                                Function<MsgGet, SenderSendList> senderFunc,
                                Function<MsgGet, SenderSetList> setterFunc,
                                Function<MsgGet, SenderGetList> getterFunc
    ){

        // 返回最终结果
        return onMsg(msgget, senderFunc.apply(msgget), setterFunc.apply(msgget), getterFunc.apply(msgget));
    }

    /**
     * 根据函数构建三大送信器
     * @param msgget            消息接收器
     * @param senderListFunc    送信器三接口总列表
     * @return 相应结果列表
     */
    default ListenResult[] onMsg(MsgGet msgget, Function<MsgGet, RootSenderList> senderListFunc){
        return onMsg(msgget, senderListFunc.apply(msgget));
    }

}
