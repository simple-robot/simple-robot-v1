/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupMemberReduce.java
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
import com.forte.qqrobot.beans.messages.types.ReduceType;

/**
 * 群成员减少事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface GroupMemberReduce extends EventGet, CodesAble {

    /** 获取类型 */
    ReduceType getType();

    /** 群号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 操作者的QQ号 */
    String getOperatorQQ();

    /** 被操作者的QQ号 */
    String getBeOperatedQQ();

    /** 使用的是被操作者的QQ */
    @Override
    default String getQQCode(){
        return getBeOperatedQQ();
    }


}
