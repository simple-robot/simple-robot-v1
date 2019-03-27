package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.listener.SocketListener;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 监听器阻塞器
 * 每种种类的监听器同一时间仅只能有一个阻塞中的监听器，且阻塞的监听器只能由自己解除阻塞（或定时解除）
 * 阻塞后，同类型的监听器不论是普通监听器还是备用监听器都将失去作用，无法接收到消息，直到解除阻塞
 * 同时过滤器注解也将失去作用，并且提供为阻塞的时候专用的阻塞过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:45
 * @since JDK1.8
 **/
public class ListenerPlug {

    /**
     *  全局阻塞
     *
     **/
    private static final AtomicReference<Method> GLOBAL_BLOCK = new AtomicReference<>(null);








}
