package com.forte.qqrobot.depend.parameter;

import com.forte.qqrobot.depend.NameTypeEntry;

import java.lang.reflect.Parameter;

/**
 * 当检测到使用者没有开启参数编译的时候，使用此类
 * 此类中获取的所有参数名称全部为null
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class NullParamNameGetter extends NormalParamNameGetter {

    @Override
    public String getParameterName(Parameter parameter) {
        return null;
    }


    @Override
    public NameTypeEntry getNameTypeEntry(Parameter parameter) {
        return NameTypeEntry.getInstanceLower(null, parameter.getType());
    }

    NullParamNameGetter() {



    }
}
