/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ThisCodeAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages;

/**
 * 提供接口以支持消息获取当前Bot的账号信息
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface ThisCodeAble {
    /**
     * 此消息获取的时候，代表的是哪个账号获取到的消息。
     * @return 接收到此消息的账号。
     */
    String getThisCode();

    /**
     * 允许重新定义Code以实现在存在多个机器人的时候切换处理。
     * @param code code
     */
    void setThisCode(String code);
}
