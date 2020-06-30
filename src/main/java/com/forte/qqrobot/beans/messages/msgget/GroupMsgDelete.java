/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupMsgDelete.java
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

/**
 * 群消息撤回事件
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface GroupMsgDelete extends EventGet, CodesAble, FlagAble {

    /**
     * 撤回的消息
     * @return 撤回的消息
     */
    @Override
    String getMsg();

    /**
     * 获取此消息的唯一id
     * @return 消息id
     */
    @Override
    String getId();

    /**
     * 获取标识，等同于获取消息id
     * @return 消息id
     */
    @Override
    default String getFlag(){
        return getId();
    }

    /** 操作者的QQ号，即执行撤回操作的人的QQ号 */
    String getOperatorQQ();


    /** 被操作者的QQ号, 即被撤回消息的人的QQ号 */
    String getBeOperatedQQ();

    /**
     * 获取QQ号信息。
     * 假如一个消息封装中存在多个QQ号信息，例如同时存在处理者与被处理者，一般情况下我们认为其返回值为被处理者。
     */
    @Override
    default String getQQCode(){
        return getBeOperatedQQ();
    }

}
