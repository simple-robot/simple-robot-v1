/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ResultSelectType.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.listener.result.ListenResult;

import java.util.function.Function;

/**
 *
 * 监听函数的返回值筛选类型枚举。
 * 提供多个监听函数返回值, 对其进行筛选。<br>
 * 参数中：
 * <ul>
 *     <li>
 *         一般来讲，如果存在截断返回值，则最后一个参数为截断返回值且只会存在一个。
 *     </li>
 *     <li>
 *         一般来讲，参数可能长度为0，但是不会为null。
 *     </li>
 *     <li>
 *         一般来讲，参数已经根据sort参数进行排序。
 *     </li>
 *     <li>
 *         一般来讲，不可以返回null，除非没有触发任何监听，否则至少需要一个响应值。
 *     </li>
 * </ul>
 *
 * 过滤的时候请使用{@link #filter(ListenResult[])} 方法。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum ResultSelectType {

    /**
     * 默认取第一个返回值
     */
    FIRST(rs -> rs.length <= 0 ? null : rs[0]),

    /**
     * 默认寻找第一个截断返回值.
     * 一般来讲，我们认为数组的最后一个即为截断返回值。
     *
     */
    FIRST_BREAK(rs -> rs.length <= 0 ? null : rs[rs.length - 1]),

    /**
     * 获取第一个插件截断的返回值
     */
    FIRST_BREAK_PLUGIN(rs -> {
        if(rs.length <= 0){
            return null;
        }
        for (ListenResult r : rs) {
            if(r.isToBreakPlugin()){
                return r;
            }
        }
        // 如果没有，返回最后一个
        return rs[rs.length - 1];
    })

    ;
    /**
     *
     */
    private final ListenResultFilter filter;

    /**
     * 构造，提供一个过滤函数
     * @param filter 过滤函数
     */
    ResultSelectType(ListenResultFilter filter){
        this.filter = filter;
    }

    /**
     * 过滤结果。如果数组为空则返回null。
     * 参数不可为null。
     * @param listenResults result数组
     */
    public <T> ListenResult<T> filter(ListenResult<T>[] listenResults){
        if(listenResults == null){
            throw new NullPointerException();
        }else if(listenResults.length <= 0){
            return null;
        }else{
            return filter.apply(listenResults);
        }
    }


    /**
     * 监听函数返回值过滤器，本质是function函数
     */
    @FunctionalInterface
    static interface ListenResultFilter extends  Function<ListenResult[], ListenResult>{ }
}
