package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

/**
 * 定义一个监听函数所存在的各种消息与参数
 * @author ForteScarlet
 *
 */
public interface ListenerInfo {

    /** 获取简述
     * @return simple description
     * */
    String getName();

    /**
     * 获取描述
     * @return description
     */
    String getDescription();

    /**
     * 获取所监听的消息类型
     * @return listen types
     */
    MsgGetTypes[] listenTypes();

    /**
     * 获取指定索引下的监听消息类型
     * @param index 索引
     * @return listen type
     */
    MsgGetTypes listenTypes(int index);

    /** 获取所监听的消息类型的数量 */
    int listenTypesLength();

    /**
     * 排序索引
     * @return sort number
     */
    int sort();




}
