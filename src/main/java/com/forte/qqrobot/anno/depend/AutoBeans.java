/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AutoBeans.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.anno.depend;

import com.forte.qqrobot.anno.AnnotateMapping;
import com.forte.qqrobot.constant.PriorityConstant;

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

    /** 依赖对象的名称，如果没有则以类名取代 */
    @AnnotateMapping(type = Beans.class)
    String value() default "";

    /** 是否为单例，默认为单例 */
    @AnnotateMapping(type = Beans.class)
    boolean single() default true;

    /**
     * 是否将类中全部字段标记为Depend，默认为false
     * */
    @AnnotateMapping(type = Beans.class)
    boolean allDepend() default false;

    /** 当全部标记为@Depend的时候，此参数为所有字段标记的@Depend注解对象，默认为无参注解 */
    @AnnotateMapping(type = Beans.class)
    Depend depend() default @Depend;


    /** 根据参数类型列表来指定构造函数，默认为无参构造。仅标注在类上的时候有效 */
    @AnnotateMapping(type = Beans.class)
    Class[] constructor() default {};

    /**
     * 优先级。当在获取某个依赖的时候，假如在通过类型获取的时候存在多个值，会获取优先级更高级别的依赖并摒弃其他依赖。
     * 使用jdk自带的排序规则，即升序排序。默认为优先级最低。
     * @since core-1.12
     */
    @AnnotateMapping(type = Beans.class)
    int priority() default PriorityConstant.FIRST_LAST;
}
