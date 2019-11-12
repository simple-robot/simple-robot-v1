package com.forte.qqrobot.anno;

import com.forte.qqrobot.beans.types.BreakType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 监听截断，当将此注解标记于监听函数上的时候，除非返回值为ListenResult类型，否则将会必然截断接下来的监听函数。
 *
 * 参数为一个枚举类型，代表了接收一个Object类型的参数，返回是否截断。（Object类型即为方法执行的结果）
 * 此枚举提供byName注解
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
@ByNameType(ListenBreak.ByName.class)
public @interface ListenBreak {

    /**
     * 判断是否截断的方式类型。存在byName注解
     * 默认为必定截断
     */
    BreakType value() default BreakType.ALWAYS_BREAK;


    /**
     * 对应的byName注解
     */
    @Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
    @Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
    @ByNameFrom(ListenBreak.class)
    @interface ByName {

        @ByNameField(BreakType.class)
        String value() default "ALWAYS_BREAK";

    }

}
