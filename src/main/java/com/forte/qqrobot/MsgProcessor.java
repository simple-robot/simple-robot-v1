package com.forte.qqrobot;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.types.ResultSelectType;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.util.function.Function;

/**
 *
 * 针对于{@link MsgProcessable}接口的实现，并提供一些有用的构造参数以简化开发与实现。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MsgProcessor implements MsgProcessable {

    /**
     * 监听回执筛选器
     */
    private ResultSelectType selectType;

    /**
     * 消息处理器
     */
    private ListenerManager listenerManager;

    /*
     * 对于三大送信器的构建函数
     */

    private Function<MsgGet, SenderSendList> senderFunc;
    private Function<MsgGet, SenderSetList> setterFunc;
    private Function<MsgGet, SenderGetList> getterFunc;

    /**
     * 构造
     * @param selectType        监听回执筛选器
     * @param listenerManager   监听函数管理器
     * @param senderFunc        sender构造器
     * @param setterFunc        setter构造器
     * @param getterFunc        getter构造器
     */
    public MsgProcessor(
            ResultSelectType selectType,
            ListenerManager listenerManager,
            Function<MsgGet, SenderSendList> senderFunc,
            Function<MsgGet, SenderSetList> setterFunc,
            Function<MsgGet, SenderGetList> getterFunc
    ){
        this.selectType = selectType;
        this.listenerManager = listenerManager;
        this.senderFunc = senderFunc;
        this.setterFunc = setterFunc;
        this.getterFunc = getterFunc;
    }

    /**
     * 处理监听消息
     * @param msgGet 监听消息
     * @return 监听响应
     */
    @Override
    public ListenResult[] onMsg(MsgGet msgGet) {
        return listenerManager.onMsg(msgGet, senderFunc, setterFunc, getterFunc);
    }

    /**
     * 经过筛选后的响应集
     * @param msgGet 监听消息
     * @return 执行并筛选后的监听回执
     */
    public ListenResult onMsgSelected(MsgGet msgGet){
        return selectType.filter(onMsg(msgGet));
    }

}
