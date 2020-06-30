/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     DefaultBotSender.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot;

import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * 默认为BotManager的defaultBot的送信器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DefaultBotSender extends MsgSender {

    /**
     * 构造
     *
     * @param sender
     * @param setter
     * @param getter
     * @param listenerMethod
     * @param runtime
     */
    public DefaultBotSender(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod, BotRuntime runtime) {
        super(sender, setter, getter, listenerMethod, runtime);
    }

    /**
     * 构造
     *
     * @param sender
     * @param setter
     * @param getter
     * @param listenerMethod
     * @param runtime
     * @param interceptSend
     * @param interceptSet
     * @param interceptGet
     */
    public DefaultBotSender(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod, BotRuntime runtime, boolean interceptSend, boolean interceptSet, boolean interceptGet) {
        super(sender, setter, getter, listenerMethod, runtime, interceptSend, interceptSet, interceptGet);
    }
}
