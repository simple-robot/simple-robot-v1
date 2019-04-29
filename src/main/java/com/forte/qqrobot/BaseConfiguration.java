package com.forte.qqrobot;

import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
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

    /** 本机QQ信息, 一般唯一，使用静态 */
    private static LoginQQInfo loginQQInfo = null;

    /** 全部初始化监听器 */
    private Set<InitListener> initListeners = new HashSet<>();

    /** 本机QQ号, 一般唯一，使用静态 */
    private static String localQQCode = "";

    /** 本机QQ的昵称, 一般唯一，使用静态 */
    private static String localQQNick = "";

    /** 使用的编码格式，默认为UTF-8 */
    private String encode = "UTF-8";

    /** 酷Q根路径的配置，默认为null, 路径一般不会有多个，使用静态即可 */
    private static String cqPath;

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
        BaseConfiguration.localQQNick = localQQNick;
    }

    public static String getLocalQQCode() {
        return BaseConfiguration.localQQCode;
    }

    public void setLocalQQCode(String localQQCode) {
        BaseConfiguration.localQQCode = localQQCode;
    }

    public LoginQQInfo getLoginQQInfo() {
        return loginQQInfo;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public static String getCqPath() {
        return cqPath;
    }

    public void setCqPath(String cqPath) {
        BaseConfiguration.cqPath = cqPath;
    }

    public void setLoginQQInfo(LoginQQInfo loginQQInfo) {
        BaseConfiguration.loginQQInfo = loginQQInfo;
        BaseConfiguration.localQQCode = loginQQInfo.getQQ();
        BaseConfiguration.localQQNick = loginQQInfo.getName();
    }




}
