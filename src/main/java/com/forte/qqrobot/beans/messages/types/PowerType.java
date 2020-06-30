/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     PowerType.java
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
 * 群权限类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum PowerType {

    OWNER(1, "群主"),
    ADMIN(0, "管理员"),
    MEMBER(-1, "群员");

    public final int TYPE;
    public final String TO_STRING;

    PowerType(int type, String toString){
        this.TYPE = type;
        this.TO_STRING = toString;
    }

    public boolean isOwner(){
        return TYPE == OWNER.TYPE;
    }
    public boolean isAdmin(){
        return TYPE == ADMIN.TYPE;
    }
    public boolean isMember(){
        return TYPE == MEMBER.TYPE;
    }

    @Override
    public String toString() {
        return TO_STRING;
    }

    /**
     * 工厂获取
     */
    public static PowerType of(int type) {
        for (PowerType value : values()) {
            if (value.TYPE == type) {
                return value;
            }
        }
        return null;
    }



}

