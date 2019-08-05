package com.forte.qqrobot.listener.classify;

import com.forte.qqrobot.listener.RequestFriendListener;
import com.forte.qqrobot.listener.RequestGroupListener;

/**
 * 与请求有关的监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:32
 * @since JDK1.8
 **/
@Deprecated
public interface RequestListener extends RequestFriendListener,
                                         RequestGroupListener {
}
