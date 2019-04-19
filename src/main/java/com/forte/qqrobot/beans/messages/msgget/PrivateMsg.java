package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.PrivateMsgType;

/**
 * 私信类型
 **/
public interface PrivateMsg extends MsgGet {

    /** 获取私聊消息类型 */
    PrivateMsgType getType();

    /** 获取发送人的QQ号 */
    String getQQ();

    /** 获取字体信息 */
    String getFont();

}
