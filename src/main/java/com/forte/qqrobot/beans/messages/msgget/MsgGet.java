/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MsgGet.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.OriginalAble;
import com.forte.qqrobot.beans.messages.RootBean;
import com.forte.qqrobot.beans.messages.ThisCodeAble;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

/**
 * 消息接收类型接口，定义一个获取接收到的消息的方法. <br>
 * 2019/11/25 update <br>
 * 有些插件支持在某些接收消息的时候返回一些特定的消息来达到快捷响应的目的，考虑支持此方式。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 10:30
 * @version 1.0
 **/
public interface MsgGet extends OriginalAble, ThisCodeAble, RootBean {

    /** 获取ID, 一般用于消息类型判断 */
    String getId();



    /**
     * 一般来讲，监听到的消息大部分都会有个“消息内容”。定义此方法获取消息内容。
     * 如果不存在，则为null。（旧版本推荐为空字符串，现在不了。我变卦了）
     */
    String getMsg();

    /**
     * 重新设置消息
     * @param newMsg msg
     * @since 1.7.x
     */
    void setMsg(String newMsg);

    /**
     * 根据当前的msg来更新msg信息。
     * oldMsg -> newMsg
      * @param updateMsg msg更新函数
     */
    default void setMsg(Function<String, String> updateMsg){
        setMsg(updateMsg.apply(getMsg()));
    }

    /** 获取消息的字体 */
    String getFont();


    /** 获取到的时间, 代表某一时间的秒值。一般情况下是秒值。如果类型不对请自行转化 */
    Long getTime();

    /**
     * 将时间作为秒值之间戳转化为LocalDateTime
     */
    default LocalDateTime getTimeToLocalDateTime(){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(getTime()), ZoneId.systemDefault());
    }


}
