package com.forte.qqrobot.anno;

import com.forte.qqrobot.beans.types.BreakType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件截断注解，当标注了此注解，会根据参数来判断此函数是否会对低优先级的插件进行截断。
 * 此截断功能需要有插件的功能支持。
 * 使用方式类似{@link ListenBreak}
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
@ByNameType(ListenBreakPlugin.ByName.class)
public @interface ListenBreakPlugin {

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
    @ByNameFrom(ListenBreakPlugin.class)
    @interface ByName {

        @ByNameField(BreakType.class)
        String value() default "ALWAYS_BREAK";

    }
}
