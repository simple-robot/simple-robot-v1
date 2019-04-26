package com.forte.qqrobot.sender;

/**
 * 为送信器提供阻断相关的方法
 * 送信器应当存在一个为其服务的监听函数对象
 * 此接口提供一种方法以为送信器提供判断是否存在监听函数的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Blockable {
    /**
     * 是否存在监听器函数
     */
    boolean hasMethod();

}
