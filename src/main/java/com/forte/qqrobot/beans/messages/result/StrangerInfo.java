/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     StrangerInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.NickOrRemark;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * 陌生人信息
 * TODO 似乎提供COOKIE可以获取更多信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface StrangerInfo extends InfoResult, QQCodeAble, NickOrRemark {

    /** QQ号 */
    String getQQ();
    /** 性别 */
    SexType getSex();
    /** 年龄 */
    Integer getAge();
    /** 头像地址 */
    default String headUrl(){
        return getQQHeadUrl();
    }
    /** 等级 */
    Integer getLevel();

    /**
     * TODO 未来将会删除此方法
     * @see #getNickname()
     * @see #getRemark()
     * */
    @Deprecated
    default String getName(){
        return getNickname();
    }

    @Override
    default String getQQCode() {
        return getQQ();
    }






}
