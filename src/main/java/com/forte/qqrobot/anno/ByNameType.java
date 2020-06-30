/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ByNameType.java
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
 * 此注解指定一个注解所对应的byName注解
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.ANNOTATION_TYPE}) //注解
public @interface ByNameType {

    /**
     * 此注解对应的ByName注解
     * 或许ByName注解存在的不止一个，可能存在很多扩展用的，但是官方指定的只能存在一个
     * */
    Class<? extends Annotation> value();

}
