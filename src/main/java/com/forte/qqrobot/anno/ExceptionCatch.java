package com.forte.qqrobot.anno;

import com.forte.qqrobot.anno.depend.Beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在监听器中进行异常处理, 标注在实现了ExceptionHandle接口的类上
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE}) //接口、类、枚举、注解、方法
@Beans
public @interface ExceptionCatch {

    /** 捕获的异常类型，默认为{@link Exception}类型 */
    Class<? extends Exception>[] value() default java.lang.Exception.class;

}
