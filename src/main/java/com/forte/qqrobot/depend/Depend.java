package com.forte.qqrobot.depend;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 资源对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Depend<V> {

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


    /**
     * 构造
     * @param name          依赖名称
     * @param type          依赖类型
     * @param single        是否为单例依赖
     * @param supplier      依赖空实例获取函数
     * @param injectDepend  依赖注入函数
     */
    public Depend(String name, Class<V> type, boolean single, Supplier<V> supplier, Consumer<V> injectDepend) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(injectDepend);

        this.NAME = name;
        this.TYPE = type;
        this.single = single;
        this.supplier = supplier;
        this.injectDepend = injectDepend;
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

    /**
     * 判断是否为单例依赖
     */
    public boolean isSingle(){
        return single;
    }


    @Override
    public String toString() {
        return "Depend{" +
                "TYPE=" + TYPE +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
