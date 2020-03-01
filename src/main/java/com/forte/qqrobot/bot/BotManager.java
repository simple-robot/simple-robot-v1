package com.forte.qqrobot.bot;

/**
 * Bot信息管理器
 * 其实现类应该存在一个公共的无参构造
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface BotManager {

    /**
     * 通过bot的code获取一个Bot的信息
     * @param botCode 账号
     * @return bot信息
     */
    BotInfo getBot(String botCode);

    /**
     * 注册一个botInfo。在实现的时候需要注意线程安全问题，概率较小，但是不是没有可能
     * @param info bot信息，作为key的code信息将会从其中获取。info中的各项参数不可为null
     * @return 是否注册成功
     */
    boolean registerBot(BotInfo info);

}
