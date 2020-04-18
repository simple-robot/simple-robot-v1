package com.forte.lang;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 迭代器工具类
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class Iterators {

    /**
     * 将{@link java.util.Enumeration}转化为迭代器对象
     * @return Iterator
     */
    public static <T> Iterator<T> iter(Enumeration<T> enumeration){
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return enumeration.nextElement();
            }
        };
    }



}
