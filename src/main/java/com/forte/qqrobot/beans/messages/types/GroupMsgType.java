package com.forte.qqrobot.beans.messages.types;

/**
 * 群消息类型
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public enum GroupMsgType {


    /**
     * 普通消息
     */
    NORMAL_MSG(0),
    /**
     * 匿名消息
     */
    ANON_MSG(1),
    /**
     * 系统消息
     */
    SYS_MSG(2);

    /**
     * 类型编号
     */
    public final int TYPE;

    /**
     * 构造
     */
    GroupMsgType(int type) {
        this.TYPE = type;
    }

    public boolean isNormal(){
        return TYPE == NORMAL_MSG.TYPE;
    }

    public boolean isAnon(){
        return TYPE == ANON_MSG.TYPE;
    }

    public boolean isSys(){
        return TYPE == SYS_MSG.TYPE;
    }


    /**
     * 工厂获取
     */
    public static GroupMsgType of(int type) {
        for (GroupMsgType value : values()) {
            if (value.TYPE == type) {
                return value;
            }
        }
        return null;
    }

}
