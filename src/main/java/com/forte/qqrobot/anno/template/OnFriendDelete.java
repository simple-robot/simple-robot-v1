package com.forte.qqrobot.anno.template;

import com.forte.qqrobot.anno.AnnotateMapping;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * （被）好友删除事件
 *
 * @author ForteScarlet <ForteScarlet@163.com>
 * @date 2020/8/1
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //接口、类、枚举、注解、方法
@Listen(MsgGetTypes.friendDelete)
public @interface OnFriendDelete {
    /**
     * 假如出现了多个监听器处理同一个消息，使用此参数对其进行排序，默认值为1
     * 2020/1/28  ver1.6.3  修改为100
     * @see Listen#sort()
     */
    @AnnotateMapping(type = Listen.class)
    int sort() default 100;

    /**
     * 相当于一个ID，此参数为空的时候会自动根据包、类、方法名、参数列表生成一个唯一ID
     * @see Listen#name()
     */
    @AnnotateMapping(type = Listen.class)
    String name() default "";
}
