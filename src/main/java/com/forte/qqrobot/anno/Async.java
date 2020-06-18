package com.forte.qqrobot.anno;

/**
 * 标定当前监听函数为异步函数。
 * 异步函数的函数返回值、break注解等解析均会失效，默认认为其执行成功。
 * TODO
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public @interface Async {
    /**
     * 异步函数执行成功的默认值。
     * 默认认为其执行成功。
     */
    boolean success() default true;

}
