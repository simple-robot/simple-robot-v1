/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     CoreConfiguration.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.function.PathAssembler;
import com.forte.qqrobot.beans.function.VerifyFunction;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotManagerImpl;
import com.forte.qqrobot.system.limit.LimitIntercept;

/**
 * 核心的配置注入类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Beans
public class CoreConfiguration {

    /**
     * 注册验证函数
     */
    @Depend
    private VerifyFunction verifyFunction;

    /**
     * 路径拼接器
     */
    @Depend
    private PathAssembler pathAssembler;



    @Beans
    public BotManager defaultBotManager(){
        return new BotManagerImpl(pathAssembler, verifyFunction);
    }


    @Beans
    public LimitIntercept limitIntercept(){
        return new LimitIntercept();
    }

}
