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
import com.forte.qqrobot.beans.messages.RemarkAble;
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

    /**
     * 邀请人。可能为null
     * @return 邀请人code
     */
    String invitorCode();

    /**
     * 邀请者的昵称。视组件而定，不一定能够获取到。
     * @return 邀请者群名片
     */
    String invitorNickname();

    /**
     * 邀请者的群名片。视组件而定，不一定能够获取到。
     * @return 邀请者群名片
     */
    String invitorRemark();

    /**
     * 被邀请者code， 如果code==thisCode，说明被邀请的是bot
     * @return 被邀请人code
     */
    String inviteeCode();

    /**
     * 被邀请者的昵称。视组件而定，不一定能够获取到。
     * @return 被邀请者的昵称
     */
    String inviteeNickname();


    @Override
    default String getNickname(){
        return inviteeNickname();
    }

    /**
     * 此处无法通过{@link RemarkAble#getRemark()}获取群名片。
     * @return null
     * @see #invitorRemark()
     */
    @Deprecated
    @Override
    default String getRemark(){
        return null;
    }

    /**
     * 此处获取的是被邀请者的昵称
     * @return 被邀请者的昵称
     */
    @Override
    default String getRemarkOrNickname() {
        return getNickname();
    }

    /** 获取QQ号, 默认情况下就是获取被邀请者的code */
    default String getQQ() {
        return inviteeCode();
    };

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
