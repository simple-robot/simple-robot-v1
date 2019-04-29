package com.forte.qqrobot.listener;


import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.*;
import com.forte.qqrobot.sender.MsgSender;
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
    public boolean onMessage(FriendAdd event, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(GroupAdminChange event, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(GroupMemberIncrease event, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(GroupMemberReduce event, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(DiscussMsg msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(GroupMsg msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(PrivateMsg msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(FriendAddRequest request, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }

    @Override
    public boolean onMessage(GroupAddRequest request, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, MsgSender sender) {
        return false;
    }
}
