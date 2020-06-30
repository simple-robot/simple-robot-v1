/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     VerifyFunction.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.function;

import com.forte.qqrobot.bot.BotInfo;

import java.util.function.Function;

/**
 * Bot验证函数
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface VerifyFunction extends Function<BotInfo, BotInfo> {
}
