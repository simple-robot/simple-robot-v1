/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AllBeans.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.anno.depend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解仅可以使用在启动器上
 * 此注解标注的包路径将会
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //类、方法
public @interface AllBeans {

    /** 全部加入的包路径, 默认为全包路径 */
    String[] value() default {};

    /** 扫描的时候默认使用的Beans注解对象，默认尝试自动注入所有字段 */
    Beans beans() default @Beans(allDepend = true);
}
