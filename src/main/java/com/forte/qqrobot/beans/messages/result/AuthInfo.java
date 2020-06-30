/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AuthInfo.java
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
 * 权限信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface AuthInfo extends InfoResult {

    /**
     * 获取一个编码
     */
    String getCode();

    /**
     * 获取cookies信息
     */
    String getCookies();

    /**
     * 获取CsrfToken
     */
    String getCsrfToken();



}
