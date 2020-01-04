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
