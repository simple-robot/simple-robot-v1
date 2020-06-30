/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     NickOrRemark.java
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
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface NickOrRemark extends NicknameAble, RemarkAble {

    /**
     * 尝试获取备注，如果没用备注则获取昵称
     * @return 备注，或者昵称
     */
    default String getRemarkOrNickname(){
        final String remark = getRemark();
        return remark == null ? getNickname() : remark;
    }
}
