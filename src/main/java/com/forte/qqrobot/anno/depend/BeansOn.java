package com.forte.qqrobot.anno.depend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * TODO
 *
 *  beans on, 实现在注入依赖的时候进行一些条件判断
 * 不能直接代替为{@link Beans}, 需要与其一同使用
 *
 *
 * @author ForteScarlet <ForteScarlet@163.com>
 * @date 2020/7/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface BeansOn {

    /**
     * 判断类型
     */
    BeansOn.Type onType();

    /**
     * 检测对象。
     * 例如当哪些类型存在才会注入，
     * 或者当哪些类型不存在才会注入之类的。
     */
    Class<?>[] detect();


    /**
     * 判断类型。
     */
    enum Type {
        /** 当xx存在才会生效 */
        EXIST,
        /** 当xx不存在才会生效 */
        MISS
    }

}
