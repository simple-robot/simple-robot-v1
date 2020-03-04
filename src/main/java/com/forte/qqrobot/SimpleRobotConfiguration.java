package com.forte.qqrobot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) //接口、类、枚举、注解、方法
public @interface SimpleRobotConfiguration {

    /**
     * 额外的配置信息。此信息会覆盖文件读取到的信息。
     * @return 额外的配置信息
     */
    ConfigurationProperty[] value() default {};

}
