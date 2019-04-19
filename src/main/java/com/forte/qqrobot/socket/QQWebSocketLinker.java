package com.forte.qqrobot.socket;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.SocketResourceDispatchCenter;
import com.forte.qqrobot.configuration.LinkConfiguration;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.listener.invoker.ListenerPlug;
import com.forte.qqrobot.log.QQLog;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * QQWebSocket连接器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:07
 * @since JDK1.8
 **/
public class QQWebSocketLinker {

    private static final String LOCAL_IP_WITH_HEAD = "ws://localhost:";

    /**
     * 连接qqwebsocket
     *
     * @param url       连接地址
     * @param retryTime 重试次数，如果小于0则视为无限循环
     * @return
     */
    public static QQWebSocketClient link(Class<? extends QQWebSocketClient> client, String url, int retryTime) {
        QQWebSocketClient cc = null;
        //获取连接配置
        LinkConfiguration linkConfiguration = SocketResourceDispatchCenter.getLinkConfiguration();
        int times = 0;
        boolean localB = true;

        //构建监听函数管理器等扫描器所构建的
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        ListenerManager manager = scanner.buildManager();
        ListenerPlug plug = scanner.buildPlug();
        //保存
        ResourceDispatchCenter.saveListenerManager(manager);
        ResourceDispatchCenter.saveListenerPlug(plug);

        //连接的时候先尝试一次本地连接
        try {
            QQLog.info("尝试本地连接...");
            //准备参数
            Object[] localParams = new Object[]{
                    new URI(LOCAL_IP_WITH_HEAD + linkConfiguration.getPort()),
                    manager,
                    linkConfiguration.getInitListeners()
            };
            cc = client.getConstructor(URI.class, ListenerManager.class, Set.class).newInstance(localParams);
            localB = cc.connectBlocking();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InterruptedException | URISyntaxException e) {
            QQLog.debug("本地连接失败");
        }

        if (localB) {
            QQLog.info("本地连接成功");
        }

        //如果本地连接失败，正常连接
        while (!localB) {
            try {
                //参数需要：URI、QQWebSocketMsgSender sender、Set<SocketListener> listeners、set<InitListener> initListeners
                Object[] params = new Object[]{
                        new URI(url),
                        manager,
                        linkConfiguration.getInitListeners()
                };
                cc = client.getConstructor(URI.class, ListenerManager.class, Set.class).newInstance(params);
                QQLog.info("连接阻塞中...");
                boolean b = cc.connectBlocking();
                QQLog.info(b ? "连接成功" : "连接失败");
                if (b) {
                    //如果成功，跳出无限连接循环
                    break;
                } else {
                    Thread.sleep(1000);
                }
            } catch (URISyntaxException | InterruptedException e) {
                QQLog.debug("连接出现异常");
                e.printStackTrace();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ignore) {
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                //这里是利用反射创建实例的错误，当出现这个错误的时候不能再循环了，直接抛出异常
                throw new RuntimeException(e);
            } finally {
                times++;
                //如果重试次数超过设定次数，跳出循环
                if (retryTime > 0 && times >= retryTime) {
                    break;
                }
            }
        }

        //循环结束即认定为连接成功
        linkSuccess();
        return cc;
    }

    /**
     * 连接qqwebsocket,无限循环连接
     *
     * @param url 连接地址
     * @return
     */
    public static QQWebSocketClient link(Class<? extends QQWebSocketClient> client, String url) {
        return link(client, url, -1);
    }

    /**
     * 连接qqwebsocket,无限循环连接
     *
     * @param url 连接地址
     * @return
     */
    public static QQWebSocketClient link(String url) {
        return link(QQWebSocketClient.class, url, -1);
    }


    /**
     * *****************************************
     * **        连接成功回调方法
     * **
     * ******************************************
     *
     * @date 2019/3/26
     * @author ForteScarlet
     * ****************************************
     */
    private static void linkSuccess() {
        //获取连接配置
//        LinkConfiguration linkConfiguration = SocketResourceDispatchCenter.getLinkConfiguration();
//        加载普通监听器
//        linkConfiguration.getListeners().forEach(ResourceDispatchCenter.getListenerInvoker()::loadListener);
        //构建监听函数管理器
//        ListenerManager listenerManager = ResourceDispatchCenter.getListenerMethodScanner().buildManager();
//        ListenerPlug listenerPlug = ResourceDispatchCenter.getListenerMethodScanner().buildPlug();
        //保存监听函数
        //将ListenerInvoker放入资源调度中心
//        ResourceDispatchCenter.saveListenerManager(listenerManager);
        //将监听函数阻断器放入资源调度中心
//        ResourceDispatchCenter.saveListenerPlug(listenerPlug);



    }


}
