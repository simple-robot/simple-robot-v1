package com.forte.qqrobot.bot;

import com.forte.qqrobot.beans.messages.result.LoginQQInfo;

/**
 * 一个已注册的机器人的信息标注接口
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface BotInfo {

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

}
