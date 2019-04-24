package com.forte.qqrobot.beans.messages.result;

import java.util.List;

/**
 * 当返回值为列表形式的时候，使用此接口定义列表中每个元素的实现类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface InfoResultList<T extends ResultInner> extends InfoResult {

    /**
     * 获取列表
     */
    T[] getList();

    /**
     * 判断列表是否为空
     */
    default boolean isListEmpty(){
        return getList() == null || getList().length <= 0;
    }



}
