/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Share.java
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
 * 分享(文件)
 */
public interface Share extends ResultInner {
    /**
     * BUSID
     */
    String getBusid();

    /**
     * 创建时间
     */
    Long getCreateTime();

    /**
     * 上传完成时间
     */
    Long getModiflyTime();

    /**
     * 下载次数
     */
    Integer getDLTimes();

    /**
     * 文件名
     */
    String getName();

    /**
     * 文件路径
     */
    String getFilePath();

    /**
     * 文件大小
     */
    Long getSize();

    /**
     * 本地文件名
     */
    String getLocalName();

    /**
     * 上传者群昵称
     */
    String getNick();

    /**
     * 上传者QQ号
     */
    String getQQ();
}
