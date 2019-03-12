package com.forte.qqrobot;

import com.forte.qqrobot.config.LinkConfiguration;
import com.forte.qqrobot.listener.DefaultWholeListener;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerInvoker;
import com.forte.qqrobot.socket.QQWebSocketManager;
import com.forte.qqrobot.socket.QQWebSocketMsgCreator;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.SingleFactory;

/**
 *     资源调度中心，名字基本是纯机翻
 * <br>主要用于框架内部，用于获取一些功能性类的单例对象
 * <br>获取前需要保证在初始化方法{@link RobotApplication#init()}中已经储存过
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 14:42
 * @since JDK1.8
 **/
public class ResourceDispatchCenter {

    /**
     * 储存一个连接管理器
     * @param qqWebSocketManager    单例对象
     */
    static void saveQQWebSocketManager(QQWebSocketManager qqWebSocketManager){
        SingleFactory.set(qqWebSocketManager);
    }

    /**
     * 储存一个CQCodeUtil对象到单例工厂
     * @param cqCodeUtil    单例对象
     */
    static void saveCQCodeUtil(CQCodeUtil cqCodeUtil){
        //将CQCodeUtil放入单例工厂
        SingleFactory.set(cqCodeUtil);
    }

    /**
     * 储存一个QQWebSocketMsgCreator对象到单例工厂
     * @param qqWebSocketMsgCreator 单例对象
     */
    static void saveQQWebSocketMsgCreator(QQWebSocketMsgCreator qqWebSocketMsgCreator){
    //将CQCodeUtil放入单例工厂
        SingleFactory.set(qqWebSocketMsgCreator);
    }

    /**
     * 储存一个configuration对象到单例工厂
     * @param configuration 单例对象
     */
    static void saveLinkConfiguration(LinkConfiguration configuration){
        SingleFactory.set(configuration);
    }

    /**
     * 储存一个送信者
     * @param defaultWholeListener 单例对象
     */
    static void saveDefaultWholeListener(DefaultWholeListener defaultWholeListener){
        SingleFactory.set(defaultWholeListener);
    }

    /**
     * 储存一个监听器触发器
     * @param listenerInvoker 单例对象
     */
    static void saveListenerInvoker(ListenerInvoker listenerInvoker){
        SingleFactory.set(listenerInvoker);
    }

    /**
     * 储存一个监听器分类器
     * @param listenerFilter 单例对象
     */
    static void saveListenerFilter(ListenerFilter listenerFilter){
        SingleFactory.set(listenerFilter);
    }

        /* ———————————————— 获取方法 ———————————————— */

    /**
     * 获得一个QQWebSocketManager单例对象
     * @return
     */
    public static QQWebSocketManager getQQWebSocketManager(){
        return SingleFactory.get(QQWebSocketManager.class);
    }

    /**
     * 获得一个CQCodeUtil单例对象
     * @return  CQCodeUtil单例对象
     */
    public static CQCodeUtil getCQCodeUtil(){
        return SingleFactory.get(CQCodeUtil.class);
    }


    /**
     * 获得一个QQWebSocketMsgCreator单例对象
     * @return  QQWebSocketMsgCreator单例对象
     */
    public static QQWebSocketMsgCreator getQQWebSocketMsgCreator(){
        return SingleFactory.get(QQWebSocketMsgCreator.class);
    }

    /**
     * 获取一个LinkConfiguration单例对象
     * @return LinkConfiguration单例对象
     */
    public static LinkConfiguration getLinkConfiguration(){
        return SingleFactory.get(LinkConfiguration.class);
    }

    /**
     * 获取一个DefaultWholeListener单例对象
     * @return DefaultWholeListener单例对象
     */
    public static DefaultWholeListener getDefaultWholeListener(){
        return SingleFactory.get(DefaultWholeListener.class);
    }

    /**
     * 获取一个ListenerInvoker单例对象
     * @return ListenerInvoker单例对象
     */
    public static ListenerInvoker getListenerInvoker(){
        return SingleFactory.get(ListenerInvoker.class);
    }

    /**
     * 获取一个ListenerFilter单例对象
     * @return ListenerFilter单例对象
     */
    public static ListenerFilter getListenerFilter(){
        return SingleFactory.get(ListenerFilter.class);
    }

}
