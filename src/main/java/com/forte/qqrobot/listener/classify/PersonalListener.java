package com.forte.qqrobot.listener.classify;

import com.forte.qqrobot.listener.EventFriendAddedListener;
import com.forte.qqrobot.listener.MsgPrivateListener;
import com.forte.qqrobot.listener.RequestFriendListener;

/**
 * 与个人有关的监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:36
 * @since JDK1.8
 **/
public interface PersonalListener extends  EventFriendAddedListener,
                                            MsgPrivateListener,
                                            RequestFriendListener {

}
