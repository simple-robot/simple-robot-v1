package com.forte.qqrobot.listener.invoker;

/**
 *
 * 是否被At了的检测
 * 函数接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface AtDetection {

    /**
     * 检测是否被at了
     */
    boolean test();

}
