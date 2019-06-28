package com.forte.qqrobot.utils;

import java.util.Iterator;

/**
 * 空值迭代器
 **/
public class EmptyIterator<T> implements Iterator<T> {


    private static EmptyIterator emptyIterator = new EmptyIterator();

    /**
     * 由于是空值迭代器，不存在下一个值
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * 由于是空值迭代器，下一个值必定为null
     */
    @Override
    public T next() {
        return null;
    }

    /** 静态工厂方法 */
    public static <T> EmptyIterator<T> getInstance(){
        return emptyIterator;
    }

    /**
     * 构造私有化
     */
    private EmptyIterator(){}
}
