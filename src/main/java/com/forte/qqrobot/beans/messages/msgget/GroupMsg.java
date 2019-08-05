package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.FlagAble;
import com.forte.qqrobot.beans.messages.types.GroupMsgType;

/**
 * 群消息
 **/
public interface GroupMsg extends MsgGet, CodesAble, FlagAble {

    /** 获取群消息发送人的qq号 */
    String getQQ();

    @Override
    default String getQQCode(){
        return getQQ();
    }

    /** 获取群消息的群号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 获取消息类型 */
    GroupMsgType getType();

    /** flag默认使用id */
    @Override
    default String getFlag(){
        return getId();
    }

}
