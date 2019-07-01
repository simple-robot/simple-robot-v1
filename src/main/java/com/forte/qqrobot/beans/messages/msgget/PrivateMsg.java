package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.Flagable;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.types.PrivateMsgType;

/**
 * 私信消息
 **/
public interface PrivateMsg extends MsgGet, QQCodeAble, Flagable {

    /** 获取私聊消息类型 */
    PrivateMsgType getType();

    /** 获取发送人的QQ号 */
    String getQQ();

    @Override
    default String getQQCode(){
        return getQQ();
    }

    /** flag默认使用id */
    @Override
    default String getFlag(){
        return getId();
    }

}
