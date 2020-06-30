/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupBanType.java
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
 *
 * 群禁言事件的禁言类型，禁言/解除禁言
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum GroupBanType {

    /** 禁言 */
    BAN(0),
    /** 取消禁言 */
    LIFT_BAN(1),
    ;

    /** 给一个code */
    public final int CODE;

    GroupBanType(int code){
        this.CODE = code;
    }

    public int getCode(){
        return CODE;
    }

    public boolean isBan(){
        return CODE == BAN.CODE;
    }

    public boolean isLiftBan(){
        return CODE == LIFT_BAN.CODE;
    }


}
