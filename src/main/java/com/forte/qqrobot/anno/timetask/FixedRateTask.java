package com.forte.qqrobot.anno.timetask;

import com.forte.qqrobot.beans.types.TimeType;
import sun.misc.Timeable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用固定时间间隔进行记时的注解
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.TYPE) //类
public @interface FixedRateTask {
    /** 标记的定时任务类的id，如果为空则默认为类名 */
    String id() default "";

    /** 时间间隔的内容：毫秒、秒、分等等，类型由{@link #timeType()}决定，默认为秒 */
    long value();

    /** 时间类型，默认为秒 */
    TimeType timeType();

    /** 开始后的重复次数，默认为-1，-1为无限重复 */
    int RepeatCount() default -1;


}
