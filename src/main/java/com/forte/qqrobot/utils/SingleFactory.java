package com.forte.qqrobot.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 基于CAS原理的单例工厂<br>
 * 单例原理为基于CAS的乐观锁懒汉式单例<br>
 * 方法内全部为final类型的静态方法，所以本类为抽象类且没有继承的必要<br>
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date 2018/12/19 15:59
 * @version 2.0
 * @since JDK1.8
 **/
public abstract class SingleFactory {

    /**
     * 单例仓库-线程安全Map
     */
    private static final Map<Class , SingleBean> SINGLE_MAP = Collections.synchronizedMap(new HashMap<>());

    /**
     * 获取单例，如果没有此类的记录则返回空
     */
    public static final <T> T get(Class<? extends T> clz){
        return Optional.ofNullable(SINGLE_MAP.get(clz)).map(s -> (T)s.get()).orElse(null);
    }

    /**
     * 获取单例，如果没有则尝试使用反射获取一个新的，将会被记录。
     * 如果创建失败将会抛出相应的异常
     * @param <T>
     * @return
     */
    public static final <T> T getOrNew(Class<T> clz){
        return getOrNew(clz, (Object[]) null);
    }

    public static final <T> T getOrNew(Class<? extends T> clz , Object... params){
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
    public static final <T> T getOrSet(Class<? extends T> clz, T t){
        return Optional.ofNullable((T)get(clz)).orElseGet(() -> setAndGet(clz, t));
    }

    /**
     * 如果存在则获取，不存在则赋值，不指定class对象
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> T getOrSet(T t){
        return Optional.ofNullable((T)get(t.getClass())).orElseGet(() -> setAndGet(t));
    }


    /**
     * 如果存在则获取，不存在则赋值
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public static final <T> T getOrSet(Class<? extends T> clz, Supplier<? extends T> supplier){
        return Optional.ofNullable((T)get(clz)).orElseGet(() -> setAndGet(clz, supplier));
    }

    /**
     * 如果存在则获取，不存在则赋值，不指定class对象
     * @param supplier
     * @param <T>
     * @return
     */
    public static final <T> T getOrSet(Supplier<? extends T> supplier){
        return Optional.ofNullable((T)get(supplier.get().getClass())).orElseGet(() -> setAndGet(supplier));
    }

    /**
     * 重设一个单例
     * @param clz
     * @param t
     * @param <T>
     */
    public static final <T> void reset(Class<? extends T> clz , T t){
        set(clz, t, true);
    }


    /**
     * 重设一个单例，不指定class
     * @param t
     * @param <T>
     */
    public static final <T> void reset(T t){
        reset(t.getClass(), t);
    }

    /**
     * 重设一个单例并获取单例实例
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> T resetAndGet(Class<? extends T> clz , T t){
        return set(clz, t, true).get();
    }


    /**
     * 重设一个单例并获取单例实例，不指定class
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> T resetAndGet(T t){
        return resetAndGet((Class<T>)t.getClass(), t);
    }

    /**
     * 重设一个单例
     * @param clz
     * @param supplier
     * @param <T>
     */
    public static final <T> void reset(Class<? extends T> clz , Supplier<? extends T> supplier){
        set(clz, supplier, true);
    }


    /**
     * 重设一个单例，不指定class
     * @param supplier
     * @param <T>
     */
    public static final <T> void reset(Supplier<? extends T> supplier){
        reset(supplier.get().getClass(), supplier);
    }

    /**
     * 重设一个单例并获取单例实例
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public static final <T> T resetAndGet(Class<? extends T> clz , Supplier<? extends T> supplier){
        return set(clz, supplier, true).get();
    }

    /**
     * 重设一个单例并获取单例实例，不指定class
     * @param supplier
     * @param <T>
     * @return
     */
    public static final <T> T resetAndGet(Supplier<? extends T> supplier){
        return resetAndGet((Class<? extends T>)supplier.get().getClass(), supplier);
    }


    /**
     * 记录一个单例
     * @param clz
     * @param t
     * @param <T>
     */
    public static final <T> void set(Class<? extends T> clz , T t){
        set(clz, t, false);
    }

    /**
     * 记录一个单例，不指定class
     * @param t
     * @param <T>
     */
    public static final <T> void set(T t){
        set(t.getClass(), t);
    }

    /**
     * 记录一个单例并获取单例实例
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> T setAndGet(Class<? extends T> clz , T t){
        return set(clz, t, false).get();
    }


    /**
     * 记录一个单例并获取单例实例，不指定class
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> T setAndGet(T t){
        return setAndGet((Class<T>)t.getClass(), t);
    }

    /**
     * 记录一个单例
     * @param clz
     * @param supplier
     * @param <T>
     */
    public static final <T> void set(Class<? extends T> clz , Supplier<? extends T> supplier){
        set(clz, supplier, false);
    }

    /**
     * 记录一个单例，不指定class
     * @param supplier
     * @param <T>
     */
    public static final <T> void set(Supplier<? extends T> supplier){
        set(supplier.get().getClass(), supplier);
    }

    /**
     * 记录一个单例并获取单例实例
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public static final <T> T setAndGet(Class<? extends T> clz , Supplier<? extends T> supplier){
        return set(clz, supplier, false).get();
    }

    /**
     * 记录一个单例，不指定class
     * @param supplier
     * @param <T>
     * @return
     */
    public static final <T> T setAndGet(Supplier<? extends T> supplier){
        return setAndGet((Class<? extends T>)supplier.get().getClass(), supplier);
    }


    /**
     * 记录一个单例对象或重设一个单例-对象
     * @param clz
     * @param t
     * @param <T>
     */
    private static final <T> SingleBean<? extends T> set(Class<? extends T> clz , T t , boolean reset){
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
    private static synchronized final <T> SingleBean<? extends T> set(Class<? extends T> clz , Supplier<? extends T> supplier , boolean reset){
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
    private static final class SingleBean<T> {
        /**
         * 获取实例的方法
         */
        private final Supplier<T> supplier;
        private AtomicReference<T> single = new AtomicReference<>();

        /**
         * 获取单例
         */
        private T get() {
            for (; ; ) {
                //获取
                T currect = single.get();
                //如果存在直接返回
                if (currect != null) {
                    return currect;
                }
                //创建
                currect = supplier.get();
                //原子赋值
                if (single.compareAndSet(null, currect)) {
                    return currect;
                }
            }
        }

        /**
         * 构造
         *
         * @param supplier
         */
        private SingleBean(Supplier<T> supplier) {
            this.supplier = supplier;
        }
    }

}
