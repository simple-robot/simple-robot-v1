/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     SingleFactory.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 基于CAS原理的单例工厂<br>
 * 单例原理为基于CAS的乐观锁懒汉式单例   <br>
 * 使用工厂方法创建一个不会重名的实例对象 <br>
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date 2018/12/19 15:59
 * @version 2.0
 * @since JDK1.8
 **/
public class SingleFactory {

    /** 单例工厂的ID，如果不指定则会使用一个随机的UUID */
    private final String ID;

    /** 线程同步Map，保存全部ID的单例工厂 */
    private static final Map<String, SingleFactory> FACTORY_MAP = new ConcurrentHashMap<>();

    /** 构造 */
    private SingleFactory(String id){
        if(FACTORY_MAP.get(id) == null){
            this.ID = id;
        }else{
            throw new SecurityException("不可以重复创建已经存在的ID实例。");
        }
    }

    /**
     * 获取或创建
     */
    private synchronized static SingleFactory getInstance(String id){
        SingleFactory get = FACTORY_MAP.get(id);
        if(get == null){
            get = new SingleFactory(id);
            FACTORY_MAP.put(get.ID, get);
        }
        return get;
    }


    /** 工厂 */
    public synchronized static SingleFactory build(Object obj){
        if(obj.getClass().equals(String.class)){
            //字符串
            return getInstance((String)obj);
        }else if(obj instanceof Number){
            //是数字类的
            return getInstance("NO."+ obj);
        }else if(obj.getClass().equals(char.class) || obj.getClass().equals(Character.class)){
            //是数字类的
            return getInstance(String.valueOf(Integer.valueOf((Character)obj)));
        }else{
            return getInstance(obj.getClass().toString());
        }
    }

    /** 工厂 */
    public synchronized static SingleFactory build(){
        return getInstance(UUID.randomUUID().toString().replaceAll("-", ""));
    }


    /**
     * 清空所有保存的数据
     */
    public void clear(){
        SINGLE_MAP.clear();
    }

    //**************** 以下为单例工厂内容 ****************//


    /**
     * 单例仓库-线程安全Map
     */
    private final Map<Class , SingleBean> SINGLE_MAP = Collections.synchronizedMap(new HashMap<>());

    /**
     * 获取单例，如果没有此类的记录则返回空
     */
    public final <T> T get(Class<? extends T> clz){
        return Optional.ofNullable(SINGLE_MAP.get(clz)).map(s -> (T)s.get()).orElse(null);
    }

    /**
     * 获取单例，如果没有则尝试使用反射获取一个新的，将会被记录。
     * 如果创建失败将会抛出相应的异常
     * @param <T>
     * @return
     */
    public final <T> T getOrNew(Class<T> clz){
        return getOrNew(clz, (Object[]) null);
    }

