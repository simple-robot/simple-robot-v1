package com.forte.qqrobot.beans.messages;

/**
 * 此接口规定方法以获取那些存在<code>QQ号</code>的信息的QQ号
 * 用于接收的消息或事件中
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface QQCodeAble {

    String getQQCode();

}
