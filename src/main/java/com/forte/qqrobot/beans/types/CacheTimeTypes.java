/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     CacheTimeTypes.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.types;

/**
 * 当使用缓存getter的时候，此枚举定义时间类型
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum CacheTimeTypes {

    /*
        这几个看名字应该就能才出来了吧
     */
    NANOS,
    SECONDS,
    MINUTES,
    HOURS,
    DAYS,
    MONTH,
    YEAR,
    /** 某个指定的日子 */
    DATE

}
