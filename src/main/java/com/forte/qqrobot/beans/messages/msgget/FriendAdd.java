/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     FriendAdd.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

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
