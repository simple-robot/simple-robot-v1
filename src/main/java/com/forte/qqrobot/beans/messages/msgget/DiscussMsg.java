package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.GroupMsgType;

/**
 * 讨论组消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface DiscussMsg extends MsgGet {

    /** 获取讨论组号 */
    String getGroup();

    /** 获取发消息的人的QQ */
    String getQQ();



}
