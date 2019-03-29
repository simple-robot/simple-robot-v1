package com.forte.qqrobot.beans.msgget;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 14:31
 * @since JDK1.8
 **/
public interface EventGet extends MsgGet {

    /**
     * 事件类型的消息没有消息类型，获取的消息内容必然为null
     * @return
     */
    @Override
    default String getMsg(){
        return null;
    }

    default void setMsg(String msg){}

}
