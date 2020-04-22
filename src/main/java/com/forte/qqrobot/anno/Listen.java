package com.forte.qqrobot.anno;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标记用<br>
 * 添加此注解，标注为一个监听器类<br>
 * 标注在类上将会记录全部的方法<br>
 * 标注在方法上将会记录此方法<br>
 * <br>
 *     当监听函数存在返回值且未标记@ListenBody的时候，部分返回值存在特殊含义：<br>
 *     <ul>
 *         <li>如果返回值本身为ListenResult对象则其他参数均无效，以其本身为主。</li>
 *         <li>布尔类型代表函数是否执行成功。</li>
 *         <li>返回值为null的时候代表执行未成功。</li>
 *         <li>数值类型小于0代表执行未成功。</li>
 *     </ul>
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/26 10:29
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //接口、类、枚举、注解、方法
//监听类默认不使用单例类型
//会牺牲一部分性能
//2019/12/27 恢复为单例
@Beans(init = true)
@ByNameType(Listen.ByName.class)
public @interface Listen {

    /**
     * 监听器所监听的类型, 可以监听多个类型
     * 方法上的参数将会覆盖类上的参数
     */
    MsgGetTypes[] value();

    /**
     * 假如出现了多个监听器处理同一个消息，使用此参数对其进行排序，默认值为1
     * 2020/1/28  ver1.6.3  修改为100
     */
    int sort() default 100;

    /**
     * 相当于一个ID，此参数为空的时候会自动根据包、类、方法名、参数列表生成一个唯一ID
     */
    String name() default "";




    /**
     * 通过额外注册的监听类型进行监听器注册，最终会转化为{@link Listen}
     */
    @Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
    @Target({ElementType.TYPE, ElementType.METHOD}) //接口、类、枚举、注解、方法
    //监听类默认不使用单例类型
    @Beans
    @ByNameFrom(Listen.class)
    @interface ByName {

        /** 额外注册的或者额外提供的监听类型的名称。 */
        @ByNameField(MsgGetTypes.class)
        String[] value();

        int sort() default 1;

        String name() default "";

    }


}
