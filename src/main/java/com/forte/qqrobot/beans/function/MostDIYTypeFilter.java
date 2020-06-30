/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MostDIYTypeFilter.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.function;

import com.forte.qqrobot.anno.data.Filter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.Filterable;
import com.forte.qqrobot.listener.ListenContext;
import com.forte.qqrobot.listener.invoker.AtDetection;

/**
 * 为枚举{@link com.forte.qqrobot.beans.types.MostDIYType}服务, 作为函数接口使用。
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@FunctionalInterface
public interface MostDIYTypeFilter {

    boolean test(Filter filter, MsgGet msg, AtDetection at, ListenContext context, Filterable[] filters);

}
