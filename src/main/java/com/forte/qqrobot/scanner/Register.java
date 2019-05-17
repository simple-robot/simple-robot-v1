package com.forte.qqrobot.scanner;

import com.forte.qqrobot.sender.MsgSender;

import java.util.function.Supplier;

/**
 * 注册器接口，定义一系列对各种功能进行注册的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Register {

    /** 注册监听函数 */
    void registerListener();

    /** 注册定时任务 */
    void registerTimeTask(MsgSender sender);
    /** 注册定时任务 */
    void registerTimeTask(Supplier<MsgSender> senderSupplier);
}
