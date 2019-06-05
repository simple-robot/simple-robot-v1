package com.forte.qqrobot.depend.parameter;

import com.forte.qqrobot.depend.NameTypeEntry;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ParamNameGetter {

    /**
     * 获取参数名称列表
     */
    String[] getNames(Method method);

    /**
     * 获取名称参数对应列表
     */
    NameTypeEntry[] getNameTypeEntrys(Method method);

    /**
     * 获取参数的变量名称
     */
    String getParameterName(Parameter parameter);

    /**
     * 获取名称参数
     */
    NameTypeEntry getNameTypeEntry(Parameter parameter);

}
