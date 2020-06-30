/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     OriginalAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用于获取原生数据字符串的接口
 * 一般来讲，所有可获取到的消息都应实现此接口
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface OriginalAble {

    /** 获取原本的数据 originalData */
    @JSONField(serialize = false)
    String getOriginalData();



}
