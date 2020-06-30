/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     RunParameterImpl.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.system;

import java.util.Arrays;

/**
 * 启动程序的时候的参数 impl
 * @see RunParameter
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class RunParameterImpl implements RunParameter {

    private final String name;
    private final String base;
    private final String[] parameters;
    private final RunParameterType type;


    public RunParameterImpl(String base, String name, String[] parameters, RunParameterType type) {
        this.base = base;
        this.name = name;
        this.parameters = parameters;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getParameters() {
        return Arrays.copyOf(parameters, parameters.length);
    }

    @Override
    public String getParameter(int index) {
        return parameters[index];
    }

    @Override
    public int parameterLength() {
        return parameters.length;
    }

    @Override
    public RunParameterType getType() {
        return type;
    }

    @Override
    public String getBase() {
        return base;
    }

    @Override
    public String toString() {
        return base;
    }
}
