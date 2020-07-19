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

import cn.hutool.core.lang.UUID;
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
    private Map<String, ListenLimit> limitMap = new ConcurrentHashMap<>();

    /**
     * 优先级为+3
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

        final Limit limit = AnnotationUtils.getAnnotation(method, Limit.class);
        if(limit == null){
            return true;
        }else{
            final MsgGet msgGet = context.getMsgGet();
            final long time = limit.timeUnit().toMillis(limit.value());
            StringBuilder uuidStringBuilder = new StringBuilder();
//            List<String> hashList = new ArrayList<String>(6){{
//                add(limit.toString());
//                add(method.toString());
//            }};
            uuidStringBuilder.append(limit.toString()).append(method.toString());
            if(limit.group() && msgGet instanceof GroupCodeAble){
                uuidStringBuilder.append(((GroupCodeAble) msgGet).getGroupCode());
//                hashList.add(((GroupCodeAble) msgGet).getGroupCode());
            }
            if(limit.group() && msgGet instanceof QQCodeAble){
                uuidStringBuilder.append(((QQCodeAble) msgGet).getCode());
//                hashList.add(((QQCodeAble) msgGet).getCode());
            }
            if(limit.bot()){
                uuidStringBuilder.append(msgGet.getThisCode());
//                hashList.add(msgGet.getThisCode());
            }

            // hash
//            final int hash = Objects.hash(hashList);
            // uuid
            final String uuid = UUID.fromString(uuidStringBuilder.toString()).toString(true);

            final ListenLimit listenLimit = limitMap.computeIfAbsent(uuid, h -> new ListenLimit(time));
            return listenLimit.expired();
        }
    }
}
