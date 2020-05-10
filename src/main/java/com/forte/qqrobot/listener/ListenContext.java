package com.forte.qqrobot.listener;

import com.forte.qqrobot.intercept.BaseContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 监听函数中使用的上下文
 * 有两种值，一个是永久生效的全局上下文，一个是单个对象生效的当前上下文
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ListenContext extends BaseContext<Void> implements Closeable {


    /** 本地线程内容 */
    private static final ThreadLocal<ListenContext> LOCAL = new ThreadLocal<>();

    /** 设置一个本地线程内容 */
    public static void setLocalValue(ListenContext listenContext){
        LOCAL.set(listenContext);
    }

    /** 将自己存入线程LOCAL中 */
    public void setLocal(){
        setLocalValue(this);
    }

    /** 获取本地线程中可能存在的值 */
    public static ListenContext getLocal(){
        return LOCAL.get();
    }


    public ListenContext(Map<String, Object> globalContext) {
        super(null, globalContext);
    }


    public ListenContext(Map<String, Object> globalContext, Map<String, Object> normalMap) {
        super(null, globalContext, normalMap);
    }

    /**
     * 获取实例对象
     */
    public static ListenContext getInstance(Map<String, Object> globalContext) {
        return new ListenContext(globalContext);
    }

    /**
     * 获取实例对象
     */
    public static ListenContext getInstance(Map<String, Object> globalContext, Map<String, Object> normalMap) {
        return new ListenContext(globalContext, normalMap);
    }

    /**
     * 初始化normalMap
     */
    private Map<String, Object> getNormalMap() {
        return getContextMap();
    }

    /**
     * 无效参数
     */
    @Override
    @Deprecated
    public Void getValue(){ return null; }

    /**
     * 默认的get方法。<br>
     * 会优先从当前域获取，获取不到则寻找全局域
     *
     * @param key key
     * @return 值
     */
    @Override
    public Object get(String key) {
        Object normalGet = getContextMap().get(key);
        return normalGet == null ? getGlobal(key) : normalGet;
    }


    /**
     * 从当前域中获取
     *
     * @param key key
     * @return 值
     */
    public Object getNormal(String key) {
        return get(key);
    }

    /**
     * 记录一个当前域值, 相当于方法{@link #set(String, Object)}
     * @param key   键
     * @param value 值
     * @see #set(String, Object)
     */
    public Object setNormal(String key, Object value) {
        return set(key, value);
    }


    /**
     * 仅清除全局域
     */
    public void clearGlobal() {
        getGlobalContextMap().clear();
    }

    /**
     * 当前域keySet
     */
    public Set<String> normalKeySet() {
        return getContextMap().keySet();
    }

    /**
     * 全局域keySet
     */
    public Set<String> globalKeySet() {
        return getGlobalContextMap().keySet();
    }

    /**
     * 清理ThreadLocal
     */
    @Override
    public void close() {
        LOCAL.remove();
    }

    /**
     * 清理ThreadLocal
     */
    public static void removeLocal(){
        LOCAL.remove();
    }

}
