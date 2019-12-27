package com.forte.qqrobot.listener.intercept;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

/**
 *
 * 监听函数拦截
 * 监听函数的拦截有很多步骤，例如初始化、获取参数等等。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ListenIntercepts {

    /**
     * 监听函数准备进行执行的时候。
     * 不存在参数。
     * 位于刚刚进入执行方法，获取监听函数的参数列表之前。
     * @param id 监听函数的id
     * @param sort 排序索引
     * @param types 监听类型数组。此数组需要进行cpoy，不应能够被改变
     * @return 是否放行
     */
    boolean init(String id, int sort, MsgGetTypes[] types);

    /**
     * 监听函数获取完参数后，获取监听函数对象实例之前
     * @param args 获取到的方法参数列表
     * @return 是否放行
     */
    boolean paramInit(Object[] args);



}
