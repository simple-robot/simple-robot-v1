/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     DependUtil.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend.util;

import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.utils.FieldUtils;
import com.forte.qqrobot.utils.MethodUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 依赖注入的工具类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DependUtil {

    /**
     * 判断一个类中的依赖是否可以进行注入
     * 函数形式
     */
    public static <T> Function<T, Boolean> canInj(Class<T> type, Field field, Depend dependAnnotation) throws NoSuchMethodException {
        //通过字段判断是否可以为bean注入
        if(dependAnnotation.nonNull()){
            //如果需要判断是否为空
            //判断是否需要通过getter来判断
            if(dependAnnotation.byGetter()){
                Method getter;
                //判断是否指定了getter方法
                if(dependAnnotation.getterName().trim().length() == 0){
                    //没有指定，直接获取getter，不涉及多层级，直接获取
                    getter = FieldUtils.getFieldGetter(type, field);
                    if(getter == null){
                        throw new DependResourceException("noFieldGetter", type, field);
                    }
                }else{
                    //指定了getter方法，获取此方法
                    getter = MethodUtil.getMethod(type, dependAnnotation.getterName());
                }
                //如果为null则可以注入
                return bean -> {
                    try {
                        return getter.invoke(bean) == null;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new DependResourceException("cannotGetValue", e , getter, type, field);
                    }
                };
            }else{

                return bean -> {
                    //不需要通过getter判断，直接取值
                    field.setAccessible(true);
                    //如果为null则可以注入
                    boolean isNull;
                    try {
                        isNull = field.get(bean) == null;
                    } catch (IllegalAccessException e) {
                        throw new DependResourceException("fieldValueGetFailed", e, type, field);
                    }
                    field.setAccessible(false);
                    return isNull;
                };
            }
        }else{
            return bean -> true;
        }
    }


    /**
     * 对字段进行注入
     * 函数形式
     * @param type  需要注入的对象的类型
     * @param field 字段
     * @param dependAnnotation  depend注解
     * @param getter 获取字段依赖的函数
     * @return  是否注入成功
     */
    public static <T> Consumer<T> doInj(Class<T> type, Field field, Depend dependAnnotation, Supplier<com.forte.qqrobot.depend.Depend> getter) throws NoSuchMethodException {
        //是否使用set注入
        if(dependAnnotation.bySetter()){
            //通过setter注入
            Method setter;
            if(dependAnnotation.setterName().trim().length() == 0){
                //没有指定setter的方法名，则获取setter
                setter = FieldUtils.getFieldSetter(type, field);
                if(setter == null){
                    throw new DependResourceException("noFieldSetter", type, field);
                }
            }else{
                //否则，按照给定的方法名获取
                setter = MethodUtil.getMethod(type, dependAnnotation.setterName());
            }

            //赋值函数
            return bean -> {
                try {
                    com.forte.qqrobot.depend.Depend<T> beanDepend = getter.get();
                    //先获取空值实例
                    T emptyInstance = beanDepend == null ? null : beanDepend.getEmptyInstance();
                    setter.invoke(bean, emptyInstance);
                    //为实例注入参数
                    if(beanDepend != null){
                        beanDepend.inject(emptyInstance);
                    }

                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new DependResourceException("CannotSetValue", e, setter, type, field);
                }
            };
        }else{
            return b -> {
                //直接使用字段反射注入
                field.setAccessible(true);
                try {
                    //先获取空值实例
                    com.forte.qqrobot.depend.Depend<T> beanDepend = getter.get();
                    T emptyInstance = beanDepend == null ? null : beanDepend.getEmptyInstance();
                    field.set(b, emptyInstance);
                    //为实例注入参数
                    if(beanDepend != null){
                        beanDepend.inject(emptyInstance);
                    }

                } catch (IllegalAccessException e) {
                    throw new DependResourceException("fieldValueSetFailed", e, type, field);
                }
                field.setAccessible(false);
            };
        }
    }

    /**
     * 对字段进行注入
     * 函数形式
     * 仅使用额外参数
     * @param type  需要注入的对象的类型
     * @param field 字段
     * @param dependAnnotation  depend注解
     * @param getter 获取字段依赖的函数
     * @return  是否注入成功
     */
    public static <T> BiConsumer<T, DependGetter> doInj(Class<T> type, Field field, Depend dependAnnotation, Function<DependGetter, com.forte.qqrobot.depend.Depend> getter) throws NoSuchMethodException {
        //是否使用set注入
        if(dependAnnotation.bySetter()){
            //通过setter注入
            Method setter;
            if(dependAnnotation.setterName().trim().length() == 0){
                //没有指定setter的方法名，则获取setter
                setter = FieldUtils.getFieldSetter(type, field);
                if(setter == null){
                    throw new DependResourceException("noFieldSetter", type, field);
                }
            }else{
                //否则，按照给定的方法名获取
                setter = MethodUtil.getMethod(type, dependAnnotation.setterName());
            }

            //赋值函数
            return (bean, add) -> {
                try {
                    com.forte.qqrobot.depend.Depend<T> beanDepend = getter.apply(add);
                    //先获取空值实例
                    T emptyInstance = beanDepend == null ? null : beanDepend.getEmptyInstance();
                    setter.invoke(bean, emptyInstance);
                    //为实例注入参数
                    if(beanDepend != null){
                        //注入时提供额外参数
                        beanDepend.injectAdditional(emptyInstance, add);
                    }

                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new DependResourceException("CannotSetValue", e, setter, type, field);
                }
            };
        }else{
            return (bean, add) -> {
                //直接使用字段反射注入
                field.setAccessible(true);
                try {
                    //先获取空值实例
                    com.forte.qqrobot.depend.Depend<T> beanDepend = getter.apply(add);
                    T emptyInstance = beanDepend == null ? null : beanDepend.getEmptyInstance();
                    field.set(bean, emptyInstance);
                    //为实例注入参数
                    if(beanDepend != null){
                        //注入时提供额外参数
                        beanDepend.injectAdditional(emptyInstance, add);
                    }

                } catch (IllegalAccessException e) {
                    throw new DependResourceException("fieldValueSetFailed", e, type, field);
                }
                field.setAccessible(false);
            };
        }
    }

}
