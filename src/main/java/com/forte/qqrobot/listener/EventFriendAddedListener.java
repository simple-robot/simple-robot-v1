package com.forte.qqrobot.listener;


import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.FriendAdd;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:24
 * @since JDK1.8
 **/
@Deprecated
public interface EventFriendAddedListener extends SocketListener {
    boolean onMessage(FriendAdd event, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender);
}
