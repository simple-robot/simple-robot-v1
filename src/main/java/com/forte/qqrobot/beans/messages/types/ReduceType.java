package com.forte.qqrobot.beans.messages.types;

/**
 * 群成员减少事件类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 11:44
 * @since JDK1.8
 **/
public enum ReduceType {

    /** 自行离开 */
    LEAVE(1),
    /** 被踢出 */
    KICK_OUT(-1);


    public final int TYPE;

    ReduceType(int type){
        this.TYPE = type;
    }

    public boolean isLevel(){
        return TYPE == LEAVE.TYPE;
    }
    public boolean isKickOut(){
        return TYPE == KICK_OUT.TYPE;
    }

    /**
     * 工厂获取
     */
    public static ReduceType of(int type) {
        for (ReduceType value : values()) {
            if (value.TYPE == type) {
                return value;
            }
        }
        return null;
    }

}
