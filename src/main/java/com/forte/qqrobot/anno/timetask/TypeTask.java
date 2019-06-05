package com.forte.qqrobot.anno.timetask;

import com.forte.qqrobot.beans.types.TimeTaskTemplate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用泛型来使用一些预设的定时任务类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE}) //类
public @interface TypeTask {
    /** 定时任务模板 */
    TimeTaskTemplate value();

    String id() default "";
}
