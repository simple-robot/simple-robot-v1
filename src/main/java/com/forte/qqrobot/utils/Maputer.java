package com.forte.qqrobot.utils;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Map的操作工具类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/1 15:16
 * @since JDK1.8
 **/
public class Maputer {

    /**
     * 向map中存放一个值
     * 如果存在，将存在的值传入并返回一个要保存的值
     * 如果不存在，无参数，获取一个要保存的值
     */
    public static <K, V> void put(Map<K, V> map, K key, Function<V, V> ifExist, Supplier<V> ifNull){
        V value = map.get(key);
        if(value != null){
            //如果能获取到，参数传入并获取一个放入值
            map.put(key, ifExist.apply(value));
        }else{
            //如果没有，获取参数
            map.put(key, ifNull.get());
        }
    }

    /**
     * 向map中存放一个值
     * 如果存在，将存在的值传入并返回一个要保存的值
     * 如果不存在，无参数，获取一个要保存的值
     */
    public static <K, V> void peek(Map<K, V> map, K key, Consumer<V> ifExist, Supplier<V> ifNull){
        V value = map.get(key);
        if(value != null){
            //如果能获取到，操作存在的参数
            ifExist.accept(value);
        }else{
            //如果没有，获取参数
            map.put(key, ifNull.get());
        }
    }

}
