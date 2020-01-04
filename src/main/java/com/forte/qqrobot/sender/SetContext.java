package com.forte.qqrobot.sender;

import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SetContext extends SenderContext<SenderSetList> {

    public final SenderSetList SETTER;

    public SetContext(SenderSetList value, Object... params) {
        super(value, params);
        SETTER = value;
    }
}
