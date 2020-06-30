/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Filterable.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.listener;

import com.forte.qqrobot.anno.data.Filter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.invoker.AtDetection;

/**
 * 使用户自己创建自定义的规则判断
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface Filterable {

    /**
     * 根据自定义规则对消息进行过滤
     * @param filter  filter注解对象, 如果不存在@Filter，则会为null
     * @param msgget  接收到的消息
     * @param at      判断当前消息是否被at的函数
     * @param context 监听上下文
     * @return 是否通过
     */
    boolean filter(Filter filter, MsgGet msgget, AtDetection at, ListenContext context);

}
