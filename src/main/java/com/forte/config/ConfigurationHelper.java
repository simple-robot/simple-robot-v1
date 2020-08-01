/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ConfigurationHelper.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.config;

import com.forte.config.resolve.ConfigResolvor;

import java.util.Map;
import java.util.Properties;

/**
 *
 * 工具入口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ConfigurationHelper {

    /**
     * 解析一个类并转化为可注入的Configuration对象。
     * 如果你里面一个对应的注解都没
     * 那反正也没变化
     * @param type  类型
     */
    public static <T> InjectableConfig<T> toInjectable(Class<T> type){
        return ConfigResolvor.toInjectable(type);
    }

    /**
     * 为一个类注入配置信息
     * @param config            配置类
     * @param configProperties  配置信息
     */
    public static <T> void inject(T config, Properties configProperties){
        Class<T> configClass = getClass(config);
        InjectableConfig<T> injectableConfig = toInjectable(configClass);
        injectableConfig.inject(config, configProperties);
    }

    /**
     * 为一个类注入配置信息
     * @param config            配置类
     * @param configMap  配置信息
     */
    public static <T> void inject(T config, Map<String, Object> configMap){
        Class<T> configClass = getClass(config);
        InjectableConfig<T> injectableConfig = toInjectable(configClass);
        injectableConfig.inject(config, configMap);
    }

    /**
     * 为一个类注入配置信息
     * @param config            配置类
     * @param key    配置信息的key
     * @param value  配置信息的值
     */
    public static <T> void inject(T config, String key, Object value){
        Class<T> configClass = getClass(config);
        InjectableConfig<T> injectableConfig = toInjectable(configClass);
        injectableConfig.inject(config, key, value);
    }

    /**
     * 获取对应的Class
     */
    private static <T> Class<T> getClass(T obj){
        return (Class<T>) obj.getClass();
    }

}
