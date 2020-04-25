package com.forte.qqrobot;

import java.util.Set;
import java.util.function.BiConsumer;

/**
 * 配置文件中的全部配置信息
 * 理论上不能被修改
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ConfigProperties {
    /**
     * 获取一个Property值
     * @param key 键
     * @return 值，如果没有则为null
     */
    String getProperty(String key);

    /**
     * 获取全部的key
     * @return
     */
    Set<String> keys();

    /**
     * 遍历
     * @param foreachConsumer
     */
    default void foreach(BiConsumer<String, String> foreachConsumer){
        Set<String> keys = keys();
        for (String key : keys) {
            foreachConsumer.accept(key, getProperty(key));
        }
    }

    /**
     * 获取一个Property值
     * @param key 键
     * @return 值，如果没有则为null
     */
    default String getProperty(String key, String defaultValue){
        String v = getProperty(key);
        return v == null ? defaultValue : v;
    }
}
