package com.forte.qqrobot.socket;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.config.LinkConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * QQWebSocket连接器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:07
 * @since JDK1.8
 **/
public class QQWebSocketLinker {



    /**
     * 连接qqwebsocket
     * @param url        连接地址
     * @param retryTime  重试次数，如果小于0则视为无限循环
     * @return
     */
    public static QQWebSocketClient link(Class<? extends QQWebSocketClient> client, String url, int retryTime){
        QQWebSocketClient cc = null;
        //获取连接配置
        LinkConfiguration linkConfiguration = ResourceDispatchCenter.getLinkConfiguration();
        int times = 0;
        while(true){
            try {
                //参数需要：URI、QQWebSocketMsgSender sender、Set<SocketListener> listeners
                Object[] params = new Object[]{new URI(url), linkConfiguration.getListeners()};
                cc = client.getConstructor(URI.class, Set.class).newInstance(params);
//                cc = new QQWebSocketClient( new URI( url ) );
                System.out.println("连接阻塞中...");
                boolean b = cc.connectBlocking();
                System.out.println(b?"连接成功":"连接失败");
                if(b){
                    //如果成功，跳出无限连接循环
                    break;
                }else{
                    Thread.sleep(1000);
                }
            } catch (URISyntaxException | InterruptedException e) {
                System.err.println("连接出现异常");
                e.printStackTrace();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ignore) { }
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                //这里是利用反射创建实例的错误，当出现这个错误的时候不能再循环了，直接抛出异常
                throw new RuntimeException(e);
            }finally {
                times++;
                //如果重试次数超过设定次数，跳出循环
                if(retryTime > 0 && times >= retryTime){
                    break;
                }
            }
        }
        return cc;
    }

    /**
     * 连接qqwebsocket,无限循环连接
     * @param url        连接地址
     * @return
     */
    public static QQWebSocketClient link(Class<? extends QQWebSocketClient> client, String url){
        return link(client, url, -1);
    }

    /**
     * 连接qqwebsocket,无限循环连接
     * @param url        连接地址
     * @return
     */
    public static QQWebSocketClient link(String url){
        return link(QQWebSocketClient.class, url, -1);
    }


}
