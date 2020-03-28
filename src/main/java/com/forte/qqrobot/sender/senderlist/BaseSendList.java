package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.exception.RobotApiException;

/**
 * @see BaseRootSenderList
 */
@Deprecated
public abstract class BaseSendList implements SenderSendList {
    /**
     * 发送讨论组消息
     *
     * @param group 群号
     * @param msg   消息内容
     */
    @Override
    public String sendDiscussMsg(String group, String msg) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发送群消息
     *
     * @param group 群号
     * @param msg   消息内容
     */
    @Override
    public String sendGroupMsg(String group, String msg) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发送私聊信息
     *
     * @param QQ  QQ号
     * @param msg 消息内容
     */
    @Override
    public String sendPrivateMsg(String QQ, String msg) {
        throw RobotApiException.byFrom();
    }

    /**
     * 送花
     *
     * @param group 群号
     * @param QQ    QQ号
     */
    @Override
    public boolean sendFlower(String group, String QQ) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发送名片赞
     *
     * @param QQ    QQ号
     * @param times 次数
     */
    @Override
    public boolean sendLike(String QQ, int times) {
        throw RobotApiException.byFrom();
    }
}
