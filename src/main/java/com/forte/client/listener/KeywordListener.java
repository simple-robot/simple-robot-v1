package com.forte.client.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.msgget.MsgPrivate;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.beans.types.MostType;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.listener.MsgPrivateListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 16:08
 * @since JDK1.8
 **/
public class KeywordListener implements MsgGroupListener, MsgPrivateListener {


    @Override
    @Filter(
            value = "mua.+",
            keywordMatchType = KeywordMatchType.REGEX
    )
    public boolean onMessage(MsgPrivate msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        sender.sendMsgPrivate(msg.getFromQQ(), msg.getMsg().replaceAll("mua", "rua"));
        return true;
    }

    @Override
    @Filter(
            value = {"aaaa", "bbbb"},
            keywordMatchType = KeywordMatchType.CONTAINS,
            mostType = MostType.ANY_MATCH,
            at = true
    )
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        System.out.println(msg);
        sender.sendGroupMsg(msg.getFromGroup(), "监听成功！");
        return true;
    }
}
