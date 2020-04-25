package com.forte.qqrobot.bot;

import java.io.Closeable;

/**
 * 一个已注册的机器人的信息标注接口
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface BotInfo extends Closeable {

    /**
     * 获取Bot的账号信息
     * @return Bot账号信息
     */
    String getBotCode();

    /**
     * 获取此bot的上报信息
     * @return bot上报信息
     */
    String getPath();

    /**
     * 获取此账号的登录信息
     * @return 获取登录信息
     */
    LoginInfo getInfo();

    /**
     * 获取当前bot所对应的送信器
     * @return 当前账号送信器
     */
    BotSender getSender();

}
