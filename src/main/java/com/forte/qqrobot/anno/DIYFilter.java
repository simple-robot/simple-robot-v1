package com.forte.qqrobot.anno;

import com.forte.qqrobot.anno.depend.Beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义过滤规则的
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE}) //接口、类、枚举、注解、方法
@Beans
public @interface DIYFilter {

    /**
     * 自定义filter的名称。如果不填或者使用的是@Beans注解，则使用类名小写作为名称。
     * @return
     */
    String value() default "";

}
