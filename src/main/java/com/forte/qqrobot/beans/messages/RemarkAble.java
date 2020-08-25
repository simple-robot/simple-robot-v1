/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     RemarkAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages;

/**
 * 可以获取备注信息。一般出现在好友信息或者群信息中
 *
 * 一般来讲，能拿到群号的地方，就能拿到备注。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface RemarkAble {

    /**
     * 获取备注信息，例如群备注，或者好友备注。
     * @return 备注信息
     */
    String getRemark();

}
