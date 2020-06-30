/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupMember.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.NickOrRemark;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.result.ResultInner;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * 群成员信息
 */
public interface GroupMember extends ResultInner, QQCodeAble, NickOrRemark {
    /** 群号 */
    String getGroup();
    /** QQ号 */
    String getQQ();
    @Override
    default String getQQCode() {
        return getQQ();
    }
    /**
     * @see #getRemarkOrNickname()
     */
    @Deprecated
    default String getNickOrName(){
       return getRemarkOrNickname();
    }

    /** 获取性别 */
    SexType getSex();
    /** 所在城市 */
    String getCity();

    /** 加群时间 */
    Long getJoinTime();
    /** 最后发言时间 */
    Long getLastTime();
    /** 权限类型 */
    PowerType getPower();

    /** 获取专属头衔 */
    String getExTitle();
    /** 等级对应名称 */
    String getLevelName();
    /** 是否为不良用户 */
    Boolean isBlack();
    /** 是否允许修改群名片 */
    Boolean isAllowChangeNick();

    /** 头衔到期时间 */
    Long getExTitleTime();

    /** 头像 */
    String getHeadUrl();


}
