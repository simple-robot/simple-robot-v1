package com.forte.qqrobot;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.socket.QQWebSocketClient;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.*;
import java.util.function.Predicate;

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

    /** 是否扫描了普通监听器 */
    private boolean scannedListner = false;
    /** 是否扫描了初始化监听器 */
    private boolean scannedInitListener = false;

//    /** 全部监听器-实例类 */
//    private Set<SocketListener> listenerSet = new HashSet<>();
//
    /** 全部初始化监听器 */
    private Set<InitListener> initListeners = new HashSet<>();

    /** 本机QQ号 */
    private String localQQCode = "";

    /** 本机QQ的昵称 */
    private String localQQNick = "";

    /*  ————————————————  参数配置 ———————————————— */

//    /**
//     * 注册监听器
//     * @param listeners 监听器
//     */
//    public void registerListeners(SocketListener... listeners){
//        listenerSet.addAll(Arrays.asList(listeners));
//    }

    /**
     * 注册监听器
     * @param listeners 监听器列表
     */
    public void registerListeners(Object... listeners){
        //获取扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        //遍历
        for (Object listener : listeners) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(listener);
                QQLog.info("加载["+ listener.getClass() +"]的监听函数成功：");
                StringJoiner joiner = new StringJoiner("]\r\n\t>>>", "\t>>>", "");
                scanSet.stream().peek(lm -> joiner.add(lm.getMethodToString()));
                QQLog.info(joiner.toString());
            } catch (Exception e) {
                QQLog.error("加载[\"+ listener.getClass() +\"]的监听函数出现异常！", e);
            }
        }
    }

    /**
     * 注册监听器
     * @param listeners 监听器列表
     */
    public void registerListeners(Class<?>... listeners){
        //获取扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        //遍历
        for (Class listener : listeners) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(listener);

                QQLog.info("加载["+ listener +"]的监听函数成功：");
                scanSet.forEach(lm -> QQLog.info(">>>" + lm.getMethodToString()));
            } catch (Exception e) {
                QQLog.error("加载["+ listener +"]的监听函数出现异常！", e);
            }
        }
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
     * 包扫描普通监听器
     * @param packageName   包名
     */
    public void scannerListener(String packageName){
        Set<Class<?>> list = new FileScanner().find(packageName).get();
        registerListeners(list.toArray(new Class<?>[0]));
    }

    /**
     * 如果没有扫描过普通包，则扫描
     */
    void scannerIfNotScanned(String packageName){
        if(this.scannedListner){
            return;
        }
        Set<Class<?>> list = new FileScanner().find(packageName).get();
        registerListeners(list.toArray(new Class<?>[0]));
    }

    /**
     * 如果没有扫描过普通包，则扫描
     */
    void scannerIfNotScanned(String packageName, Predicate<Class<?>> classFilter){
        if(this.scannedListner){
            return;
        }
        Set<Class<?>> list = new FileScanner().find(packageName, classFilter).get();
        registerListeners(list.toArray(new Class<?>[0]));
    }

    /**
     * 包扫描初始化监听器
     * @param packageName   包名
     * @return
     */
    public void scannerInitListener(String packageName){
        Set<Class<?>> list = new FileScanner().find(packageName, c -> FieldUtils.isChild(c, InitListener.class)).get();

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

    public String getLocalQQNick() {
        return localQQNick;
    }

    public void setLocalQQNick(String localQQNick) {
        this.localQQNick = localQQNick;
    }

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
