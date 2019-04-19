package com.forte.qqrobot.beans.messages.types;

/**
 * 群消息类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public enum GroupMsgType {


    /** 普通消息 */
    NORMAL_MSG(0),
    /** 匿名消息 */
    ANON_MSG(1),
    /** 系统消息 */
    SYS_MSG(2)
    ;

    /** 类型编号 */
    public final int TYPE;

    /** 构造 */
    GroupMsgType(int type){
        this.TYPE = type;
    }
}
