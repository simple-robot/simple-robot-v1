package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.types.GroupMsgType;

/**
 * 群消息
 **/
public interface GroupMsg extends MsgGet, GroupCodeAble {

    /** 获取群消息发送人的qq号 */
    String getQQ();

    /** 获取群消息的群号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 获取字体信息 */
    String getFont();

    /** 获取消息类型 */
    GroupMsgType getType();

    /** 获取群名称 */
    String getName();

}
