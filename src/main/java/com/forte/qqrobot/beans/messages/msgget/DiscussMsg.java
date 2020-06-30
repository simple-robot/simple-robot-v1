/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     DiscussMsg.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.FlagAble;
import com.forte.qqrobot.beans.messages.NickOrRemark;

/**
 * 讨论组消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface DiscussMsg extends MsgGet, CodesAble, FlagAble, NickOrRemark {

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
