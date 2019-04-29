package com.forte.qqrobot.listener;


import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:26
 * @since JDK1.8
 **/
@Deprecated
public interface EventGroupMemberJoinListener extends SocketListener {

    boolean onMessage(GroupMemberIncrease event, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender);
}
