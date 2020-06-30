/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BotSender.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.bot;

import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * 一个数据类，定义一个Bot中储存的三个送信器
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotSender extends MsgSender {

    public BotSender(SenderSendList sender, SenderSetList setter, SenderGetList getter){
        super(sender, setter, getter, null, null);
    }
    public BotSender(RootSenderList rootSender){
        this(rootSender, rootSender, rootSender);
    }

    /**
     * 从BotSender中获取没啥意义
     */
    @Override
    @Deprecated
    public BotInfo bot(String botCode){
        return BotRuntime.getRuntime().getBotManager().getBot(botCode);
    }

    /**
     * 从BotSender中获取没啥意义
     */
    @Override
    @Deprecated
    public BotInfo bot(){
        return BotRuntime.getRuntime().getBotManager().defaultBot();
    }

    /**
     * 获取默认的Bot送信器
     * @return bot送信器
     */
    @Override
    public BotSender botSender(){
        return this;
    }

}
