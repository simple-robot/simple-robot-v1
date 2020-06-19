package com.forte.qqrobot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用注解启动器
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) //接口、类、枚举、注解、方法
public @interface SimpleRobotApplication {

    /**
     *  <pre> 启动器所处的class位置。如果指定了Application接口的类型本身，则说明直接使用当前标注了这个注解的类来生成代理。
     */
    Class<? extends Application> application() default Application.class;

    /**
     * <pre> 配置文件所在的resources资源路径下的读取路径，默认为"simple-robot-conf.properties".
     * <pre> 如果值为空字符的话在不会读取文件。
     * @return 资源路径
     */
    String resources() default "simple-robot-conf.properties";

//    /**
//     * <pre> 此处为额外的配置信息。使用注解来覆盖、代替文件形式的配置。
//     * <pre> 格式与properties相同，使用key-value的格式。
//     * @return {@link SimpleRobotConfiguration}
//     */
//    SimpleRobotConfiguration config() default @SimpleRobotConfiguration;


}
