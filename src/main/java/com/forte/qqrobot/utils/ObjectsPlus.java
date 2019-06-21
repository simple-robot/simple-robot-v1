package com.forte.qqrobot.utils;

import java.util.Objects;

/**
 * 提供几个判断是否存在null值的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ObjectsPlus {


    public static void allNonNull(Iterable list, String message){
        list.forEach(i -> Objects.requireNonNull(i, message));
    }

    public static void allNonNull(Iterable list){
        list.forEach(Objects::requireNonNull);
    }

    public static void allNonNull(String message, Object... arr){
        for (Object o : Objects.requireNonNull(arr, message)) {
            Objects.requireNonNull(o, message);
        }
    }

    public static void allNonNull(Object... arr){
        for (Object o : Objects.requireNonNull(arr)) {
            Objects.requireNonNull(o);
        }
    }

}
