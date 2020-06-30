/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     FriendList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.result.inner.Friend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 好友列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface FriendList extends InfoResult {

    /*
        好友列表有分组比较特殊，暂不使用InfoResultList接口
     */

    /** 各个分组下的好友列表 */
    Map<String, Friend[]> getFriendList();

    /** 通过某个分组获取其中的好友列表 */
    Friend[] getFirendList(String group);

    /**
     * 获取所有好友列表
     * @return
     */
    default Friend[] getAllFriends(){
        Map<String, Friend[]> friendList = getFriendList();
        // size * 2
        List<Friend> friends = new ArrayList<>(friendList.size() << 1);
        friendList.forEach((k, v) -> {
            if(v != null){
                friends.addAll(Arrays.asList(v));
            }
        });
        return friends.toArray(new Friend[0]);
    }

}
