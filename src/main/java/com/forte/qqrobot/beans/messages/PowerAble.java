/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     PowerAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages;

import com.forte.qqrobot.beans.messages.types.PowerType;

/**
 * 用于获取群权限相关数据
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface PowerAble {

    /**
     * 获取此人在群里的权限
     * @return 权限，例如群员、管理员等
     */
    PowerType getPowerType();

    /**
     * 重新定义此人的权限
     * @param powerType 权限
     */
    void setPowerType(PowerType powerType);

}
