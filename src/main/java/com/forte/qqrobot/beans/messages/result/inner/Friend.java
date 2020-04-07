package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 好友信息
 */
public interface Friend extends ResultInner, QQCodeAble {
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

}
