/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MsgGetContext.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.util.Map;

/**
 *
 * 消息拦截器上下文对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MsgGetContext extends BaseContext<MsgGet> {

    /*
     * MsgGet对象。假如对此对象进行修改，会同样影响后续的监听
     * MsgGet对象保存在父类中。
     */

    /** 消息类型 */
    private MsgGetTypes types;

    /** 监听上下文 */
    private ListenContext listenContext;

    /** sender */
    public final SenderSendList SENDER;
    /** setter */
    public final SenderSetList  SETTER;
    /** getter */
    public final SenderGetList  GETTER;

    //**************** value ****************//

    public MsgGet getMsgGet() {
        return getValue();
    }

    public void setMsgGet(MsgGet msgGet) {
       setValue(msgGet);
    }

    //**************** element ****************//

    public MsgGetTypes getTypes() {
        return types;
    }

    public void setTypes(MsgGetTypes types) {
        this.types = types;
    }

    public ListenContext getListenContext(){
        return listenContext;
    }

    /**
     * 构造
     * @param msgGet    接收到的消息
     * @param context   监听上下文
     * @param sender    sender
     * @param setter    setter
     * @param getter    getter
     * @param globalContextMap  全局上下文Map
     */
    public MsgGetContext(MsgGet msgGet, ListenContext context, SenderSendList sender, SenderSetList setter, SenderGetList getter, Map<String, Object> globalContextMap){
        super(msgGet, globalContextMap);
        this.types = MsgGetTypes.getByType(msgGet);
        this.listenContext = context;
        this.SENDER = sender;
        this.SETTER = setter;
        this.GETTER = getter;
    }
}
