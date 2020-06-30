/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractFriendList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @see FriendList
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractFriendList extends AbstractInfoResult implements FriendList {

    @Override
    public String toString(){
        return "FriendList{" +
                "friendList=" + getFriendList().entrySet().stream().map(e -> "{"+e.getKey()+"="+ Arrays.toString(e.getValue()) +"}").collect(Collectors.joining(", ")) +
                "} ";
    }
}
