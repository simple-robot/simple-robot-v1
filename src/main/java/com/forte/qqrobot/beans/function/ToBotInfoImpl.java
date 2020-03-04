package com.forte.qqrobot.beans.function;

import com.forte.qqrobot.bot.BotInfo;

import java.util.function.BiFunction;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ToBotInfoImpl implements ToBotInfo {

    private ExFunction<String, Integer, String, String> toFullPath;
    private BiFunction<String, String, BotInfo> toBot;

    /**
     * 构造
     * @param toFullPath 将ip、端口、请求路径拼接为完整的连接的函数
     * @param toBot 将code、完整连接转化为待验证的botInfo对象的函数
     */
    ToBotInfoImpl(ExFunction<String, Integer, String, String> toFullPath, BiFunction<String, String, BotInfo> toBot){
        this.toFullPath = toFullPath;
        this.toBot = toBot;
    }

    @Override
    public BotInfo toBotInfo(String code, String ip, int port, String path) {
        return toBotInfo(code, toFullPath.apply(ip, port, path));
    }

    @Override
    public BotInfo toBotInfo(String code, String urlPath) {
        return toBot.apply(code, urlPath);
    }
}
