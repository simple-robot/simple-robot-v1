package com.forte.qqrobot.bot;

import com.forte.qqrobot.beans.messages.result.LoginQQInfo;

/**
 *
 * 一个已注册的机器人的信息
 * 一个简单的信息封装类，一般为登录后验证完全部的登录信息后。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotInfoImpl implements BotInfo {

    private String botCode;
    private String path;
    private LoginInfo info;

    /**
     * 构建一个bot信息对象
     * @param botCode bot的Code信息
     * @param path    上报路径信息
     * @param info    bot的详细信息
     */
    public BotInfoImpl(String botCode, String path, LoginInfo info){
        this.botCode = botCode;
        this.path = path;
        this.info = info;
    }

    @Override
    public String getBotCode() {
        return botCode;
    }

    public void setBotCode(String botCode) {
        this.botCode = botCode;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public LoginInfo getInfo() {
        return info;
    }

    public void setInfo(LoginInfo info) {
        this.info = info;
    }
}
