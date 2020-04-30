package com.forte.qqrobot.bot;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.function.PathAssembler;
import com.forte.qqrobot.beans.function.VerifyFunction;
import com.forte.qqrobot.exception.BotVerifyException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link BotManager}的基础实现类，使用Map储存数据。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotManagerImpl implements BotManager {

    /**
     * 默认bot，默认情况下为第一个被注册的bot
     */
    private String defaultBot;

    /**
     * 数据储存用的Map
     */
    private final Map<String, BotInfo> botMap;

    /**
     * 注册验证函数
     */
    private VerifyFunction verifyFunction;

    /**
     * 路径拼接器
     */
    private PathAssembler pathAssembler;


    public BotManagerImpl(PathAssembler pathAssembler, VerifyFunction verifyFunction) {
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
        if (defaultBot == null) {
            if (botMap.size() == 0) {
                return null;
            } else {
                BotInfo next = botMap.values().iterator().next();
                defaultBot = next.getBotCode();
                return next;
            }
        } else {
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
     *
     * @return bots
     */
    @Override
    public BotInfo[] bots() {
        return botMap.values().toArray(new BotInfo[0]);
    }

    /**
     * 注册一个botInfo。在实现的时候需要注意线程安全问题，概率较小，但是不是没有可能
     *
     * @param info bot信息，作为key的code信息将会从其中获取。info中path必须存在。
     *             会对账号进行一次验证，如果通过则会添加。
     * @return 如果注册成功，返回注册后的botInfo，否则返回null
     */
    @Override
    public BotInfo registerBot(BotInfo info) {
        // 先验证函数
        String key = info.getBotCode();
        // 在注册时候锁住map对象
        synchronized (botMap) {
            BotInfo verifyBot = null;
            if(key == null){
                verifyBot = verifyBot(info);
                key = (info = verifyBot).getBotCode();
            }

            BotInfo botInfo = botMap.get(key);
            if (botInfo == null) {
                if(verifyBot == null){
                    verifyBot = verifyBot(info);
                }
                botMap.put(key, verifyBot);
                if (defaultBot == null) {
                    defaultBot = key;
                }
                return info;
            } else {
                // 已经存在, 不放入并关闭info
                try {
                    info.close();
                } catch (IOException e) {
                    throw new BotVerifyException(e);
                }
                return null;
            }
        }
    }

    /**
     * 验证一个Bot，此bot至少应当存在path
     *
     * @param info bot info
     * @return 验证结果，有可能会是null，或者抛出异常。
     */
    private BotInfo verifyBot(BotInfo info) {
        // 验证bot
        return verifyFunction.apply(info);
    }

    /**
     * 注销掉一个bot，将其从bot列表中移除。
     * 注意现成安全问题。
     *
     * @param code 要注销掉的bot账号
     */
    @Override
    public BotInfo logOutBot(String code) {
        // 移除掉一个bot的信息
        // 先锁住botMap
        synchronized (botMap) {
            BotInfo remove = botMap.remove(code);
            if(remove != null){
                try {
                    remove.close();
                } catch (IOException e) {
                    throw new BotVerifyException(e);
                }
            }
            return remove;
        }
    }

    /**
     * 刷新一个Bot的账号信息
     * @param code 要刷新的bot账号的信息
     */
    @Override
    public void refreshBot(String code) {
        // 刷新某个bot的信息
        final BotInfo botInfo = botMap.get(code);
        if (botInfo == null) {
            throw new BotVerifyException("notExists", code);
        }
        // 通过当前的botInfo获取新注册的botInfo
        final BotInfo newBotInfo = verifyBot(botInfo);
        if(newBotInfo != null){
            synchronized (botMap) {
                botMap.put(code, botInfo);
            }
        }else{
            throw new BotVerifyException("null");
        }
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
     *
     * @return 拼接函数
     */
    @Override
    public PathAssembler getPathAssembler() {
        return pathAssembler;
    }
}
