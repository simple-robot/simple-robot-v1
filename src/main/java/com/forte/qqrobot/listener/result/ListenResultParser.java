package com.forte.qqrobot.listener.result;

/**
 *
 * 监听函数解析器。根据监听函数的执行结果获取一个ListenResult对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ListenResultParser {

    /**
     * 结果转化器
     * @param t 监听函数的执行结果
     * @param sort          排序参数
     * @param isBreak       是否跳过后续函数
     * @param isBreakPlugin 是否跳过后续插件
     * @param e 如果存在异常，此为异常
     * @return 监听函数实现类
     */
    ListenResult parse(Object t, int sort, boolean isBreak, boolean isBreakPlugin, Throwable e);

}
