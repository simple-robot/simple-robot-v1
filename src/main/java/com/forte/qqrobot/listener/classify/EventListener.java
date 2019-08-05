package com.forte.qqrobot.listener.classify;

import com.forte.qqrobot.listener.EventFriendAddedListener;
import com.forte.qqrobot.listener.EventGroupAdminListener;
import com.forte.qqrobot.listener.EventGroupMemberJoinListener;
import com.forte.qqrobot.listener.EventGroupMemberReduceListener;

/**
 * 整合与事件有关的监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:30
 * @since JDK1.8
 **/
@Deprecated
public interface EventListener extends EventFriendAddedListener,
                                        EventGroupAdminListener,
                                        EventGroupMemberJoinListener,
                                        EventGroupMemberReduceListener{
}
