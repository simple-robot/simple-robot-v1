/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Depend.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 资源对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Depend<V> implements Comparable<Depend<?>> {

    /** 数据类型 */
    private final Class<V> TYPE;

    /** 此依赖的名称 */
    private final String NAME;

    /** 此依赖对象是否为单例 */
    private final boolean single;

    /** 获取实例空对象的函数 */
    private final Supplier<V> supplier;

    /** 传入一个实例对象并对其中的参数进行注入 */
    private final Consumer<V> injectDepend;

    /** 使用额外参数对实例对象进行注入，需要保证额外参数内不存在更深一级的参数 */
    private final BiConsumer<V, DependGetter> injectAdditionalDepend;

    /** 优先级，升序排序 */
    private final int priority;

    /** 是否需要被初始化 */
    private final boolean init;

    /**
     * 构造
     * @param name          依赖名称
     * @param type          依赖类型
     * @param single        是否为单例依赖
     * @param supplier      依赖空实例获取函数
     * @param injectDepend  依赖注入函数
     * @param injectAdditionalDepend  额外依赖注入函数
     */
    public Depend(String name, Class<V> type, boolean single, Supplier<V> supplier, Consumer<V> injectDepend, BiConsumer<V, DependGetter> injectAdditionalDepend, boolean init, int priority) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(injectDepend);
        Objects.requireNonNull(injectAdditionalDepend);

        this.NAME = name;
        this.TYPE = type;
        this.single = single;
        this.supplier = supplier;
        this.injectDepend = injectDepend;
        this.injectAdditionalDepend = injectAdditionalDepend;
        this.init = init;
        this.priority = priority;
    }

    /**
     * 获取类型
     */
    public Class<V> getType(){
        return TYPE;
    }

    /**
     * 获取依赖名
     */
    public String getName(){
        return NAME;
    }

    /**
     * 获取实例
     */
    public V getInstance(){
        //获取空实例
        V instance = getEmptyInstance();
        //注入参数
        inject(instance);
        return instance;
    }

    /**
     * 获取一个空实例
     */
    public V getEmptyInstance(){
        return supplier.get();
     }

     /**
     * 对一个实例对象进行依赖注入
     */
    public void inject(V v){
        injectDepend.accept(v);
    }

    /** 通过额外参数对象进行注入 */
    public void injectAdditional(V v, DependGetter additionalDepends){
        injectAdditionalDepend.accept(v, additionalDepends);
    }

    /**
     * 判断是否为单例依赖
     */
    public boolean isSingle(){
        return single;
    }


    @Override
    public String toString() {
        return "{" +
                "TYPE=" + TYPE +
                ", NAME='" + NAME + '\'' +
                '}';
    }

    public boolean isInit() {
        return init;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Depend o) {
        if(o == null){
            return 1;
        }
        return Integer.compare(priority, o.priority);
    }
}
