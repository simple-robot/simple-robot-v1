package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.sender.intercept.InterceptValue;

/**
 * SENDER 送信方法
 * 1.2.1增加扩充方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderSendList extends SenderList {

    //**************************************
    //*            send方法列表
    //**************************************

    /**
     * 发送讨论组消息
     * @param group 群号
     * @param msg   消息内容
     */
    @InterceptValue(value = "false")
    boolean sendDiscussMsg(String group, String msg);

    /**
     * 发送讨论组消息
     * @param groupCode 携带群号的类
     * @param msg       消息内容
     */
    default boolean sendDiscussMsg(GroupCodeAble groupCode, String msg){
        return sendDiscussMsg(groupCode.getGroupCode(), msg);
    }

    /**
     * 发送群消息
     * @param group 群号
     * @param msg   消息内容
     */
    @InterceptValue(value = "false")
    boolean sendGroupMsg(String group, String msg);


    /**
     * 发送群消息
     * @param groupCode 携带群号的类
     * @param msg       消息内容
     */
    default boolean sendGroupMsg(GroupCodeAble groupCode, String msg){
        return sendGroupMsg(groupCode.getGroupCode(), msg);
    }



    /**
     * 发送私聊信息
     * @param QQ    QQ号
     * @param msg   消息内容
     */
    @InterceptValue(value = "false")
    boolean sendPrivateMsg(String QQ, String msg);


    /**
     * 发送私聊信息
     * @param qqCode    携带QQ号的类
     * @param msg       消息内容
     */
    default boolean sendPrivateMsg(QQCodeAble qqCode, String msg){
        return sendPrivateMsg(qqCode.getQQCode(), msg);
    }

    /**
     * 送花
     * @param group 群号
     * @param QQ    QQ号
     */
    @InterceptValue(value = "false")
    boolean sendFlower(String group, String QQ);

    /**
     * 送花
     * @param group 携带群号
     * @param qq    携带QQ号
     */
    default boolean sendFlower(GroupCodeAble group, QQCodeAble qq){
        return sendFlower(group.getGroupCode(), qq.getQQCode());
    }

    /**
     * 送花
     * @param codesAble 携带群号与QQ号
     */
    default boolean sendFlower(CodesAble codesAble){
        return sendFlower(codesAble.getGroupCode(), codesAble.getQQCode());
    }
    /**
     * 发送名片赞
     * @param QQ    QQ号
     * @param times 次数
     */
    @InterceptValue(value = "false")
    boolean sendLike(String QQ, int times);

    /**
     * 发送名片赞
     * @param qq    携带QQ号
     * @param times 次数
     */
    default boolean sendLike(QQCodeAble qq, int times){
        return sendLike(qq.getQQCode(), times);
    }

}
