package com.forte.qqrobot;

import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * <pre> SimpleRobotContext,  在启动器执行run方法后所得到的结果
 * <pre> 组件开发者可以选择使用一个新的封装类来继承此类，也可以选择直接使用此类。
 * <pre> 其中也包括了：
 * <ul>
 *     <li>默认的三大送信器</li>
 *     <li>{@link com.forte.qqrobot.bot.BotManager} 实例</li>
 *     <li>{@link com.forte.qqrobot.depend.DependCenter} 实例</li>
 *     <li>默认的三大送信器</li>
 *     <li>默认的三大送信器</li>
 * </ul>
 *
 * 1.13.1开始实现{@link Closeable}接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SimpleRobotContext<
        SEND extends SenderSendList,
        SET extends SenderSetList,
        GET extends SenderGetList,
        CONFIG extends BaseConfiguration,
        APPLICATION extends BaseApplication
        > implements Closeable {

    //**************** 三大送信器 ****************//

    public final SEND SENDER;
    public final SET SETTER;
    public final GET GETTER;

    private BotManager botManager;

    /** 监听消息字符串转化函数 */
    private MsgParser msgParser;

    /** 监听消息执行器 */
    private MsgProcessor processor;

    /** 依赖中心 */
    private DependCenter dependCenter;

    private CONFIG configuration;

    private APPLICATION application;

    /**
     * 构造函数
     * @param sender sender送信器
     * @param setter setter送信器
     * @param getter getter送信器
     * @param manager 监听函数管理器
     * @param msgParser 消息字符串转化器
     * @param processor 监听消息执行器
     */
    public SimpleRobotContext(SEND sender,
                              SET setter,
                              GET getter,
                              BotManager manager,
                              MsgParser msgParser,
                              MsgProcessor processor,
                              DependCenter dependCenter,
                              CONFIG configuration,
                              APPLICATION application
                              ){
        this.SENDER = sender;
        this.SETTER = setter;
        this.GETTER = getter;
        this.botManager = manager;
        this.msgParser = msgParser;
        this.processor = processor;
        this.dependCenter = dependCenter;
        this.configuration = configuration;
        this.application = application;
    }

    /**
     * 获取Bot管理器
     * @return bot管理器
     */
    public BotManager getBotManager(){
        return botManager;
    }

    /**
     * <pre> 获取监听消息字符串转化器
     * <pre> 此函数可以通过接收一个监听函数的字符串，来转化为一个{@link com.forte.qqrobot.beans.messages.msgget.MsgGet}监听消息对象
     * @return 监听消息字符串转化器
     */
    public MsgParser getMsgParser(){
        return msgParser;
    }

    /**
     * <pre> 获取监听消息执行处理器
     * <pre> 其可以通过接收一个{@link com.forte.qqrobot.beans.messages.msgget.MsgGet} 监听消息对象，来对其进行处理，并获取监听回执对象（或数组）
     * @return 监听消息执行处理器
     */
    public MsgProcessor getMsgProcessor(){
        return processor;
    }

    /**
     * 获取依赖管理中心
     * @return 依赖管理中心
     */
    public DependCenter getDependCenter(){
        return dependCenter;
    }

    public CONFIG getConfiguration() {
        return configuration;
    }

    public APPLICATION getApplication() {
        return application;
    }

    /**
     * closeable
     */
    @Override
    public void close() throws IOException {
        application.close();
    }
}
