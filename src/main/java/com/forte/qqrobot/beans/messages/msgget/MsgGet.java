package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.OriginalAble;
import com.forte.qqrobot.beans.messages.RootBean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 消息接收类型接口，定义一个获取接收到的消息的方法. <br>
 * 2019/11/25 update <br>
 * 有些插件支持在某些接收消息的时候返回一些特定的消息来达到快捷响应的目的，考虑支持此方式。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 10:30
 * @since JDK1.8
 **/
public interface MsgGet extends OriginalAble, RootBean {

    /** 获取ID, 一般用于消息类型判断 */
    String getId();

    /**
     * 消息接收类型定义接口获取接收到的消息类型<br>
     * 如果没有消息推荐使用空字符串来代替
     */
    String getMsg();

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
