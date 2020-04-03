package com.forte.qqrobot.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 为那些ByName的注解提供对应关系
 * 标注在对应ByName的注解上
 * 方法必须是字符串或者字符串数组类型
 * 其对应的主注解类型与其对应的必须一致
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
public @interface ByNameField {

    /** 某个枚举的值, 标注在byName注解的方法上 */
    Class<? extends Enum<?>> value();

}
