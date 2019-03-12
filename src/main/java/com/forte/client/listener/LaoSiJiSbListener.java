package com.forte.client.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 18:56
 * @since JDK1.8
 **/
public class LaoSiJiSbListener implements MsgGroupListener {

    /**
     * 监听群消息
     */
    @Override
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        String m = msg.getMsg();
        if(m.trim().matches("老司机[!\\！。\\.\\?？]*")){
            sender.sendGroupMsg(msg.getFromGroup(), m.replaceAll("老司机", "傻逼"));
        }else if(m.trim().matches("傻逼[!\\！。\\.\\?？]*")){
            sender.sendGroupMsg(msg.getFromGroup(),  m.replaceAll("傻逼", "老司机"));
        }else{
            return false;
        }
        return true;
    }
}
