package com.forte.qqrobot.beans.messages.result;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface InfoResultList<T extends ResultInner> extends InfoResult {

    T[] getList();
}
