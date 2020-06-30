/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ExFunction.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.function;

/**
 * this is a functionInterface like {@link java.util.function.Function} but there are 3 parameters
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface ExFunction<P1, P2, P3, R> {


    R apply(P1 p1, P2 p2, P3 p3);

}
