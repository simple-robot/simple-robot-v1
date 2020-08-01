package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.NickOrRemark;
import com.forte.qqrobot.beans.messages.QQCodeAble;

/**
 *
 * 好友删除事件
 * 基本上与{@link FriendAdd}类似
 *
 * @author ForteScarlet <ForteScarlet@163.com>
 * @date 2020/8/1
 */
public interface FriendDelete extends EventGet, QQCodeAble, NickOrRemark {
}
