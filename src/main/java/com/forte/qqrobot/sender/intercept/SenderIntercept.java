/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     SenderIntercept.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.intercept.Interceptor;
import com.forte.qqrobot.sender.senderlist.SenderList;

/**
 *
 * 送信器拦截器，用于拦截所有送信器的执行流程
 * 应当存在三个子接口对应三种拦截：Send、Set、Get
 * <br>
 * ※注意事项：当返回值为false的时候，代表拦截操作，不执行原方法以及后续拦截，此时会抛出一个异常：
 * <br>
 *     <p>
 *         由于java接口机制原因，三个接口无法同时实现在同一个类上。此问题我将会在日后修复
 *     </p>
 * {@link SenderInterceptException}
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderIntercept<S extends SenderList, C extends SenderContext<S>> extends Interceptor<S, C> {
}
