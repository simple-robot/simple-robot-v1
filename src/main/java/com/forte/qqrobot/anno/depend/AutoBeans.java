package com.forte.qqrobot.anno.depend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一般用于自动装配的模组上，此注解与{@link Beans}的区别在于此注解为默认会初始化一次的。
 * 但是其他参数不可选，默认即为单例、
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //类、方法
@Beans(init = true)
public @interface AutoBeans {
}
