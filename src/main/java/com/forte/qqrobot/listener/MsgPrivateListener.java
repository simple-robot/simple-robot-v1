package com.forte.qqrobot.listener;


import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:28
 * @since JDK1.8
 **/
public interface MsgPrivateListener extends SocketListener{
    boolean onMessage(PrivateMsg msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender);
}
