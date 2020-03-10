package com.forte.qqrobot.anno;

import com.forte.qqrobot.anno.depend.Beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将标注的、实现了{@link com.forte.qqrobot.sender.HttpClientAble}的类注册到{@link com.forte.qqrobot.sender.HttpClientHelper}中
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE}) //接口、类、枚举、注解、方法
@Beans
public @interface HttpTemplate {

    /**
     * 注册到{@link com.forte.qqrobot.sender.HttpClientHelper}的时候使用的注册名称
     */
    String value();

    /**
     * 是否将其作为默认使用的http模板，默认为false
     */
    boolean beDefault() default false;

}
