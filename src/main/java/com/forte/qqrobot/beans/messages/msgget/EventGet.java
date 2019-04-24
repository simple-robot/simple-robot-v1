package com.forte.qqrobot.beans.messages.msgget;

/**
 * 事件接收的接口
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface EventGet extends MsgGet {

    /**
     * 事件中获取消息默认内容为空且无法设置
     */
    @Override
    default String getMsg(){
        return "";
    }
    default void setMsg(String msg){}

    /**
     * 事件中字体的获取默认为空且无法设置
     */
    @Override
    default String getFont(){
        return "";
    }
    default void setFont(String font){}


}
