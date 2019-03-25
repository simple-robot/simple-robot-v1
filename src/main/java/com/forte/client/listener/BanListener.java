package com.forte.client.listener;

import com.forte.client.utils.ConstantData;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.inforeturn.ReturnGroupMember;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.msgget.MsgPrivate;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.listener.MsgPrivateListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import com.forte.qqrobot.utils.RegexUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/20 11:06
 * @since JDK1.8
 **/
public class BanListener implements MsgGroupListener, MsgPrivateListener {

    /**
     * 接收到群消息
     */
    @Filter(value = "(领取?套餐( +)?\\d+)|((领取?)?随机套餐)", keywordMatchType = KeywordMatchType.TRIM_REGEX)
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
            int timesByMin = 0;
            if(msg.getMsg().contains("随机")){
                //如果是随机，获取1-15直接的区间参数
                timesByMin = RandomUtil.getNumber$right(1, 15);
            }else{
                //获取他的套餐时间
                timesByMin = Integer.parseInt(RegexUtil.getMatcher(msg.getMsg(), "\\d+").get(0));
            }
            //设置禁言，保底1分钟
            sender.setGroupMemberBanned(fromQQ, fromGroup, timesByMin <= 0 ? 60 : (timesByMin*60L));
            return true;
        }
    }

    /**
     * 私聊解禁
     */
    @Filter(value = "取消套餐(\\d+)?" , keywordMatchType = KeywordMatchType.TRIM_REGEX)
    @Override
    public boolean onMessage(MsgPrivate msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        String fromQQ = msg.getFromQQ();

        if (LocalDate.now().getDayOfWeek().getValue() == 7) {
            //r如果是周天，通知不能解除
            sender.sendMsgPrivate(fromQQ, "今天是周一呢，真正的群主大人嘱托我说周一不提供解除服务呢~抱歉啦");
            return true;
        }

        List<String> groupCodeList = RegexUtil.getMatcher(msg.getMsg(), "\\d+");
        String groupCode = null;
        //如果没有群号，群号默认为581250423
        if(groupCodeList.size() == 0){
            groupCode = "581250423";
        }else{
            groupCode = groupCodeList.get(0);
        }

        //判断自己的权限
        ReturnGroupMember mine = sender.getGroupMember(groupCode, ConstantData.MY_QQ_CODE, "1");

        //如果对方的权限不小于自己的权限，通知他无法禁言
        if(mine.getPermission() <= 1){
            String returnMsg = "以我的权限来看，我可能帮不了你呢..";
            sender.sendMsgPrivate(fromQQ, returnMsg);
            return true;
        }else{
            //否则可以尝试取消禁言
            sender.setGroupMemberBanned(fromQQ, groupCode, 0L);
            sender.sendMsgPrivate(fromQQ, "我已经试着帮你解除了，如果没生效我也无能为力了呢..");
            return true;
        }

    }
}
