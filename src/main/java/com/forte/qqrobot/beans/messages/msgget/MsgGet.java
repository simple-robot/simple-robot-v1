package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.OriginalAble;
import com.forte.qqrobot.beans.messages.RootBean;

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
public interface MsgGet extends OriginalAble, RootBean {

    /** 获取ID, 一般用于消息类型判断 */
    String getId();



    /**
     * 此消息获取的时候，代表的是哪个账号获取到的消息。
     * @return 接收到此消息的账号。
     */
    String getThisCode();

    /**
     * 允许重新定义Code以实现在存在多个机器人的时候切换处理。
     * @param code code
     */
    void setThisCode(String code);

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
