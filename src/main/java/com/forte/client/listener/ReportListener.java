package com.forte.client.listener;

import com.forte.client.listener.report.ReportHelper;
import com.forte.qqrobot.anno.Spare;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.msgget.MsgPrivate;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.listener.MsgPrivateListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DEMO-复读监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 11:59
 * @since JDK1.8
 **/
@Spare
public class ReportListener implements MsgGroupListener, MsgPrivateListener {

//    /** 复读记录map key为群号，value[0] 为上一次的消息，value[1] 为这个消息是否已经复读*/
//    private static Map<String, Object[]> map = new ConcurrentHashMap<>(1);

    /**
     * 接收到了群的消息，复读
     */
    @Override
    public boolean onMessage(MsgGroup msgGroup, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return ReportHelper.groupReport2(msgGroup, cqCode, at, cqCodeUtil, sender);
    }

    /**
     * 接收到了私聊信息
     */
    @Override
    public boolean onMessage(MsgPrivate msgPrivate, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        return ReportHelper.privateReport(msgPrivate, sender);
    }
}
