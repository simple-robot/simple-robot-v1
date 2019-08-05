package com.forte.qqrobot.listener.classify;

import com.forte.qqrobot.listener.*;

/**
 * 与群组、讨论组有关的监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:34
 * @since JDK1.8
 **/
@Deprecated
public interface GroupListener extends EventGroupAdminListener,
                                        EventGroupMemberJoinListener,
                                        EventGroupMemberReduceListener,
                                        MsgDisGroupListener,
                                        MsgGroupListener,
                                        RequestGroupListener {
}