    public final <T> T getOrNew(Class<? extends T> clz , Object... params){
        return Optional.ofNullable(SINGLE_MAP.get(clz)).map(s -> (T)s.get()).orElseGet(() -> {
            //如果没有，记录
            return set(clz, () -> {
                try {
                    //判断参数数量
                    if(params != null && params.length > 0){
                        //如果数量大于0
                        //获取参数的class数组
                        Class[] classes = Arrays.stream(params).map(Object::getClass).toArray(Class[]::new);
                        //尝试使用反射获取一个新对象
                        return clz.getConstructor(classes).newInstance(params);
                    }else{
                        //没有参数，直接获取
                        return clz.newInstance();
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }, false).get();
        });
    }


    /**
     * 如果存在则获取，不存在则赋值
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(Class<? extends T> clz, T t){
        return Optional.ofNullable((T)get(clz)).orElseGet(() -> setAndGet(clz, t));
    }

    /**
     * 如果存在则获取，不存在则赋值，不指定class对象
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(T t){
        return Optional.ofNullable((T)get(t.getClass())).orElseGet(() -> setAndGet(t));
    }


    /**
     * 如果存在则获取，不存在则赋值
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(Class<? extends T> clz, Supplier<? extends T> supplier){
        return Optional.ofNullable((T)get(clz)).orElseGet(() -> setAndGet(clz, supplier));
    }

    /**
     * 如果存在则获取，不存在则赋值，不指定class对象
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(Supplier<? extends T> supplier){
        return Optional.ofNullable((T)get(supplier.get().getClass())).orElseGet(() -> setAndGet(supplier));
    }

    /**
     * 重设一个单例
     * @param clz
     * @param t
     * @param <T>
     */
    public final <T> void reset(Class<? extends T> clz , T t){
        set(clz, t, true);
    }


    /**
     * 重设一个单例，不指定class
     * @param t
     * @param <T>
     */
    public final <T> void reset(T t){
        reset(t.getClass(), t);
    }

    /**
     * 重设一个单例并获取单例实例
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(Class<? extends T> clz , T t){
        return set(clz, t, true).get();
    }


    /**
     * 重设一个单例并获取单例实例，不指定class
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(T t){
        return resetAndGet((Class<T>)t.getClass(), t);
    }

    /**
     * 重设一个单例
     * @param clz
     * @param supplier
     * @param <T>
     */
    public final <T> void reset(Class<? extends T> clz , Supplier<? extends T> supplier){
        set(clz, supplier, true);
    }


    /**
     * 重设一个单例，不指定class
     * @param supplier
     * @param <T>
     */
    public final <T> void reset(Supplier<? extends T> supplier){
        reset(supplier.get().getClass(), supplier);
    }

    /**
     * 重设一个单例并获取单例实例
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(Class<? extends T> clz , Supplier<? extends T> supplier){
        return set(clz, supplier, true).get();
    }

    /**
     * 重设一个单例并获取单例实例，不指定class
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(Supplier<? extends T> supplier){
        return resetAndGet((Class<? extends T>)supplier.get().getClass(), supplier);
    }


    /**
     * 记录一个单例
     * @param clz
     * @param t
     * @param <T>
     */
    public final <T> void set(Class<? extends T> clz , T t){
        set(clz, t, false);
    }

    /**
     * 记录一个单例，不指定class
     * @param t
     * @param <T>
     */
    public final <T> void set(T t){
        set(t.getClass(), t);
    }

    /**
     * 记录一个单例并获取单例实例
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(Class<? extends T> clz , T t){
        return set(clz, t, false).get();
    }


    /**
     * 记录一个单例并获取单例实例，不指定class
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(T t){
        return setAndGet((Class<T>)t.getClass(), t);
    }

    /**
     * 记录一个单例
     * @param clz
     * @param supplier
     * @param <T>
     */
    public final <T> void set(Class<? extends T> clz , Supplier<? extends T> supplier){
        set(clz, supplier, false);
    }

    /**
     * 记录一个单例，不指定class
     * @param supplier
     * @param <T>
     */
    public final <T> void set(Supplier<? extends T> supplier){
        set(supplier.get().getClass(), supplier);
    }

    /**
     * 记录一个单例并获取单例实例
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(Class<? extends T> clz , Supplier<? extends T> supplier){
        return set(clz, supplier, false).get();
    }

    /**
     * 记录一个单例，不指定class
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(Supplier<? extends T> supplier){
        return setAndGet((Class<? extends T>)supplier.get().getClass(), supplier);
    }


    /**
     * 记录一个单例对象或重设一个单例-对象
     * @param clz
     * @param t
     * @param <T>
     */
    private final <T> SingleBean<? extends T> set(Class<? extends T> clz , T t , boolean reset){
        return set(clz , () -> t , reset);
    }

    /**
     * 记录一个单例对象或重设一个单例-函数接口
     * 赋值相关重载方法的根方法
     * 使用synchronized标记
     * @param clz
     * @param supplier
     * @param <T>
     */
    private synchronized final <T> SingleBean<? extends T> set(Class<? extends T> clz , Supplier<? extends T> supplier , boolean reset){
        SingleBean<? extends T> singleBean = SINGLE_MAP.get(clz);
        if(!reset && singleBean != null){
            //如果已经存在，直接返回此对象
            return singleBean;
        }

        //创建新对象
        singleBean = new SingleBean<>(supplier);
        SINGLE_MAP.put(clz, singleBean);
        return singleBean;
    }


    /**
     * 内部单例类，应当是基于CAS原理的懒汉式单例类
     * @param <T>
     */
    private final class SingleBean<T> {
        /**
         * 获取实例的方法
         */
        private final Supplier<T> supplier;
        private final AtomicReference<T> single = new AtomicReference<>();

        /**
         * 获取单例
         */
        private T get() {
            //获取
            T currect = single.get();
            //如果存在直接返回
            if (currect != null) {
                return currect;
            }
            //创建并赋值
            return single.updateAndGet(old -> supplier.get());
        }

        /**
         * 构造
         */
        private SingleBean(Supplier<T> supplier) {
            this.supplier = supplier;
        }
    }

}
