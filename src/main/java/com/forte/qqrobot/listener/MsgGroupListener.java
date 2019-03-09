package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 群消息监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 11:38
 * @since JDK1.8
 **/
public interface MsgGroupListener {

    void OnMessage(MsgGroup msg, CQCode cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender);
}
