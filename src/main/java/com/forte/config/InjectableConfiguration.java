/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     InjectableConfiguration.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.config;

import cn.hutool.core.convert.Convert;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

/**
 * 可注入的Configuration类，注入的时候使用此类即可。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class InjectableConfiguration<T> implements InjectableConfig<T> {

    /**
     * 函数集
     * key: 配置的名称
     * value：字段类型与注入函数的键值对
     */
    private final Map<String, Map.Entry<Class, BiFunction<Object, T, T>>> injectFunctionMap;

    /**
     * 构造
     */
    public InjectableConfiguration(Map<String, Map.Entry<Class, BiFunction<Object, T, T>>> injectFunctionMap) {
        this.injectFunctionMap = injectFunctionMap;
    }

    /**
     * 注入
     *
     * @param config 配置对象
     * @param data   数据
     * @return       还是配置对象
     */
    @Override
    public T inject(T config, Properties data) {
        AtomicReference<T> configRe = new AtomicReference<>(config);
        // 遍历data值
        data.stringPropertyNames().forEach(key -> {
            String value = data.getProperty(key);
            // 当值不是空字符串的时候
            if (value != null && value.trim().length() > 0) {
                // 从值集合中获取
                Map.Entry<Class, BiFunction<Object, T, T>> entry = injectFunctionMap.get(key);
                if (entry != null) {
                    // 如果存在这个entry，获取值并注入
                    Object convertValue = Convert.convert(entry.getKey(), value);
                    // 执行注入并为config重新赋值
                    // 虽然本质上应该是不会变化config对象的
                    configRe.set(entry.getValue().apply(convertValue, config));
                }
            }
        });
        return configRe.get();
    }

    @Override
    public T inject(T config, Map<String, Object> data) {
        AtomicReference<T> configRe = new AtomicReference<>(config);
        // 遍历data
        data.forEach((key, value) -> {
            // 存在值
            if (value != null) {
                // 从值集合中获取
                Map.Entry<Class, BiFunction<Object, T, T>> entry = injectFunctionMap.get(key);
                if(entry != null){
                    // 如果存在这个entry，获取值并注入
                    Object convertValue = Convert.convert(entry.getKey(), value);
                    // 执行注入并为config重新赋值
                    // 虽然本质上应该是不会变化config对象的
                    configRe.set(entry.getValue().apply(convertValue, config));
                }
            }
        });
        return configRe.get();
    }

    @Override
    public T inject(T config, String name, Object value) {
        // 从值集合中获取
        Map.Entry<Class, BiFunction<Object, T, T>> entry = injectFunctionMap.get(name);
        if (entry != null) {
            // 如果存在这个entry，获取值并注入
            Object convertValue = Convert.convert(entry.getKey(), value);
            // 执行注入
            entry.getValue().apply(convertValue, config);
        }
        return config;
    }
}
