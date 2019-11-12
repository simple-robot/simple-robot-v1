package com.forte.config;

import org.apache.commons.beanutils.ConvertUtils;

import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 *
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
    public InjectableConfiguration(Map<String, Map.Entry<Class, BiFunction<Object, T, T>>> injectFunctionMap){
        this.injectFunctionMap = injectFunctionMap;
    }

    /**
     * 注入
     * @param config
     * @param data
     * @return
     */
    @Override
    public T inject(T config, Properties data) {
        // 遍历data值
        data.stringPropertyNames().forEach(key -> {
            // 从值集合中获取
            Map.Entry<Class, BiFunction<Object, T, T>> entry = injectFunctionMap.get(key);
            if(entry != null){
                // 如果存在这个entry，获取值并注入
                String value = data.getProperty(key);
                Object convertValue = ConvertUtils.convert(value, entry.getKey());
                // 执行注入
                entry.getValue().apply(convertValue, config);
            }
        });
        return config;
    }

    @Override
    public T inject(T config, Map<String, Object> data) {
        return null;
    }

    @Override
    public T inject(T config, String name, Object value) {
        // 从值集合中获取
        Map.Entry<Class, BiFunction<Object, T, T>> entry = injectFunctionMap.get(name);
        if(entry != null){
            // 如果存在这个entry，获取值并注入
            Object convertValue = ConvertUtils.convert(value, entry.getKey());
            // 执行注入
            entry.getValue().apply(convertValue, config);
        }
        return config;
    }
}
