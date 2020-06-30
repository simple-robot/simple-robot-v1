/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     PrivateMsgType.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.types;

/**
 * 私信消息类型枚举
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public enum PrivateMsgType {

    /** 来自好友 */
    FROM_FRIEND(0),
    /** 来自在线的人 */
    FROM_ONLINE(1),
    /** 来自群 */
    FROM_GROUP(2),
    /** 来自讨论组 */
    FROM_DISCUSS(3),
    /** 系统消息 */
    FROM_SYSTEM(4)
    ;

    /** 类型对应数字 */
    public final int TYPE;

    /** 构造 */
    PrivateMsgType(int tnum){
        this.TYPE = tnum;
    }

    /* ———————— API ———————— */

    /** 是否来自好友 */
    public boolean isFromFriend(){
        return TYPE == FROM_FRIEND.TYPE;
    }
    /** 是否来自在线消息 */
    public boolean isFromOnline(){
        return TYPE == FROM_ONLINE.TYPE;
    }
    /** 是否来自群消息 */
    public boolean isFromGroup(){
        return TYPE == FROM_GROUP.TYPE;
    }
    /** 是否来自讨论组消息 */
    public boolean isFromDiscuss(){
        return TYPE == FROM_DISCUSS.TYPE;
    }
    /** 是否为系统消息 */
    public boolean isFromSystem(){
        return TYPE == FROM_SYSTEM.TYPE;
    }
    /**
     * 工厂获取
     */
    public static PrivateMsgType of(int type) {
        for (PrivateMsgType value : values()) {
            if (value.TYPE == type) {
                return value;
            }
        }
        return null;
    }




}
