package com.forte.qqrobot.anno;

/**
 * 在监听器中进行异常处理
 * 此注解标注在监听器类中，进行异常处理
 * 如果非静态方法，则与出现异常的监听类使用的是同一个类容器
 *
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public @interface Catch {

    /** 捕获的异常类型，默认为全类型 */
    Class<? extends Throwable> value() default java.lang.Exception.class;

    /** 当出现多个可处理方法的时候，根据这个来进行优先级配置，数越小优先级越高 */
    int level() default 0;


}
