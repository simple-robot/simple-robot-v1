/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     NormalParamNameGetter.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend.parameter;

import com.forte.qqrobot.depend.NameTypeEntry;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * 参数获取器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Deprecated
public class NormalParamNameGetter implements ParamNameGetter {
    @Override
    public String[] getNames(Method method) {
        return Arrays.stream(method.getParameters()).map(this::getParameterName).toArray(String[]::new);
    }

    @Override
    public NameTypeEntry[] getNameTypeEntrys(Method method){
        return Arrays.stream(method.getParameters()).map(this::getNameTypeEntry).toArray(NameTypeEntry[]::new);
    }

    @Override
    public String getParameterName(Parameter parameter) {
        return parameter.getName();
    }


    @Override
    public NameTypeEntry getNameTypeEntry(Parameter parameter) {
        return NameTypeEntry.getInstanceLower(parameter.getName(), parameter.getType());
    }

    NormalParamNameGetter() {
    }
}
