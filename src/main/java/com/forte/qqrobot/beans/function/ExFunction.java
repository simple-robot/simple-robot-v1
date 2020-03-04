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
