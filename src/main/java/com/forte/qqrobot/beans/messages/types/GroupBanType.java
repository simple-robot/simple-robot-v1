package com.forte.qqrobot.beans.messages.types;

/**
 *
 * 群禁言事件的禁言类型，禁言/解除禁言
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum GroupBanType {

    BAN(0),
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
