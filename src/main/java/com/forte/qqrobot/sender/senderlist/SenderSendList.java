package com.forte.qqrobot.sender.senderlist;

/**
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
    boolean sendDiscussMsg(String group, String msg);

    /**
     * 送花
     * @param group 群号
     * @param QQ    QQ号
     */
    boolean sendFlower(String group, String QQ);

    /**
     * 发送群消息
     * @param group 群号
     * @param msg   消息内容
     */
    boolean sendGroupMsg(String group, String msg);

    /**
     * 发送私聊信息
     * @param QQ    QQ号
     * @param msg   消息内容
     */
    boolean sendPrivateMsg(String QQ, String msg);

    /**
     * 发送名片赞
     * @param QQ    QQ号
     * @param times 次数
     */
    boolean sendLike(String QQ, int times);



}
