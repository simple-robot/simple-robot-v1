package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.NickOrRemark;
import com.forte.qqrobot.beans.messages.QQCodeAble;

/**
 * 好友已经添加事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface FriendAdd extends EventGet, QQCodeAble {

    /** 添加人的QQ */
    String getQQ();

    @Override
    default String getQQCode(){
        return getQQ();
    }
}
