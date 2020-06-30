/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ByNameFrom.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.anno;

import java.lang.annotation.*;

/**
 *
 * 此注解指定一个ByName注解所对应的父类注解
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.ANNOTATION_TYPE}) //接口、类、枚举、注解、方法
public @interface ByNameFrom {

    /** byName所唯一对应的父类注解 */
    Class<? extends Annotation> value();

}
