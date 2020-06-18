package com.forte.qqrobot.depend;

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

    /** 空值 */
    public static final NameTypeEntry<?>[] EMPTY = new NameTypeEntry[0];

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

    public static NameTypeEntry[] getEmpty(){
        return EMPTY;
    }

    /**
     * 将开头小写后创建
     * 基本统一使用此方法创建实例
     */
    @Deprecated
    public static NameTypeEntry getInstanceLower(String name, Class<?> type){
        return getInstance(name, type);
    }

    /**
     * 原样创建
     */
    public static NameTypeEntry getInstance(String name, Class<?> type){
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
