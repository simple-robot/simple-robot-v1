package com.forte.qqrobot.listener.intercept;

import com.forte.qqrobot.anno.depend.Beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拦截器注解，目前暂时仅用于标记@Beans
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.TYPE) //类
@Beans
public @interface Intercept {
}
