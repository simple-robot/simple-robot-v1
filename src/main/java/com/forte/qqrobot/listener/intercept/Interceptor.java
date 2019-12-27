package com.forte.qqrobot.listener.intercept;

/**
 *
 * 拦截器跟接口，定义拦截器接口规范。
 * 每一个拦截器有一个对应的上下文对象，并且返回值为boolean值。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Interceptor<T, C extends BaseContext<T>> {

    /**
     * 拦截执行函数
     * @param context 上下文对象
     * @return 是否放行
     */
    boolean intercept(C context);

}
