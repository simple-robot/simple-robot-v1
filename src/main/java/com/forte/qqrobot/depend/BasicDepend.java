/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BasicDepend.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend;


import com.forte.qqrobot.constant.PriorityConstant;

/**
 * 基础数据类型的Depend对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BasicDepend<V> extends Depend<V> {

    /**
     * 构造
     * @param name  基础数据类型名称
     * @param type  基础数据类型
     * @param value 值
     */
    public BasicDepend(String name, Class<V> type, V value) {
        super(name, type, true, () -> value, v -> {}, (v, add) -> {}, false, PriorityConstant.FIRST_LAST);
    }

    public static <T> BasicDepend<T> getInstance(String name, T value){
        return new BasicDepend<>(name, (Class<T>) value.getClass(), value);
    }

}
