package com.forte.qqrobot.bot;

import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * 一个数据类，定义一个Bot中储存的三个送信器
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotSender {
    public final SenderSendList SENDER ;
    public final SenderSetList SETTER ;
    public final SenderGetList GETTER ;

    public BotSender(SenderSendList sender, SenderSetList setter, SenderGetList getter){
        this.SENDER = sender;
        this.SETTER = setter;
        this.GETTER = getter;
    }
    public BotSender(RootSenderList rootSender){
        this(rootSender, rootSender, rootSender);
    }

}
