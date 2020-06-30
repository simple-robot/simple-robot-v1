/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AnonInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

/**
 * 匿名信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface AnonInfo extends InfoResult {

    /**
     * 获取ID
     */
    String getId();

    /**
     * 获取匿名名称
     */
    String getAnonName();

    /**
     * Token, 原数据似乎是数据流形式
     */
    String token();


}
