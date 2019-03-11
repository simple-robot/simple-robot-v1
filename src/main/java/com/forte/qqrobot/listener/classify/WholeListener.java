package com.forte.qqrobot.listener.classify;

import com.forte.qqrobot.listener.*;

/**
 * 包含了全部监听器的监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:38
 * @since JDK1.8
 **/
public interface WholeListener extends EventFriendAddedListener,
                                        EventGroupAdminListener,
                                        EventGroupMemberJoinListener,
                                        EventGroupMemberReduceListener,
                                        MsgDisGroupListener,
                                        MsgGroupListener,
                                        MsgPrivateListener,
                                        RequestFriendListener,
                                        RequestGroupListener{


}
