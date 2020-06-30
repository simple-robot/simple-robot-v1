/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     HttpResp.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender;

/**
 * Http请求响应值
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface HttpResp {

    /**
     * 相应数据
     * @return 相应数据字符串
     */
    String getResponse();

    /**
     * 响应码
     * @return 响应码
     */
    int getCode();

    /**
     * 响应码说明
     * @return 响应码说明
     */
    String getReasonPhrase();

}
