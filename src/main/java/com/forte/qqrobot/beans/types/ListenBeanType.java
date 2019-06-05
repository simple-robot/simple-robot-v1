package com.forte.qqrobot.beans.types;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 此枚举定义监听函数是否为单例、单例的类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum ListenBeanType {

    /** 以监听函数为准的单例，即每一个监听函数对应一个唯一单例 */
    LISTENER_SINGLE(supplier -> {
        //此函数仅获取一次
        Object single = supplier.get();
        return () -> single;
    }),
    /** 全局单例，即真正的单例，所有函数所有情况下都只存在唯一单例，如果存在依赖，则依赖也仅只存入一次 */
    GLOBAL_SINGLE(supplier -> {
        //将此类型保存至依赖资源管理中心
        Object single = supplier.get();

        //TODO 存入单例

        return null;
    }),
    /** 非单例，每一次都新建一个新的实例对象, 即使用原本传入的新实例获取方法原样返回不做处理 */
    NEW_INSTANCE_EVERY_TIME(supplier -> supplier)
    ;

    /**
     * 构造，需要保证两个?数据类型相同
     */
    ListenBeanType(Function<Supplier<?>, Supplier<?>> getter){
        this.getter = getter;
    }

    /** 获取一个实例对象，以此实例对象为准获取一个获取对象实例的函数，需要保证Function的两个？类型需要相同。 */
    private final Function<Supplier<?>, Supplier<?>> getter;

    /**
     * 通过对象的实例方法来获取对应的获取方式
     */
    public <T> Supplier<T> getSupplier(Supplier<T> supplier){
        return (Supplier<T>) getter.apply(supplier);
    }


}
