/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BreakType.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.types;

import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * 截断类型枚举
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum BreakType {

    /**
     * 不论结果，永远截断
     */
    ALWAYS_BREAK(o -> true),

    /**
     * 不论结果，永远不截断（等于没写
     */
    ALWAYS_NO(o -> false),

    /**
     * 当执行结果不为空的时候才会进行截断
     */
    WHEN_NOT_NULL(Objects::nonNull),

    /**
     * 当执行结果为null的时候才会进行截断
     * 但是一般不会用这个的（我个人感觉
     */
    WHEN_NULL(Objects::isNull),

    ;

    /**
     * non null
     *
     * 根据一个返回值的结果判断是否需要进行截断。
     * 永远不会接收到ListenResult类型的结果。
     */
    private final Predicate<Object> resultTest;

    BreakType(Predicate<Object> resultTest){
        this.resultTest = resultTest;
    }

    public Predicate<Object> getResultTest(){
        return resultTest;
    }

    public boolean test(Object o){
        return resultTest.test(o);
    }

}
