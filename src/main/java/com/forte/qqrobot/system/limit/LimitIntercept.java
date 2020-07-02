/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     LimitIntercept.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.system.limit;

import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.constant.PriorityConstant;
import com.forte.qqrobot.listener.ListenIntercept;
import com.forte.qqrobot.listener.invoker.ListenInterceptContext;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.utils.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * limit intercept
 * 即限流拦截器。
 *
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class LimitIntercept implements ListenIntercept {

    /**
     * limit map
     */
    private Map<Integer, ListenLimit> limitMap = new ConcurrentHashMap<>();

    /**
     * 缓存记录不需要拦截的函数。
     * 记录函数的hashcode
     */
    private final Set<Integer> skipSet = new HashSet<>();

    /**
     * 优先级为正3
     */
    @Override
    public int sort() {
        return PriorityConstant.THIRD;
    }

    /**
     * 拦截执行函数
     *
     * @param context 上下文对象
     * @return 是否放行
     */
    @Override
    public boolean intercept(ListenInterceptContext context) {
        final ListenerMethod listenerMethod = context.getValue();
        final Method method = listenerMethod.getMethod();

        if(skipSet.contains(Objects.hashCode(method))){
            return true;
        }

        final Limit limit = AnnotationUtils.getAnnotation(method, Limit.class);
        if(limit == null){
            synchronized (skipSet){
                skipSet.add(Objects.hashCode(method));
            }
            return true;
        }else{
            final MsgGet msgGet = context.getMsgGet();
            final long time = limit.timeUnit().toMillis(limit.value());
            List<Object> hashList = new ArrayList<Object>(6){{
                add(limit);
                add(method);
            }};
            if(limit.group() && msgGet instanceof GroupCodeAble){
                hashList.add(((GroupCodeAble) msgGet).getGroupCode());
            }
            if(limit.group() && msgGet instanceof QQCodeAble){
                hashList.add(((QQCodeAble) msgGet).getCode());
            }
            if(limit.bot()){
                hashList.add(msgGet.getThisCode());
            }

            // hash
            final int hash = Objects.hash(hashList);

            final ListenLimit listenLimit = limitMap.computeIfAbsent(hash, h -> new ListenLimit(time));
            return listenLimit.expired();
        }
    }
}
