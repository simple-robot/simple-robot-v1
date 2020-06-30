/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupNote.java
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
 * 群公告
 */
public interface GroupNote extends ResultInner {
    /**
     * ID
     */
    String getId();

    /**
     * 完整正文
     */
    String getMsg();

    /**
     * 预览文
     */
    String getFaceMsg();

    /**
     * 标题
     */
    String getTitle();

    /**
     * 发布时间
     */
    Long getTime();

    /**
     * 已读人数数量
     */
    Integer getReadNum();

    /**
     * 是否提醒群员修改群名片
     */
    Boolean isShowEditCard();

    /**
     * 发布者QQ
     */
    String getQQ();

    /**
     * 公告类型ID
     */
    String getTypeId();

}
