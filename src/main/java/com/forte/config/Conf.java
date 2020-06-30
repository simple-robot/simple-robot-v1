/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Conf.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.config;

import java.lang.annotation.*;

/**
 *
 * 字段对应的配置名称。
 * 最终的对应结果为类上的+'.'+字段上的
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.FIELD, ElementType.TYPE}) // 字段、类
@Documented
@Inherited  //可继承的
public @interface Conf {

    /**
     * 配置名称，一般用xxx.xxx的这种格式。如果配置中没有内容，则会视为无效。
     * 尽量不要在类与字段上同时存在多个，会极大的降低效率。
     * */
    String[] value();

    /**
     * 是否只有当字段为null的时候才注入
     * */
    boolean onlyNull() default false;

    /** 是否 *优先* 使用setter注入 */
    boolean setter() default true;

    /** 指定setter方法，参数类型必定为字段类型或者子类, 类上无效 */
    String setterName() default "";

    /** 是否 *优先* 使用getter判断是否为null */
    boolean getter() default true;

    /** 指定getter方法，无参数。 类上无效*/
    String getterName() default "";

    /** setter方法的参数类型是什么。如果是Object类型则默认认为是与字段相同。 */
    Class<?> setterParameterType() default Object.class;

    /** the comment of this conf */
    String comment() default "";

}
