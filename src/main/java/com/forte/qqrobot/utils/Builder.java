package com.forte.qqrobot.utils;

/**
 * 规定一切构建器都必将拥有一个方法：build() 来构建一个参数
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Builder<T> {

    T build();

}
