package com.forte.qqrobot;

/**
 * 对于一些框架内部的类，它们都需要存在一个执行期间的Runtime对象, 并且使其可以被获取到。
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface Runtimeable {
    /**
     * 获取当前的Runtime对象
     * @return 当前的Runtime对象
     */
    BotRuntime getRuntime();
}
