package com.forte.qqrobot.depend;

import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.exception.RobotDevException;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

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
     * 只有当不是基础数据类型的时候才会在类型Map中进行保存
     * 否则将只能按照名称保存
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
     * 请尽可能保证不出现类型冲突
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
            //只有当不是基础类型的时候才根据类型添加
            if(!BasicResourceWarehouse.isBasicType(v)){
                // 类型重复，覆盖处理
                typeMap.put(v.getClass(), v);
//                typeMap.merge(v.getClass(), v, typeMapMergeThrows(v.getClass()));
            }
//            nameMap.put(k, v);
            // 名称绝对不可重复
            nameMap.merge(k, v, nameMapMergeThrows(k));
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

    /**
     * 当类型Map出现键冲突的时候，抛出异常并提示信息
     * @param type  冲突的键类型
     */
    private static <V> BiFunction<? super V, ? super V, ? extends V> typeMapMergeThrows(Class<?> type){
        return (old, val) -> {
            throw new RobotDevException("动态参数类型出现重复！" +
                    "\t\r\nkey(type): \t\t" + type +
                    "\t\r\nvalue-old: " + old +
                    "\t\r\nvalue-new: " + val);
        };
    }


    /**
     * 当名称Map出现键冲突的时候，抛出异常并提示信息
     * @param key  冲突的键名称
     */
    private static <V> BiFunction<? super V, ? super V, ? extends V> nameMapMergeThrows(String key){
        return (old, val) -> {
            throw new RobotDevException("动态参数名称出现重复！" +
                    "\t\r\nkey(name): \t\t" + key +
                    "\t\r\nvalue-old: " + old +
                    "\t\r\nvalue-new: " + val);
        };
    }



}
