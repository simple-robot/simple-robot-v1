package com.forte.qqrobot.beans.messages;

import com.forte.qqrobot.utils.FieldUtils;

import java.io.Serializable;

/**
 * 所有的消息接口的根接口b<r>
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 10:28
 * @since JDK1.8
 **/
public interface RootBean extends Serializable {

    /**
     * 尝试通过反射直接获取参数，通过获取get方法并执行来获取
     * 如果获取不到则返回null
     * 支持多层级字段的获取了
     * 例如：result.name
     */
    default Object getOtherParam(String key){
        try{
            return FieldUtils.objectGetter(this, key);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 尝试通过反射直接获取参数，通过获取get方法并执行来获取
     * 如果获取不到则返回null
     * 支持多层级字段的获取了
     * 例如：result.name
     */
    default <T> T getOtherParam(String key, Class<T> type){
        try{
            return (T) FieldUtils.objectGetter(this, key);
        }catch (Exception e){
            return null;
        }
    }



}
