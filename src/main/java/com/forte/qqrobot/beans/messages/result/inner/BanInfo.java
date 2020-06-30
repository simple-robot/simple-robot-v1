/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BanInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 禁言详细信息
 */
public interface BanInfo extends ResultInner, QQCodeAble {
    /**
     * 被禁言者的QQ
     */
    String getQQ();

    /**
     * 被禁言成员昵称
     */
    String getNickName();

    /**
     * 是否为管理员
     */
    Boolean isManager();

    /**
     * 禁言剩余时间
     */
    Long lastTime();

    @Override
    default String getQQCode() {
        return getQQ();
    }
}