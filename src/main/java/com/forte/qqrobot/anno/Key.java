package com.forte.qqrobot.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解主要用于简化开发者的开发，使用在方法参数上
 * 通过对一个接口进行代理，将会根据返回值与参数构建一个Bean或者一个JSON字符串
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.PARAMETER)//使用在方法参数上
public @interface Key {

    /**
     * 唯一参数，指定此方法的参数对应的key值名称
     * 如果没有注解则默认尝试通过Parameter对象获取
     * 如果要通过Parameter对象获取，请在pom中添加-parameters参数
     */
     String value();
}
