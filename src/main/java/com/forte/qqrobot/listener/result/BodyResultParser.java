/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BodyResultParser.java
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
 * 对携带ListenBody的函数进行转化的转化器。
 * 直接将结果作为result-data
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BodyResultParser implements ListenResultParser {
    // 使用单例，没有多例的必要

    private static final BodyResultParser INSTANCE = new BodyResultParser();
    public static BodyResultParser getInstance(){
        return INSTANCE;
    }
    private BodyResultParser(){ }

    /**
     * 不论执行结果为什么，只要不存在异常，则均认为执行成功，并将结果赋予result-data
     * @param t 监听函数的执行结果
     * @param sort          排序参数
     * @param isBreak       是否跳过后续函数
     * @param isBreakPlugin 是否跳过后续插件
     * @param e 如果存在异常，此为异常
     * @return
     */
    @Override
    public ListenResult parse(Object t, int sort, boolean isBreak, boolean isBreakPlugin, Exception e) {
        if(e != null){
            return new ListenResultImpl<>(sort, t, false, isBreak, isBreakPlugin, e);
        }else{
            return new ListenResultImpl<>(sort, t, true, isBreak, isBreakPlugin, null);
        }
    }
}
