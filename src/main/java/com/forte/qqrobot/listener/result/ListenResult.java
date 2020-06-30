/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ListenResult.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.listener.result;

/**
 *
 * 此接口定义一个监听函数执行完成后的结果报告
 *
 * 存在一个固定类型的返回值，此返回值应当由组件进行控制
 * 且返回值应当允许被排序
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ListenResult<T> extends Comparable<ListenResult> {

    /**
     * 函数是否执行成功
     * @return 是否成功
     */
    Boolean isSuccess();

    /**
     * 是否阻断接下来的监听函数
     * @return 是否阻断
     */
    Boolean isToBreak();

    /**
     * 是否截断酷Q中低优先级的其他插件。
     * 此返回值需要插件功能性的支持，否则将无效。
     */
    Boolean isToBreakPlugin();

    /**
     * 获取执行结果响应
     * @return 获取执行结果
     */
    T result();

    /**
     * 获取返回值的排序值
     * @return 排序值
     */
    int sortValue();

    /**
     * 如果出现了异常，则此为异常
     * @return 出现的异常（如果有的话
     */
    Exception getError();

    /**
     * 默认的排序方式为根据sortValue值进行排序
     * @param o 对比对象
     * @return  排序结果
     */
    @Override
    default int compareTo(ListenResult o) {
        return Integer.compare(sortValue(), o.sortValue());
    }
}
