package com.forte.qqrobot.intercept;

/**
 * 基础上下文接口，一个上下文对象首先应该针对某个元素.
 * 且这个元素是可修改的。
 * 并且提供一个上下文map以实现数据携带
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface Context<T> {

    T getValue();

    void setValue(T newValue);

    /**
     * 获取一个当前上下文参数
     * @param key key
     * @return 当前上下文参数
     */
    Object get(String key);

    /**
     * 含义与{@link #set(String, Object)}相同。
     * @param key
     * @param value
     * @return
     */
    default Object put(String key, Object value){
        return set(key, value);
    }

    /**
     * 记录一个当前上下文参数
     * @param key key
     * @param value value
     */
    Object set(String key, Object value);

    /**
     * 获取一个全局上下文参数
     * @param key key
     * @return key
     */
    Object getGlobal(String key);


    /**
     * 记录一个全局上下文参数
     * @param key
     * @param value
     * @return
     */
    Object setGlobal(String key, Object value);

    /**
     * 清除当前域的内容
     */
    void clear();

    /**
     * 清除所有域的内容
     */
    void clearAll();

}
