package com.forte.qqrobot.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 阻塞注解
 * 标注在类上的时候，类下的全部函数全都会被标记上此注解
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:22
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //接口、类、枚举、注解、方法
public @interface Block {

    /** 阻塞的名称，相同名称的阻塞方法共享阻塞状态，阻塞名称可以有多个，没有名称的函数将会以 类名#函数名(参数列表) 的规则来命名 */
    String[] value() default {};


}
