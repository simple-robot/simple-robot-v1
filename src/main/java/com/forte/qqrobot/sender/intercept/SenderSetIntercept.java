/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     SenderSetIntercept.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderSetIntercept extends SenderIntercept<SenderSetList, SetContext> {
    /**
     * 拦截执行函数
     * @param context 上下文对象
     * @return 是否放行
     */
    @Override
    boolean intercept(SetContext context);
}
