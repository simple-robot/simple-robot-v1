package com.forte.client.listener;

import com.forte.client.utils.ConstantData;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.inforeturn.ReturnGroupMember;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RegexUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/20 11:06
 * @since JDK1.8
 **/
public class BanListener implements MsgGroupListener {

    /**
     * 接收到群消息
     */
    @Filter(value = "领取?套餐( +)?\\d+", keywordMatchType = KeywordMatchType.TRIM_REGEX)
    @Override
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        //判断这个人在群里的权限
        String fromGroup = msg.getFromGroup();
        String fromQQ = msg.getFromQQ();
        //判断群员的权限
        ReturnGroupMember groupMember = sender.getGroupMember(fromGroup, fromQQ, "1");
        //判断自己的权限
        ReturnGroupMember mine = sender.getGroupMember(fromGroup, ConstantData.MY_QQ_CODE, "1");

        //如果对方的权限不小于自己的权限，通知他无法禁言
        if(groupMember.getPermission() >= mine.getPermission()){

            String cqCode_at = cqCodeUtil.getCQCode_at(fromQQ);
            String returnMsg = cqCode_at + " 以你的权限来看，我给不了你想要的套餐呢..";
            sender.sendGroupMsg(fromGroup, returnMsg);
            return true;
        }else{
            //否则可以禁言
            //获取他的套餐时间
            int timesByMin = Integer.parseInt(RegexUtil.getMatcher(msg.getMsg(), "\\d+").get(0));

            //设置禁言，保底1分钟
            sender.setGroupMemberBanned(fromQQ, fromGroup, timesByMin <= 0 ? 60 : (timesByMin*60L));
            return true;
        }
    }
}
