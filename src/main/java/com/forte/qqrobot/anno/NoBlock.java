package com.forte.qqrobot.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解定义：被标记的函数将不会受到阻塞机制的影响；
 * ※ 被此注解标注的函数将不能进入阻断状态，也不会被阻断机制所影响。
 * 假如尝试使其进入阻断模式，将会抛出一个异常。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/3 18:14
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.METHOD) //接口、类、枚举、注解、方法
public @interface NoBlock {

}
