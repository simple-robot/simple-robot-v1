/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupAddRequestType.java
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
 * 加群申请类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum GroupAddRequestType {

    /** 邀请 */
    INVITE(1),
    /** 添加 */
    ADD(-1);

    public final int TYPE;

    GroupAddRequestType(int type){
        this.TYPE = type;
    }

    public boolean isInvite(){
        return TYPE == INVITE.TYPE;
    }

    public boolean isAdd(){
        return TYPE == ADD.TYPE;
    }

    /**
     * 工厂获取
     */
    public static GroupAddRequestType of(int type) {
        for (GroupAddRequestType value : values()) {
            if (value.TYPE == type) {
                return value;
            }
        }
        return null;
    }



}
