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
