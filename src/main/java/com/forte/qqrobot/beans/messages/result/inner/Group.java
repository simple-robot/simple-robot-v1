/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Group.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 群列表的群信息
 */
public interface Group extends ResultInner, GroupCodeAble {
    /** 群名 */
    String getName();
    /** 群号 */
    String getCode();
    /** 群头像地址, 默认情况下直接使用p.qlogo接口 */
    default String getHeadUrl(){
        String groupCode = getCode();
        return "http://p.qlogo.cn/gh/"+ groupCode +"/"+ groupCode +"/640";
    }

    @Override
    default String getGroupCode(){
        return getCode();
    }
}
