package com.forte.qqrobot.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 用于标记一个监听函数的API信息
 * 仅能使用在method上, 且只有被注册的ListenMethod生效
 * 就算此注解不存在，也会生成相应信息，但是所有信息均为默认值
 *
 * @author ForteScarlet
 */
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
public @interface ListenerAPI {

    /**
     * API的名称简述，例如：私聊复读，默认为{类名}.{方法名}
     */
    String value() default "";

    /**
     * 监听API的详细描述，默认为空
     */
    String description() default "";




}
