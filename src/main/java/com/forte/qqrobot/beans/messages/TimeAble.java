/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     TimeAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 此接口定义对象可以获取到时间值，并对时间值进行一些转化
 * TODO 尚未投入使用
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface TimeAble {

    /** 获取时间， 一般为秒类型的时间戳 */
    Long getTime();


    /**
     * 将时间视为秒值时间戳来获取{@link LocalDateTime}对象
     */
    default LocalDateTime getTimeBySec(){
        Long time = getTime();
        return time == null ? null : LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
    }

    /**
     * 将时间视为毫秒值时间戳来获取{@link LocalDateTime}对象
     */
    default LocalDateTime getTimeByMilli(){
        Long time = getTime();
        return time == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    /**
     * 将时间时为秒值时间戳来获取{@link Instant}对象
     */
    default Instant getInstantBySec(){
        Long time = getTime();
        return time == null ? null : Instant.ofEpochSecond(time);
    }


    /**
     * 将时间视为毫秒值时间戳来获取{@link Instant}对象
     * @return
     */
    default Instant getInstantByMilli(){
        Long time = getTime();
        return time == null ? null : Instant.ofEpochMilli(time);
    }



}
