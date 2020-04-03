package com.forte.qqrobot.sender.intercept;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 送信器接口列表API， 用于标记一个API
 * @author ForteScarlet
 */
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
public @interface SenderListAPI {
    /**
     * 一般此API可以被拦截，则代表其属于一个父级的API，此处来定义此API的名称
     * 此name对应了lang国际化语言中的api.{API_name}.name
     * 默认值为""的时候，默认为其方法名
     */
    String value() default "";

    /**
     * 是否属于父类接口。
     * 一般来讲，标注为default的封装接口不是父类API，那些标注了InterceptValue注解的方法为父类API
     * @return
     */
    boolean root() default false;
}
