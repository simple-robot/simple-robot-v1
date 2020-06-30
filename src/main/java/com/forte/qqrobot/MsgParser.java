/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MsgParser.java
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

/**
 *
 * 消息转化器，接收一个消息字符串，转化为消息封装类{@link com.forte.qqrobot.beans.messages.msgget.MsgGet}
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MsgParser {

    /**
     * 将一个从插件那里得到的监听消息的原始字符串转化为一个对应的消息封装类。
     * @param msg 监听消息字符串
     * @return {@link MsgGet}
     */
    MsgGet parse(String msg);

}
