package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.sender.senderlist.SenderSendList;

/**
 *
 * 提供一个sender对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SendContext extends SenderContext<SenderSendList> {
    public final SenderSendList SENDER;

    public SendContext(SenderSendList value, Object... params) {
        super(value, params);
        SENDER = value;
    }
}
