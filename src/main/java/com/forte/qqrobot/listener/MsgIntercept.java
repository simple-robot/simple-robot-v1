/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MsgIntercept.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.intercept.Interceptor;

/**
 *
 * <p>消息拦截接口</p>
 * <p>消息拦截器在被内部使用的时候<b>仅会被实例化一次</b>，但是同样需要标记@Beans</p>
 * <p>当消息拦截器返回值为false的时候，会终止后续拦截器的执行与监听消息的监听。</p>
 * <p>排序使用正序排序，值越小优先级越高。</p>
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MsgIntercept extends Interceptor<MsgGet, MsgGetContext>{

}
