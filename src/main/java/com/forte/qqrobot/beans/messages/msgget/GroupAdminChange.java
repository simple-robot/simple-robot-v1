/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupAdminChange.java
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
import com.forte.qqrobot.beans.messages.types.GroupAdminChangeType;

/**
 * 群管理员变动事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 11:35
 * @since JDK1.8
 **/
public interface GroupAdminChange extends EventGet, CodesAble {

    /** 来自的群 */
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

    /** 获取管理员变动类型 */
    GroupAdminChangeType getType();


}
