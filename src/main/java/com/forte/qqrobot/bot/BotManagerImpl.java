package com.forte.qqrobot.bot;

import com.forte.qqrobot.beans.function.PathAssembler;
import com.forte.qqrobot.beans.function.VerifyFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link BotManager}的基础实现类，使用Map储存数据。
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotManagerImpl implements BotManager {

    /**
     * 默认bot，默认情况下为第一个被注册的bot
     */
    private String defaultBot;

    /** 数据储存用的Map */
    private Map<String, BotInfo> botMap;

    /**
     * 注册验证函数
     */
    private VerifyFunction verifyFunction;

    /**
     * 路径拼接器
     */
    private PathAssembler pathAssembler;


    public BotManagerImpl(PathAssembler pathAssembler, VerifyFunction verifyFunction){
        this.pathAssembler = pathAssembler;
        this.verifyFunction = verifyFunction;
        this.botMap = new ConcurrentHashMap<>(2);
    }


    /**
     * 大多数情况下，可能不一定必须指定一个bot，则此方法规定获取一个默认的bot
     *
     * @return 获取一个默认bot
     */
    @Override
    public BotInfo defaultBot() {
        if(defaultBot == null){
            if(botMap.size() == 0){
                return null;
            }else{
                BotInfo next = botMap.values().iterator().next();
                defaultBot = next.getBotCode();
                return next;
            }
        }else{
            return getBot(defaultBot);
        }
    }

    /**
     * 设置默认bot的账号信息
     *
     * @param botCode 默认bot的账号信息
     */
    @Override
    public void setDefaultBot(String botCode) {
        defaultBot = botCode;
    }

    /**
     * 通过bot的code获取一个Bot的信息
     *
     * @param botCode 账号
     * @return bot信息
     */
    @Override
    public BotInfo getBot(String botCode) {
        return botMap.get(botCode);
    }

    /**
     * 获取全部的bots信息
     * @return bots
     */
    @Override
    public BotInfo[] bots(){
        return botMap.values().toArray(new BotInfo[0]);
    }

    /**
     * 注册一个botInfo。在实现的时候需要注意线程安全问题，概率较小，但是不是没有可能
     *
     * @param info bot信息，作为key的code信息将会从其中获取。info中的各项参数不可为null
     * @return 是否注册成功
     */
    @Override
    public boolean registerBot(BotInfo info) {
        // 先验证函数
        String key = (info = verifyBot(info)).getBotCode();
        // 在注册时候锁住map对象
        synchronized (botMap) {
            BotInfo botInfo = botMap.get(key);
            if(botInfo == null){
                botMap.put(key, info);
                if(defaultBot == null){
                    defaultBot = key;
                }
                return true;
            }else{
                // 已经存在, 不放入
                return false;
            }
        }
    }

    /**
     * 验证一个Bot，此bot至少应当存在path
     * @param info bot info
     * @return 验证结果，有可能会是null，或者抛出异常。
     */
    private BotInfo verifyBot(BotInfo info){
        // 验证bot
        return verifyFunction.apply(info);
    }

    /**
     * 获取注册用的验证函数
     *
     * @return 验证函数
     */
    @Override
    public VerifyFunction getVerifyFunction() {
        return verifyFunction;
    }

    /**
     * 获取路径拼接函数
     * @return 拼接函数
     */
    @Override
    public PathAssembler getPathAssembler(){
        return pathAssembler;
    }
}
