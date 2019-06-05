package com.forte.qqrobot.depend;

/**
 * 依赖注入器，获取依赖实例后，通过此接口的实例对象来对对象内部的依赖内容进行注入
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface DependInjector {

    /**
     * 传入一个对象实例，对此对象实例中的所需依赖进行注入
     */
    <T> void inject(T t);


}
