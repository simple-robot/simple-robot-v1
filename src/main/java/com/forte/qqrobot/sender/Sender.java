package com.forte.qqrobot.sender;

/**
 * 送信器接口，实现以下接口：
 * <ul>
 *     <li>{@link Senderable} 提供判断送信接口是否存在的方法</li>
 *     <li>{@link Methodable} 提供判断监听函数是否存在的方法</li>
 *     <li>{@link Blockable}  提供将存在的监听函数添加进阻断状态的方法</li>
 * </ul>
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
interface Sender extends Senderable, Methodable, Blockable{
}
