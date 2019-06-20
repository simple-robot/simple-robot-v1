package com.forte.qqrobot.depend;

import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 额外的参数对象
 * 此类主要提供给{@link com.forte.qqrobot.listener.invoker.ListenerMethod} 来在调用函数的时候添加动态参数
 * 此类不区分是否为常量，请保证如果存在常量类型，需要使其类型唯一。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AdditionalDepends implements DependGetter {

    /**
     * 根据类型的map
     */
    private final Map<Class, Object> type;

    /**
     * 根据名称的map
     */
    private final Map<String, Object> name;

    /**
     * 空的对象
     */
    private static final AdditionalDepends EMPTY = new AdditionalDepends(Collections.EMPTY_MAP, Collections.EMPTY_MAP);

    private AdditionalDepends(Map<Class, Object> type, Map<String, Object> name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 判断自己是否为空
     */
    public boolean isEmpty(){
        return this.equals(EMPTY) || ((name.isEmpty()) && (type.isEmpty()));
    }

    /**
     * 获取空参数对象
     */
    public static AdditionalDepends getEmpty() {
        return EMPTY;
    }

    /**
     * 工厂近供包内使用，请尽可能保证不出现类型冲突
     */
    public static AdditionalDepends getInstance(Map<String, Object> nameObjMap) {
        if (nameObjMap == null || nameObjMap.isEmpty()) {
            return EMPTY;
        }

        //遍历
        //普通依赖
        Map<Class, Object> typeMap = new HashMap<>();
        Map<String, Object> nameMap = new HashMap<>();

        nameObjMap.forEach((k, v) -> {
            //不是基础类型
            typeMap.put(v.getClass(), v);
            nameMap.put(k, v);
        });
        //构建
        return new AdditionalDepends(typeMap, nameMap);
    }

    /**
     * 通过Class对象获取实例
     */
    @Override
    public <T> T get(Class<T> type) {
        T o = (T) this.type.get(type);
        if (o != null) {
            return o;
        } else {
            Class[] classes = this.type.keySet().stream().filter(t -> FieldUtils.isChild(t, type)).toArray(Class[]::new);
            if (classes.length > 1) {
                //不止1个，但是对于额外参数来讲可能性不大
                throw new DependResourceException("存在不止一个[" + type + "]类型的子类型：" + Arrays.toString(classes) + ",请尝试使用名称获取。");
            } else if (classes.length == 0) {
                //没有，返回null
                return null;
            } else {
                //只有一个
                return (T) this.type.get(classes[0]);

            }
        }
    }

    /**
     * 通过名称和类型获取指定类型的对象实例
     */
    @Override
    public <T> T get(String name, Class<T> type) {
        return (T) get(name);
    }

    /**
     * 仅通过名称获取对象实例
     */
    @Override
    public Object get(String name) {
        return this.name.get(name);
    }

    /**
     * 获取常量参数
     * 常量参数：8大基本数据类型、8大基础数据类型的封装类、String类
     */
    @Override
    public <T> T constant(String name, Class<T> type) {
        return (T) get(name);
    }

    /**
     * 获取常量参数
     * 常量参数：8大基本数据类型、8大基础数据类型的封装类、String类
     */
    @Override
    public Object constant(String name) {
        return get(name);
    }
}
