/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Application.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot;


import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 用户实现的启动器的接口
 * 泛型定义:
 * - 配置类的具体类型
 * 定义启动类执行前后方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:16
 * @since JDK1.8
 **/
public interface Application<CONFIG extends BaseConfiguration> {

    /**
     * 启动之前的配置方法
     * @param configuration
     */
    void before(CONFIG configuration);

    /**
     * @param cqCodeUtil cq码工具类
     * @param sender 不再建议使用MsgSender。如果想要主动发送消息，请试着使用{@code BotRuntime().getRuntime().getBotManager()}
     */
    void after(CQCodeUtil cqCodeUtil, MsgSender sender);

    /**
     * 获取启动器的包路径。默认情况下即获取当前类的包路径。
     * 无特殊需求不要重写。
     * @return {@link Package}
     */
    default Package getPackage(){
        return this.getClass().getPackage();
    }

    /**
     * 获取Application启动器的Class，默认情况下即获取自己的Class。
     * 无特殊需求不要重写。
     * @return Class
     */
    default Class<?> getApplicationClass(){
        return this.getClass();
    }

    /**
     * 这个方法不需要重写了啦！
     */
    default String author(){
        return "@ForteScarlet";
    }

}
