/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MsgProcessable.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.result.ListenResult;

/**
 * <pre> 消息处理器，将一个MsgGet消息交由监听函数处理器进行处理。
 * <pre> 一般配合抽象类{@link }
 * <pre> 此过程一般来讲是单线程的，所以如果你选择了自己使用这个功能，你可以拥有更多选择。
 * <pre> 例如，线程池、消息队列..
 * <pre> 最终结果一般来讲是一个最终的执行数组，不过提供了一个默认的方法以得到一个经过默认条件筛选的最终结果。
 *      这个筛选的方式是{@link com.forte.qqrobot.beans.types.ResultSelectType}, 在配置中是可配置的。
 * <pre> 此接口一般是面向组件开发者的，他需要组件开发者在内部实现对于MsgSender的构建逻辑。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MsgProcessable {

    /**
     * 监听一个消息，返回监听响应结果集
     * @param msgGet 监听消息
     * @return 监听响应集
     */
    ListenResult[] onMsg(MsgGet msgGet);



}
