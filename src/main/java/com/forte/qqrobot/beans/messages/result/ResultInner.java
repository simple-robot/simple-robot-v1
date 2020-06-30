/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ResultInner.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.OriginalAble;
import com.forte.qqrobot.beans.messages.RootBean;

/**
 * 返回值有很多情况会有内部接口，内部接口使用此接口定义
 * v1.0.4版本之后（不包括）所有此类型消息均实现接口{@link com.forte.qqrobot.beans.messages.OriginalAble}
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ResultInner extends RootBean, OriginalAble {
}
