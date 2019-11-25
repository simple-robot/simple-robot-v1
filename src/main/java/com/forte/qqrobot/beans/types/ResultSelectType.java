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
 *         一般来讲，返回值可以为null。
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
    FIRST(rs -> rs[0]),

    /**
     * 默认寻找第一个截断返回值.
     * 一般来讲，我们认为数组的最后一个即为截断返回值。
     */
    FIRST_BREAK(rs -> {
        // 获取最后一个
        ListenResult last = rs[rs.length - 1];
        if(last.isToBreak()){
            return last;
        }else{
            return null;
        }
    }),

    /**
     * 获取第一个插件截断的返回值
     */
    FIRST_BREAK_PLUGIN(rs -> {
        for (ListenResult r : rs) {
            if(r.isToBreakPlugin()){
                return r;
            }
        }
        return null;
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
