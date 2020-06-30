/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MostTypeFactory.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.factory;

import com.forte.qqrobot.beans.function.MostTypeFilter;
import com.forte.qqrobot.beans.types.MostType;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;

import java.util.function.IntFunction;

/**
 *
 * 为创建 {@link com.forte.qqrobot.beans.types.MostType} 枚举类型的工厂
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MostTypeFactory extends BaseFactory<MostType> {

    /** enum type */
    private static final Class<MostType> ENUM_TYPE = MostType.class;

    /** constructor types array */
    private static final Class<?>[] CONSTRUCTOR_TYPES = {MostTypeFilter.class};

    /** to array function */
    private static final IntFunction<MostType[]> TO_ARRAY_FUNCTION = MostType[]::new;

    private static final MostTypeFactory SINGLE = new MostTypeFactory();

    private MostTypeFactory(){}

    public static MostTypeFactory getInstance() {
        return SINGLE;
    }

//    /**
//     * 注册一个新的mostType类型枚举
//     * 会对异常进行捕获
//     * @param name              name
//     * @param mostTypeFilter    枚举所需构造
//     * @return 新实例
//     */
//    public MostType register(String name, MostTypeFilter mostTypeFilter){
//        try {
//            return registerOrThrow(name, mostTypeFilter);
//        } catch (EnumInstantiationRequireException | EnumInstantiationException e) {
//            QQLog.error("枚举类型[ com.forte.qqrobot.beans.types.MostType ]实例[ "+ name +" ]构建失败", e);
//            return null;
//        }
//    }

    /**
     * 注册一个新的mostType类型枚举
     * @param name              name
     * @param mostTypeFilter    枚举所需构造
     * @return 新实例
     * @throws EnumInstantiationRequireException 参数权限认证失败
     * @throws EnumInstantiationException        实例构建失败
     */
    public MostType register(String name, MostTypeFilter mostTypeFilter) throws EnumInstantiationRequireException, EnumInstantiationException {
            return super.registerEnum(name, mostTypeFilter);
    }

    public static MostType registerType(String name, MostTypeFilter mostTypeFilter) throws EnumInstantiationRequireException, EnumInstantiationException {
        return getInstance().register(name, mostTypeFilter);
    }


    @Override
    protected Class<MostType> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<MostType[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }



    /** 判断参数是否合规 */
    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数只有一个
        if(!(params[0] instanceof MostTypeFilter)){
            throw new IllegalArgumentException("参数不是["+ MostTypeFilter.class +"]类型！");
        }
    }
}
