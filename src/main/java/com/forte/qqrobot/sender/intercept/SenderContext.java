/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     SenderContext.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.sender.senderlist.SenderList;

import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * 送信拦截器所使用的父类上下文对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class SenderContext<T extends SenderList> extends BaseContext<T> {


    /**
     * api使用的参数列表。
     * 可以进行修改, 并且修改后会影响最终的执行。
     */
    private Object[] params;

    /**
     * 被代理的方法
     */
    private Method method;

    public SenderContext(T value, Map<String, Object> globalContextMap, Method method, Object... params) {
        super(value, globalContextMap);
        this.params = params;
        this.method = method;
    }

    public T getSender(){
        return getValue();
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
