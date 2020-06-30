/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Interceptor.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.intercept;

/**
 *
 * 拦截器跟接口，定义拦截器接口规范。
 * 每一个拦截器有一个对应的上下文对象，并且返回值为boolean值。
 * 但是我感觉这个规范不怎么受用
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Interceptor<T, C extends Context<T>> extends Comparable<Interceptor<T, C>> {

    /**
     * 排序方法，默认为1。
     * 无特殊排序需求则不用重写此方法。
     * @return 优先级，值越小优先级越高。目前理论上支持负值。
     */
    default int sort(){
        return 1;
    }

    /**
     * 拦截执行函数
     * @param context 上下文对象
     * @return 是否放行
     */
    boolean intercept(C context);

    /**
     * 实现的排序方法。
     * 无特殊需求请不要重写此方法。
     * @return 排序值
     */
    @Override
    default int compareTo(Interceptor other){
        return Integer.compare(this.sort(), other.sort());
    }

}
