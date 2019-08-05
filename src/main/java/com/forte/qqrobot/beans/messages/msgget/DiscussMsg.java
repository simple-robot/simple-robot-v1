package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.FlagAble;

/**
 * 讨论组消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface DiscussMsg extends MsgGet, CodesAble, FlagAble {

    /** 获取讨论组号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 获取发消息的人的QQ */
    String getQQ();

    @Override
    default String getQQCode(){
        return getQQ();
    }

    /** 标识默认使用getId获取 */
    @Override
    default String getFlag(){
        return getId();
    }



}
