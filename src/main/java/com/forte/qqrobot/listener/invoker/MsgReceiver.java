package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.result.ListenResult;

import java.util.function.Function;

/**
 *
 * 消息接收者接口，用于向外界用户提供消息接收接口
 * 返回值一般都是监听回执{@link com.forte.qqrobot.listener.result.ListenResult[]}数组
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MsgReceiver {

    /**
     * 接收到一条消息
     * @param msgGet MsgGet
     * @return 消息回执
     */
    ListenResult[] onMsg(MsgGet msgGet);

    /**
     * 接收到一条消息的字符串, 并提供转化函数
     * @param msgGetValue 消息字符串, 例如json格式的字符串
     * @param format      转化函数
     * @return 消息回执
     */
    ListenResult[] onMsg(String msgGetValue, Function<String, MsgGet> format);

}
