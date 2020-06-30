/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupBan.java
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
import com.forte.qqrobot.beans.messages.types.GroupBanType;

/**
 *
 * 群禁言事件
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupBan extends EventGet, CodesAble {

    /** 获取禁言类型：禁言/解除禁言 */
    GroupBanType getBanType();

    /** 群号 */
    String getGroup();
    @Override
    default String getGroupCode(){
        return getGroup();
    }
    /** 操作者的QQ号 */
    String getOperatorQQ();

    /** 被操作者的QQ号 */
    String getBeOperatedQQ();

    /** 使用的是被操作者的QQ */
    @Override
    default String getQQCode(){
        return getBeOperatedQQ();
    }

    /** 禁言时长-秒 */
    Long time();

}
