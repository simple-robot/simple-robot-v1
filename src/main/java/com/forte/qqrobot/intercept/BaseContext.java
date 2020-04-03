package com.forte.qqrobot.intercept;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 基础上下文对象，一个上下文对象首先应该针对某个元素.
 * 且这个元素是可修改的。
 * 并且提供一个上下文map以实现数据携带
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseContext<T> {

    private T value;

    /** 当前上下文参数 */
    private Map<String, Object> contextMap;

    /** 全局上下文参数 */
    private final Map<String, Object> globalContextMap;

    public T getValue(){
        return value;
    }
    public void setValue(T newValue){
        this.value = newValue;
    }

    /**
     * 获取contextMap
     */
    protected Map<String, Object> getContextMap(){
        if(contextMap == null){
            contextMap = new HashMap<>(4);
        }
        return contextMap;
    }

    /**
     * 获取全局Map
     */
    protected Map<String, Object> getGlobalContextMap(){
        return globalContextMap;
    }

    /**
     * 获取一个当前上下文参数
     * @param key key
     * @return 当前上下文参数
     */
    public Object get(String key){
        return getContextMap().get(key);
    }

    /**
     * 记录一个当前上下文参数
     * @param key key
     * @param value value
     */
    public Object set(String key, Object value){
        return getContextMap().put(key, value);
    }

    /**
     * 获取一个全局上下文参数
     * @param key key
     * @return key
     */
    public Object getGlobal(String key){
        return getGlobalContextMap().get(key);
    }

    public Object setGlobal(String key, Object value){
        return getGlobalContextMap().put(key, value);
    }

    /**
     * 清除内容。只会清除当前域
     */
    public void clear(){
        getContextMap().clear();
    }


    /**
     * 构造
     * @param value 上下文所对应的值
     * @param globalContextMap 全局上下文值。
     * */
    public BaseContext(T value, Map<String, Object> globalContextMap){
        this.value = value;
        this.globalContextMap = globalContextMap;
    }

}
