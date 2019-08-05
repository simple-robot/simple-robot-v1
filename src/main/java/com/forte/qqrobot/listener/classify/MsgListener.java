package com.forte.qqrobot.listener.classify;

import com.forte.qqrobot.listener.MsgDisGroupListener;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.listener.MsgPrivateListener;

/**
 * 整合与消息有关的监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:31
 * @since JDK1.8
 **/
@Deprecated
public interface MsgListener extends MsgDisGroupListener,
                                     MsgGroupListener,
                                     MsgPrivateListener {
}
