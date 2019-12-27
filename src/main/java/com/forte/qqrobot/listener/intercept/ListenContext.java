package com.forte.qqrobot.listener.intercept;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 监听函数中使用的上下文
 * 有两种值，一个是永久生效的全局上下文，一个是单个对象生效的当前上下文
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ListenContext {

    /** 全局生效的数据Map */
    private static Map<String, Object> globalContext = new ConcurrentHashMap<>(4);

    /** 单次生效的Map, 使用懒加载 */
    private Map<String, Object> normalMap;

    /** 获取实例对象 */
    public static ListenContext getInstance(){
        return new ListenContext();
    }

    /**
     * 初始化normalMap
     */
    private Map<String, Object> getNormalMap(){
        if(normalMap == null){
            normalMap = new HashMap<>(4);
        }
        return normalMap;
    }

    /**
     * 默认的get方法。<br>
     *     会优先从当前上下文获取，获取不到则寻找全局上下文
     * @param key key
     * @return 值
     */
    public Object get(String key) {
        Object normalGet = getNormalMap().get(key);
        return normalGet == null ? globalContext.get(key) : normalGet;
    }


    /**
     * 从当前上下文中获取
     * @param key key
     * @return 值
     */
    public Object getNormal(String key){
        return getNormalMap().get(key);
    }

    /**
     * 从全局上下文中获取
     * @param key key
     * @return 值
     */
    public Object getGlobal(String key){
        return globalContext.get(key);
    }

    /**
     * 默认的记录一个上下文值，默认记录在当前上下文
     * @param key    键
     * @param value  值
     */
    public Object set(String key, Object value){
        return setNormal(key, value);
    }

    /**
     * 记录一个当前上下文值
     * @param key    键
     * @param value  值
     */
    public Object setNormal(String key, Object value){
        return getNormalMap().put(key, value);
    }

    /**
     * 记录一个全局上下文值
     * @param key    键
     * @param value  值
     */
    public Object setGlobal(String key, Object value){
        return globalContext.put(key, value);
    }


    /**
     * 清除上下文。默认为清除当前上下文
     */
    public void clear(){
        getNormalMap().clear();
    }

    /**
     * 清除全局上下文
     */
    public void clearGlobal(){
        clearContext();
    }

    /**
     * 清除全局上下文
     */
    public static void clearContext(){
        globalContext.clear();
    }

    /**
     * 当前上下文keySet
     */
    public Set<String> normalKeySet(){
        return getNormalMap().keySet();
    }

    /**
     * 全局上下文keySet
     */
    public Set<String> globalKeySet(){
        return keySet();
    }

    /**
     * 全局上下文keySet
     */
    public static Set<String> keySet(){
        return globalContext.keySet();
    }













}
