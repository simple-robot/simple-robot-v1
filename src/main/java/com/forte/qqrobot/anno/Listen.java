package com.forte.qqrobot.anno;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标记用
 * 添加此注解，标注为一个监听器类
 * 标注在类上将会记录全部的方法
 * 标注在方法上将会记录此方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/26 10:29
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //接口、类、枚举、注解、方法
//监听类默认不使用单例类型
@Beans(single = false)
public @interface Listen {

    /**
     * 监听器所监听的类型, 可以监听多个类型
     * 方法上的参数将会覆盖类上的参数
     */
    MsgGetTypes[] value();

    /**
     * 假如出现了多个监听器处理同一个消息，使用此参数对其进行排序，默认值为1
     */
    int sort() default 1;

}
