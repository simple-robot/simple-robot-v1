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
public interface ListenResult<T> extends Comparable<ListenResult<T>> {

    /**
     * 函数是否执行成功
     * @return 是否成功
     */
    Boolean isSuccess();

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

}
