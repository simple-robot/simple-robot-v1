/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BreakTypeFactory.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.factory;

import com.forte.qqrobot.beans.types.BreakType;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;

import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 *
 * 截断类型工厂
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BreakTypeFactory extends BaseFactory<BreakType> {
    private static final Class<BreakType> ENUM_TYPE = BreakType.class;
    private static final Class<?>[] CONSTRUCTOR_TYPES = {Predicate.class};
    private static final IntFunction<BreakType[]> TO_ARRAY_FUNCTION = BreakType[]::new;
    private static final BreakTypeFactory SINGLE = new BreakTypeFactory();
    private BreakTypeFactory(){ }

    /** 获取实例对象 */
    public static BreakTypeFactory getInstance(){
        return SINGLE;
    }

//    /**
//     * 普通的注冊，在注冊的時候如果出現异常会使用log打印日志
//     * @param name 枚举名称
//     * @param test 枚举构造所需参数 :
//     *              根据一个返回值的结果判断是否需要进行截断。
//     *              永远不会接收到ListenResult类型的结果。
//     * @return  新的枚举对象
//     */
//    public BreakType register(String name, Predicate<Object> test){
//        try {
//            return registerOrThrow(name, test);
//        } catch (EnumInstantiationRequireException | EnumInstantiationException e) {
//            QQLog.error("枚举类型[ com.forte.qqrobot.beans.types.BreakType ]实例[ "+ name +" ]构建失败", e);
//            return null;
//        }
//    }

    /**
     * 注册枚举类型，如果出现异常则会抛出
     * @param name  枚举名称
     * @param test  枚举构造所需参数 :
     *  根据一个返回值的结果判断是否需要进行截断。
     *  永远不会接收到ListenResult类型的结果。
     * @return  新的枚举对象
     * @throws EnumInstantiationRequireException    参数权限验证失败
     * @throws EnumInstantiationException           枚举实例构建失败
     */
    public BreakType register(String name, Predicate<Object> test) throws EnumInstantiationRequireException, EnumInstantiationException {
        return super.registerEnum(name, test);
    }


    public static BreakType registerType(String name, Predicate<Object> test) throws EnumInstantiationRequireException, EnumInstantiationException {
        return getInstance().register(name, test);
    }


    @Override
    protected Class<BreakType> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<BreakType[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }

    /**
     * 参数判断
     * @param name          名称
     * @param params        参数列表
     */
    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数只有一个
        Predicate<Object> p = (Predicate<Object>) params[0];
        Objects.requireNonNull(p);
    }
}
