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