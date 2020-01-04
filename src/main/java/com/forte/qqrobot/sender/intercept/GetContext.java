package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.sender.senderlist.SenderGetList;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class GetContext extends SenderContext<SenderGetList> {

    public final SenderGetList GETTER;

    public GetContext(SenderGetList value, Object... params) {
        super(value, params);
        GETTER = value;
    }
}
