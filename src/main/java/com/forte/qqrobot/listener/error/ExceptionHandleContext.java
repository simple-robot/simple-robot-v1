package com.forte.qqrobot.listener.error;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.intercept.Context;
import com.forte.qqrobot.sender.MsgSender;

/**
 * 异常处理上下文，提供一些捕获到异常时候的参数。其不属于常见的上下文对象。
 * 提供一个实现类{@link ExceptionHandleContextImpl}
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface ExceptionHandleContext<T extends Throwable> extends Context<T> {

    String getId();
    MsgGet getMsgGet();
    int getSort();
    MsgSender getMsgSender();
    T getException();

}
