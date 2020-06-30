/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Friend.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.NickOrRemark;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 好友信息
 */
public interface Friend extends ResultInner, QQCodeAble, NickOrRemark {
    /**
     * 获取好友昵称
     */
    String getName();

    /**
     * 获取好友QQ号
     */
    String getQQ();


    @Override
    default String getQQCode() {
        return getQQ();
    }

    /**
     * 头像地址
     */
    default String getHeadUrl() {
        if (getCode() == null) {
            return null;
        } else {
            return "http://q.qlogo.cn/headimg_dl?dst_uin=" + getCode() + "&spec=640";
        }
    }

}
