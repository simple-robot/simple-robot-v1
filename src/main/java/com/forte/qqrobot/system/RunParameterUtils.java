/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     RunParameterUtils.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link RunParameter}的一些方法
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 * @see RunParameter
 */
public class RunParameterUtils {

    private static final String PROP_TYPE_HEAD = "--";
    private static final String COMMAND_TYPE_HEAD = "-C";

    /**
     * 解析启动参数并转化为一个RunParameter
     * 启动参数的格式类似于Spring的，--开头的是properties值
     *
     * @return a {@link RunParameter} null able
     * @throws IllegalArgumentException 当无法解析的时候抛出此异常
     */
    public static RunParameter parseToParameter(String value) {
        Objects.requireNonNull(value);
        if (value.length() == 0) {
            throw new IllegalArgumentException("length = 0");
        }
        // prop类型的
        if (value.startsWith(PROP_TYPE_HEAD)) {
            final String[] propInfo = value.substring(2).split("=", 2);
            if (propInfo.length <= 1) {
                return null;
            }
            final String paramName = propInfo[0];
            final String[] paramValue = propInfo[1].length() == 0 ? new String[0] : propInfo[1].split(",");
            return new RunParameterImpl(value, paramName, paramValue, RunParameterType.PROPERTIES);
        } else if (value.startsWith(COMMAND_TYPE_HEAD)) {
            // 解析指令
            // 指令还不知道用在哪儿, 总之先用:分割吧
            final String[] propInfo = value.substring(2).split(":", 2);
            final String commandName = propInfo[0];
            final String[] commandValue;
            if (propInfo.length <= 1 || propInfo[1].length() == 0) {
                commandValue = new String[0];
            } else {
                commandValue = propInfo[1].split(",");
            }
            return new RunParameterImpl(value, commandName, commandValue, RunParameterType.COMMAND);
        } else {
            return null;
        }
    }

    /**
     * 启动参数转化
     * @param params 启动参数
     */
    public static RunParameter[] parseToParameters(String... params) {
        if (params.length == 0) {
            return new RunParameter[0];
        } else {
            List<RunParameter> tempList = new ArrayList<>();
            for (String p : params) {
                final RunParameter parameter = parseToParameter(p);
                if (parameter != null) {
                    tempList.add(parameter);
                }
            }
            return tempList.toArray(new RunParameter[0]);
        }
    }


}
