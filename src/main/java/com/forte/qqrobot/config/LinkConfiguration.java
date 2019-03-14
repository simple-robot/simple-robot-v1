package com.forte.qqrobot.config;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.socket.QQWebSocketClient;

import java.util.*;

/**
 * 连接前的配置类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 15:24
 * @since JDK1.8
 **/
public final class LinkConfiguration {

    /** 连接地址，默认为localhost */
    private String linkIp = "localhost";

    /** 连接端口号，默认为25303 */
    private Integer port = 25303;

    /** 连接用的客户端class对象，默认为{@link QQWebSocketClient} */
    private Class<? extends QQWebSocketClient> socketClient = QQWebSocketClient.class;

    /** 全部监听器 */
    private Set<SocketListener> listenerSet = new HashSet<>();

    /** 全部初始化监听器 */
    private Set<InitListener> initListeners = new HashSet<>();

    /** 本机QQ号 */
    private String localQQCode = "";


    /*  ————————————————  参数配置 ———————————————— */

    /**
     * 注册监听器
     * @param listeners 监听器
     */
    public void registerListeners(SocketListener... listeners){
        listenerSet.addAll(Arrays.asList(listeners));
    }

    /**
     * 注册初始化监听器
     * @param listeners 初始化监听器
     */
    public void registerInitListeners(InitListener... listeners){
        initListeners.addAll(Arrays.asList(listeners));
    }

    /**
     * 获取初始化监听器
     */
    public Set<InitListener> getInitListeners(){
        return initListeners;
    }

    /**
     * 获取所有监听器，如果没有注册监听器则使用默认的监听器
     * @return 获取所有注册的监听器
     */
    public Set<SocketListener> getListeners(){
        return Optional.of(listenerSet).filter(s -> s.size() > 0).orElseGet(() -> {
                    Set<SocketListener> set = new HashSet<>();
                    set.add(ResourceDispatchCenter.getDefaultWholeListener());
                    return set;
                });
    }

    /**
     * 包扫描普通监听器
     * @param packageName   包名
     */
    public void scannerListener(String packageName){
        List<Class> list = new FileScanner(packageName, SocketListener.class).find().getEleStrategyList();

        registerListeners(list.stream().map(lc -> {
            try {
                return (SocketListener) lc.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                System.err.println("监听器[" + lc + "]实例化异常：没有无参构造");
                return null;
            }
        }).filter(Objects::nonNull).toArray(SocketListener[]::new));
    }


    /**
     * 包扫描初始化监听器
     * @param packageName   包名
     * @return
     */
    public void scannerInitListener(String packageName){
        List<Class> list = new FileScanner(packageName, InitListener.class).find().getEleStrategyList();

        registerInitListeners(list.stream().map(lc -> {
            try {
                return (InitListener) lc.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                System.err.println("监听器[" + lc + "]实例化异常：没有无参构造");
                return null;
            }
        }).filter(Objects::nonNull).toArray(InitListener[]::new));

    }




    /** 获取连接的完整地址 */
    public String getLinkUrl(){
        return "ws://"+ linkIp +":" + port;
    }


    /* —————————————— getter & setter —————————————— */

    public String getLocalQQCode() {
        return localQQCode;
    }

    public void setLocalQQCode(String localQQCode) {
        this.localQQCode = localQQCode;
    }

    public String getLinkIp() {
        return linkIp;
    }

    public void setLinkIp(String linkIp) {
        this.linkIp = linkIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Class<? extends QQWebSocketClient> getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Class<? extends QQWebSocketClient> socketClient) {
        this.socketClient = socketClient;
    }
}
