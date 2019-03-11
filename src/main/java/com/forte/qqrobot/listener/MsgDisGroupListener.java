package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.EventGroupMemberReduce;
import com.forte.qqrobot.beans.msgget.MsgDisGroup;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:27
 * @since JDK1.8
 **/
public interface MsgDisGroupListener extends SocketListener{

    void onMessage(MsgDisGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender);

}
