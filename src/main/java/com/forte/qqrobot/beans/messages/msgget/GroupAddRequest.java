/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupAddRequest.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.FlagAble;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;

/**
 * 群添加请求事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupAddRequest extends EventGet, CodesAble, FlagAble {

    /** 获取群号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 获取QQ号 */
    String getQQ();

    @Override
    default String getQQCode(){
        return getQQ();
    }

    /** 获取消息 */
    @Override
    String getMsg();

    /** 加群类型 */
    GroupAddRequestType getRequestType();

}
