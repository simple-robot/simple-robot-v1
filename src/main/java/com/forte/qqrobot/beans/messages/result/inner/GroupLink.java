/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupLink.java
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
 * 群链接
 */
public interface GroupLink extends ResultInner {
    /**
     * 链接地址
     */
    String getUrl();

    /**
     * 链接的封面地址
     */
    String getPicUrl();

    /**
     * 发布时间
     */
    Long getTime();

    /**
     * 标题
     */
    String getTitle();

    /**
     * 该连接的发布者QQ
     */
    String getQQ();
}
