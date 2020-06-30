/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ListenInterceptContext.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.listener.ListenContext;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.util.Map;

/**
 * 监听函数拦截器
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ListenInterceptContext extends BaseContext<ListenerMethod> {

    public final SenderSendList SENDER;
    public final SenderSetList SETTER;
    public final SenderGetList GETTER;

    public ListenInterceptContext(ListenerMethod value, ListenContext listenContext, Map<String, Object> globalContextMap,
                                  SenderSendList sender, SenderSetList setter, SenderGetList getter) {
        super(value, globalContextMap);
        this.listenContext = listenContext;
        this.SENDER = sender;
        this.SETTER = setter;
        this.GETTER = getter;
    }
    public ListenInterceptContext(ListenerMethod value, ListenContext listenContext, Map<String, Object> globalContextMap, Map<String, Object> contextMap,
                                  SenderSendList sender, SenderSetList setter, SenderGetList getter) {
        super(value, globalContextMap, contextMap);
        this.listenContext = listenContext;
        this.SENDER = sender;
        this.SETTER = setter;
        this.GETTER = getter;
    }

    /** listenContext */
    private final ListenContext listenContext;

    /** 执行参数列表 */
    private Object[] args;

    /** result。默认为null，当拦截当前监听函数的时候，此值作为返回值 */
    private Object result = null;

    /** 不可重新赋值 */
    @Override
    @Deprecated
    public void setValue(ListenerMethod newValue) { }


    public ListenContext getListenContext() {
        return listenContext;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object[] getArgs() {
        return args;
    }

    void setArgs(Object[] args) {
        this.args = args;
    }
}
