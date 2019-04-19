package com.forte.qqrobot;

import com.forte.forhttpapi.beans.response.Resp_getLoginQQInfo;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.*;
import java.util.function.Predicate;

/**
 * 配置类的根类，定义包扫描方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/4 18:02
 * @since JDK1.8
 **/
public abstract class BaseConfiguration {

    /** 是否扫描了普通监听器 */
    private boolean scannedListener = false;

    /** 是否扫描了初始化监听器 */
    private boolean scannedInitListener = false;

    /** 本机QQ信息, 先创建一个默认的类 */
    private Resp_getLoginQQInfo.LoginQQInfo loginQQInfo = new Resp_getLoginQQInfo.LoginQQInfo();

    /** 全部初始化监听器 */
    private Set<InitListener> initListeners = new HashSet<>();

    /** 本机QQ号 */
    private String localQQCode = "";

    /** 本机QQ的昵称 */
    private String localQQNick = "";

    /** 使用的编码格式，默认为UTF-8 */
    private String encode = "UTF-8";

    /** 酷Q根路径的配置，默认为null */
    private String cqPath;

    /* ———————— 动态交互的参数为静态参数，保证参数存在 */

    /** 动态交互 HTTP API插件的监听端口 */
    private static Integer HTTP_API_port = 8877;

    /** 向http api请求数据的访问地址，默认为'/' */
    private static String HTTP_API_path = "/";

    /** HTTP API 插件的ip地址 */
    private static String HTTP_API_ip = "localhost";

    /** 获取HTTP API请求地址 */
    public static String getHttpRequestUrl(){
        String ip = HTTP_API_ip;
        Integer port = HTTP_API_port;
        String path = HTTP_API_path.startsWith("/") ? HTTP_API_path.length() > 1 ? HTTP_API_path : "" : "/" + HTTP_API_path;
        return "http://" + ip + ":" + port + path;
    }

    /**
     * 注册监听器
     * @param listeners 监听器列表
     */
    public void registerListeners(Object... listeners){
        isScannedListener();
        //获取扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        //遍历
        for (Object listener : listeners) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(listener);
                if(scanSet.size() > 0){
                    QQLog.info("加载["+ listener.getClass() +"]的监听函数成功：");
                    StringJoiner joiner = new StringJoiner("]\r\n\t>>>", "\t>>>", "");
                    scanSet.stream().peek(lm -> joiner.add(lm.getMethodToString()));
                    QQLog.info(joiner.toString());
                }
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
        isScannedListener();
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
        isScannedInitListener();
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
        isScannedListener();
        Set<Class<?>> list = new FileScanner().find(packageName).get();
        registerListeners(list.toArray(new Class<?>[0]));
    }

    /**
     * 如果没有扫描过普通包，则扫描
     */
    public void scannerIfNotScanned(String packageName){
        if(this.scannedListener){
            return;
        }
        Set<Class<?>> list = new FileScanner().find(packageName).get();
        registerListeners(list.toArray(new Class<?>[0]));
    }

    /**
     * 如果没有扫描过普通包，则扫描
     */
    public void scannerIfNotScanned(String packageName, Predicate<Class<?>> classFilter){
        if(this.scannedListener){
            return;
        }
        Set<Class<?>> list = new FileScanner().find(packageName, classFilter).get();
        registerListeners(list.toArray(new Class<?>[0]));
    }

    /**
     * 包扫描初始化监听器
     * @param packageName 包名
     */
    public void scannerInitListener(String packageName){
        isScannedInitListener();
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


    //**************** getter & setter ****************//

    private void isScannedListener(){
        this.scannedListener = true;
    }

    private void isScannedInitListener(){
        this.scannedInitListener = true;
    }

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

    public Resp_getLoginQQInfo.LoginQQInfo getLoginQQInfo() {
        return loginQQInfo;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getCqPath() {
        return cqPath;
    }

    public void setCqPath(String cqPath) {
        this.cqPath = cqPath;
    }

    public static Integer getHTTP_API_port() {
        return HTTP_API_port;
    }

    public void setHTTP_API_port(Integer HTTP_API_port) {
        this.HTTP_API_port = HTTP_API_port;
    }

    public static String getHTTP_API_ip() {
        return HTTP_API_ip;
    }

    public void setHTTP_API_ip(String HTTP_API_ip) {
        this.HTTP_API_ip = HTTP_API_ip;
    }

    public static String getHTTP_API_path() {
        return HTTP_API_path;
    }

    public void setHTTP_API_path(String HTTP_API_path) {
        this.HTTP_API_path = HTTP_API_path;
    }

    public void setLoginQQInfo(Resp_getLoginQQInfo.LoginQQInfo loginQQInfo) {
        this.loginQQInfo = loginQQInfo;
        this.localQQCode = loginQQInfo.getQq();
        this.localQQNick = loginQQInfo.getNick();
    }




}
