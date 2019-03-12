package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.*;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 默认的监听器，实现全部接口，但是不做任何事情
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:39
 * @since JDK1.8
 **/
public class DefaultWholeListener implements EventFriendAddedListener,
                                                EventGroupAdminListener,
                                                EventGroupMemberJoinListener,
                                                EventGroupMemberReduceListener,
                                                MsgDisGroupListener,
                                                MsgGroupListener,
                                                MsgPrivateListener,
                                                RequestFriendListener,
                                                RequestGroupListener {
    @Override
    public boolean onMessage(EventFriendAdded msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(EventGroupAdmin msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(EventGroupMemberJoin msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(EventGroupMemberReduce msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(MsgDisGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(MsgPrivate msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(RequestFriend msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(RequestGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return false;
    }
}
