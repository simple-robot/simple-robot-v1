package com.forte.qqrobot.anno.template;

import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //接口、类、枚举、注解、方法
@Listen(MsgGetTypes.discussMsg)
public @interface OnDiscuss {
}
