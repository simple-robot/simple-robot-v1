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

}
