package com.forte.qqrobot.depend;

import org.apache.commons.collections.iterators.EmptyListIterator;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 通过实现此接口以指定一个获取资源实例的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface DependGetter {

    /**
     * 通过Class对象获取实例
     */
    <T> T get(Class<T> clazz);

    /**
     * 通过名称和类型获取指定类型的对象实例
     */
    <T> T get(String name, Class<T> type);

    /**
     * 仅通过名称获取对象实例
     */
    Object get(String name);

    /**
     * 获取常量参数
     * 常量参数：8大基本数据类型、8大基础数据类型的封装类、String类
     */
    <T> T constant(String name, Class<T> type);

    /**
     * 获取常量参数
     * 常量参数：8大基本数据类型、8大基础数据类型的封装类、String类
     */
    Object constant(String name);

    /**
     * 根据类型获取其所有实现类
     * @param type 类型
     */
    default <T> List<T> getListByType(Class<T> type){
        return Collections.emptyList();
    }

}
