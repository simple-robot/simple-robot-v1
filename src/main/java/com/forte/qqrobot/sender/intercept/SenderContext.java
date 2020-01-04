package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.sender.senderlist.SenderList;

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

    public SenderContext(T value, Object... params) {
        super(value);
        this.params = params;
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
}
