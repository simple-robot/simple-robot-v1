package com.forte.qqrobot.beans.messages.result;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * 当返回值为列表形式的时候，使用此接口定义列表中每个元素的实现类
 *  2019-5-31
 * 增加Iterable接口并增加默认方法实现，使实现了此接口的实现类可以进行迭代、遍历
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface InfoResultList<T extends ResultInner> extends InfoResult, Iterable<T> {

    /**
     * 获取列表, 极度不建议返回为null
     * non-null
     */
    T[] getList();

    /**
     * 判断列表是否为空
     */
    default boolean isEmpty(){
        return getList().length <= 0;
    }

    /**
     * 将结果数组转化为迭代器
     */
    @Override
    default Iterator<T> iterator(){
        return Arrays.asList(getList()).iterator();
    }

    /**
     * 增加接口，使其可以转化为stream对象
     */
    default Stream<T> stream(){
        return Arrays.stream(getList());
    }



}
