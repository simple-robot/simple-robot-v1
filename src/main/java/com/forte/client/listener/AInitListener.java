package com.forte.client.listener;

import com.forte.qqrobot.beans.inforeturn.ReturnGroupMember;
import com.forte.qqrobot.beans.inforeturn.ReturnLoginNick;
import com.forte.qqrobot.beans.inforeturn.ReturnLoginQQ;
import com.forte.qqrobot.beans.inforeturn.ReturnStranger;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/19 11:01
 * @since JDK1.8
 **/
public class AInitListener implements InitListener {
    /**
     * 初始化方法
     *
     * @param cqCodeUtil
     * @param sender
     */
    @Override
    public void init(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
//        ReturnLoginQQ loginQQ = sender.getLoginQQ();
//        System.out.println("loginQQ : " + loginQQ);
//        ReturnGroupMember groupMember = sender.getGroupMember("581250423", "1149159218", "1");
//        System.out.println("groupMember : " + groupMember);
//        ReturnLoginNick loginNick = sender.getLoginNick();
//        System.out.println("loginNick : " + loginNick);
//        ReturnStranger strangerInfo = sender.getStrangerInfo("469207122", "1");
//        System.out.println("strangerInfo : " + strangerInfo);
    }
}
