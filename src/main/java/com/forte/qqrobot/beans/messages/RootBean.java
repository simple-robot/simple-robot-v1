package com.forte.qqrobot.beans.messages;

import com.forte.qqrobot.utils.FieldUtils;

/**
 * 所有的消息接口的根接口b<r>
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 10:28
 * @since JDK1.8
 **/
public interface RootBean {

    /** 如果还会出现其他的参数，请自行实现获取方式<br>
     *  如果其他参数不多，你可以试着在方法中使用if_else进行分别；<br>
     *  如果参数较多，我建议你使用Map等键值对来储存参数并获取
     * */
//    Object getOtherParam(String key);

    /**
     * 尝试通过反射直接获取参数，通过获取get方法并执行来获取
     * 如果获取不到则返回null
     * 支持多层级字段的获取了
     * 例如：result.name
     */
    default Object getOtherParam(String key){
        try{
            return FieldUtils.objectGetter(this, key);
//            return this.getClass().getDeclaredMethod("get" + key).invoke(this);
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
//            return (T) this.getClass().getDeclaredMethod("get" + key).invoke(this);
        }catch (Exception e){
            return null;
        }
    }



}
