/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Content.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 群作业信息
 */
public interface Content extends ResultInner {
    /**
     * 获取内容
     */
    String getText();

    /**
     * 获取类型
     */
    String getType();
}