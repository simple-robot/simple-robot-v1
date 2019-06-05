package com.forte.qqrobot.depend;

import com.forte.qqrobot.utils.FieldUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 名称与类型对应的Entry类, 两个值都是不可更改的
 * 名称统一使用开头小写之后的类名
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class NameTypeEntry<T> implements Map.Entry<String, Class<T>> {

    private final String NAME;

    private final Class<T> TYPE;


    @Override
    public String getKey() {
        return NAME;
    }

    @Override
    public Class<T> getValue() {
        return TYPE;
    }

    private NameTypeEntry(String name, Class<T> type){
        this.NAME = name;
        this.TYPE = Objects.requireNonNull(type, "类型不可为空");
    }

    /**
     * 将开头小写后创建
     * 基本统一使用此方法创建实例
     */
    public static <T> NameTypeEntry getInstanceLower(String name, Class<T> type){
        return getInstance(name == null ? null : FieldUtils.headLower(name), type);
    }

    /**
     * 将开头大写后创建
     */
    public static <T> NameTypeEntry getInstanceUpper(String name, Class<T> type){
        return getInstance(name == null ? null : FieldUtils.headUpper(name), type);
    }

    /**
     * 原样创建
     */
    public static <T> NameTypeEntry getInstance(String name, Class<T> type){
        return new NameTypeEntry(name, type);
    }



    @Override
    @Deprecated
    public Class<T> setValue(Class<T> value) {
        throw new RuntimeException("此类不可修改value值", new IllegalAccessException());
    }

    @Override
    public String toString() {
        return "{"+NAME+"="+TYPE+"}";
    }
}
