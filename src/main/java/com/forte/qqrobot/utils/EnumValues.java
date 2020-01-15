package com.forte.qqrobot.utils;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.log.QQLog;
import com.forte.utils.reflect.EnumUtils;

import java.lang.reflect.Method;
import java.util.function.IntFunction;

/**
 * 用于获取Enum枚举的values对象
 * @author ForteScarlet
 */
public class EnumValues {

    public static <T extends Enum<T>> T[] values(Class<T> t, IntFunction<T[]> initArray) {
        // 先尝试使用EnumUtils获取
        try {
            return EnumUtils.values(t, initArray);
        }catch (Throwable e){
            try {
                // 出现异常，使用正常手段values
                Method values = t.getMethod("values");
                return (T[]) values.invoke(null);
            }catch (Exception normalValueException){
                QQLog.error("枚举类型[ "+ t +" ]获取values异常。1.", e);
                QQLog.error("2.", normalValueException);
                return initArray.apply(0);
            }
        }

    }

}
