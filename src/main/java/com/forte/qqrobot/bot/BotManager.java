package com.forte.qqrobot.bot;

/**
 * Bot信息管理器
 * 其实现类应该存在一个公共的无参构造
 * 可以使用依赖注入的方式自动配置一个BotManager，但是只能存在一个。
 * 且应当使用单例方式注入。
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface BotManager {

    /**
     * 大多数情况下，可能不一定必须指定一个bot，则此方法规定获取一个默认的bot
     * @return 获取一个默认bot
     */
    BotInfo defaultBot();

    /**
     * 设置默认bot的账号信息
     * @param botCode 默认bot的账号信息
     */
    void setDefaultBot(String botCode);

    /**
     * 通过bot的code获取一个Bot的信息
     * 参数有可能为null
     * @param botCode 账号
     * @return bot信息
     */
    BotInfo getBot(String botCode);


    /**
     * 获取全部的bot信息
     * @return bots
     */
    BotInfo[] bots();

    /**
     * 注册一个botInfo。在实现的时候需要注意线程安全问题，概率较小，但是不是没有可能
     * @param info bot信息，作为key的code信息将会从其中获取。info中的各项参数不可为null
     * @return 是否注册成功
     */
    boolean registerBot(BotInfo info);


}
