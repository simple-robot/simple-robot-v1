package com.forte.qqrobot;

import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.depend.DependInjector;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.*;

/**
 * 配置类的根类，定义包扫描方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/4 18:02
 * @since JDK1.8
 **/
public class BaseConfiguration {

    //**************************************
    //*          config params
    //**************************************


    /** 是否扫描了初始化监听器 */
//    private boolean scannedInitListener = false;

    /** 本机QQ信息, 一般唯一，使用静态 */
    private static LoginQQInfo loginQQInfo = null;

    /** 全部初始化监听器 */
    private Set<InitListener> initListeners = new HashSet<>();

    /** 本机QQ号, 一般唯一，使用静态 */
    private static String localQQCode = "";

    /** 本机QQ的昵称, 一般唯一，使用静态 */
    private static String localQQNick = "";

    /** 使用的编码格式，默认为UTF-8，静态，全局唯一 */
    private static String encode = "UTF-8";

    /** 酷Q根路径的配置，默认为null, 路径一般不会有多个，使用静态即可 */
    private static String cqPath;

    /** 需要进行的包扫描路径，默认为null */
    private Set<String> scannerPackage = new HashSet<>();

    //**************** 依赖相关 ****************//

    /** 自定义依赖对象实例化规则，假如同时使用了spring之类的框架，需要对此进行配置
     *  基本全局唯一，使用静态
     * */
    private static DependGetter dependGetter = null;

    /** 依赖实例注入器，自定义依赖实例中的依赖注入规则，假如使用了Spring这类的框架需要进行配置
     *  基本全局唯一，使用静态
     *  TODO 此参数暂不可用，是否可用再商议
     * */
    private static DependInjector dependInjector;


    //**************************************
    //*             以下为方法
    //**************************************


    /**
     * 注册监听器
     * @param listeners 监听器列表
     */
    @Deprecated
    public void registerListeners(Object... listeners){
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
                    scanSet.forEach(lm -> joiner.add(lm.getMethodToString()));
                    QQLog.info(joiner.toString());
                }
            } catch (Exception e) {
                QQLog.error("加载["+ listener.getClass() +"]的监听函数出现异常！", e);
            }
        }
    }

    /**
     * 注册监听器
     * 2019-06-05
     *
     * @param listeners 监听器列表
     */
    @Deprecated
    public void registerListeners(Class<?>... listeners){
        //获取扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();

        //遍历
        for (Class listener : listeners) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(listener);
                if(scanSet.size() > 0){
                    QQLog.info("加载["+ listener +"]的监听函数成功：");
                    StringJoiner joiner = new StringJoiner("]\r\n\t>>>", "\t>>>", "");
                    scanSet.forEach(lm -> joiner.add(lm.getMethodToString()));
                    QQLog.info(joiner.toString());
                }
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
    @Deprecated
    public void scannerListener(String packageName){
        sacnner(packageName);
    }

    /**
     * 包扫描，现在的扫描已经不再仅限于监听器了
     */
    public void sacnner(String packageName){
        //添加包路径
        scannerPackage.add(packageName);
    }

    /**
     * 包扫描初始化监听器
     * @param packageName 包名
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


    //**************** getter & setter ****************//
    /**
     * 配置需要进行扫描的路径
     */
    public void setScannerPackage(String... packages){
        if(packages != null){
            this.scannerPackage.addAll(Arrays.asList(packages));
        }
    }

    //**************** simple getter & setter ****************//


    /** 获取需要进行扫描的包路径集合 */
    public Set<String> getScannerPackage(){
        return this.scannerPackage;
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

    public static String getEncode() {
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

    /**
     * 配置loginQQInfo信息
     */
    public void setLoginQQInfo(LoginQQInfo loginQQInfo) {
        BaseConfiguration.loginQQInfo = loginQQInfo;
        BaseConfiguration.localQQCode = loginQQInfo.getQQ();
        BaseConfiguration.localQQNick = loginQQInfo.getName();
    }

    /**
     * 获取依赖获取器
     */
    public static DependGetter getDependGetter() {
        return dependGetter;
    }

    /**
     * 配置依赖获取器
     */
    public void setDependGetter(DependGetter dependGetter) {
        BaseConfiguration.dependGetter = dependGetter;
    }

    /**
     * 通过类的全包路径进行指定，通过反射创建实例
     */
    public void setDependGetter(String packPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        BaseConfiguration.dependGetter = (DependGetter) Class.forName(packPath).newInstance();
    }


    public static DependInjector getDependInjector() {
        return dependInjector;
    }

    public void setDependInjector(DependInjector dependInjector) {
        BaseConfiguration.dependInjector = dependInjector;
    }



}
