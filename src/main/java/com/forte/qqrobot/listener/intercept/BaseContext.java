package com.forte.qqrobot.listener.intercept;

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

    private Map<String, Object> contextMap;

    public T getValue(){
        return value;
    }
    public void setValue(T newValue){
        this.value = newValue;
    }

    /**
     * 获取contextMap
     */
    private Map<String, Object> getContextMap(){
        if(contextMap == null){
            contextMap = new HashMap<>(4);
        }
        return contextMap;
    }

    public Object get(String key){
        return getContextMap().get(key);
    }

    public Object set(String key, Object value){
        return getContextMap().put(key, value);
    }

    public void clear(){
        getContextMap().clear();
    }


    /** 构造 */
    public BaseContext(T value){
        this.value = value;
    }

}
