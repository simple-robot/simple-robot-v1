package com.forte.qqrobot.sender;

/**
 * 为送信器提供送信接口是否存在的方法
 * 送信器中应当存在三种类型的{@link com.forte.qqrobot.sender.senderlist.SenderList}送信接口以发送消息
 * 此接口提供三种方法以为送信器提供送信接口是否存在的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Senderable {

    /**
     * 是否存在send消息器
     */
    boolean isSendAble();

    /**
     * 是否存在set消息器
     */
    boolean isSetAble();

    /**
     * 是否存在Get消息器
     */
    boolean isGetAble();

}
