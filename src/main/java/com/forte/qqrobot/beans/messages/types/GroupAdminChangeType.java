/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupAdminChangeType.java
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
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 11:36
 * @since JDK1.8
 **/
public enum GroupAdminChangeType {

    /** 成为管理员 */
    BECOME_ADMIN(1),
    /** 被取消管理员 */
    CANCEL_ADMIN(-1);

    public final int TYPE;

    GroupAdminChangeType(int type){
        this.TYPE = type;
    }

    public boolean isBecome(){
        return TYPE == BECOME_ADMIN.TYPE;
    }

    public boolean isCancel(){
        return TYPE == CANCEL_ADMIN.TYPE;
    }

    /** 工厂获取 */
    public static GroupAdminChangeType of(int type){
        for (GroupAdminChangeType value : values()) {
            if(value.TYPE == type){
                return value;
            }
        }
        return null;
    }


}
