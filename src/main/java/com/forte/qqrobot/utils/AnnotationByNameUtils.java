package com.forte.qqrobot.utils;

import com.forte.qqrobot.anno.ByNameField;
import com.forte.qqrobot.anno.ByNameFrom;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.utils.reflect.ProxyUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 工具类，提供对应的方法将部分存在byName类型注解的值转化为对应的普通值
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AnnotationByNameUtils {




    /**
     * {@link Listen}类型的byName对应值
     * 需要保证这个byName中，对枚举类型的对应方法都写好了{@link ByNameField} 注解标识
     * 否则转化无法正常生效
     *
     * @param byName byName
     */
    public static Listen byName(Listen.ByName byName) {
//        Map<String, BiFunction<Method, Object[], Object>> stringBiFunctionMap = byNameField(byName);
//
//        return ProxyUtils.annotationProxy(Listen.class,
//                proxyByMethodMap(Listen.ByName.class, withObject(
//                        byName,
//                        stringBiFunctionMap
//                ))
//        );
        return byName(byName, Listen.class);
    }

    /**
     * {@link Filter}类型的byName对应值
     * 需要保证这个byName中，对枚举类型的对应方法都写好了{@link ByNameField} 注解标识
     * 否则转化无法正常生效
     *
     * @param byName byName
     */
    public static Filter byName(Filter.ByName byName) {
//        Map<String, BiFunction<Method, Object[], Object>> stringBiFunctionMap = byNameField(byName);
//
//        return ProxyUtils.annotationProxy(Filter.class,
//                proxyByMethodMap(Filter.ByName.class, withObject(
//                        byName,
//                        stringBiFunctionMap
//                ))
//        );
        return byName(byName, Filter.class);
    }


    /**
     * 通过一个ByName注解获取其对应的父类注解
     * @param byName    byName注解，需要存在{@link com.forte.qqrobot.anno.ByNameFrom} 注解
     * @param <FROM>    {@link com.forte.qqrobot.anno.ByNameFrom} 所对应的父类注解
     * @param <BY_NAME> ByName注解
     */
    public static <FROM extends Annotation, BY_NAME extends Annotation> FROM byName(BY_NAME byName, Class<FROM> fromType){
        if(byName == null || fromType == null){
            return null;
        }
        // 获取ByName中的ByNameFrom注解
        Class<? extends Annotation> byNameType = byName.annotationType();
        ByNameFrom from = byNameType.getAnnotation(ByNameFrom.class);
        if(from == null){
            throw new IllegalArgumentException("annotation " + byNameType + "does not exist ByNameFrom Type.");
        }
        Class<FROM> fromTypeValue = (Class<FROM>) from.value();

        // 获取ByName的代理映射集

        Map<String, BiFunction<Method, Object[], Object>> stringBiFunctionMap = byNameField(byName);

        // 改变annotationType方法的返回值为原本的类型
        stringBiFunctionMap.put("annotationType", (m, o) -> fromType);

        return ProxyUtils.annotationProxy(fromTypeValue,
                proxyByMethodMap(byNameType, withObject(
                        byName,
                        stringBiFunctionMap
                ))
        );
    }




    private static ProxyUtils.ExProxyHandler<Method, Object[], Object> proxyByMethodMap(Class<? extends Annotation> annotationType, Map<String, BiFunction<Method, Object[], Object>> map) {
        return (m, o) -> {
            // 先判断map中有没有
            BiFunction<Method, Object[], Object> func = map.get(m.getName());
            if(func != null){
                return func.apply(m, o);
            }else{
                Object defaultValue = m.getDefaultValue();
                if(defaultValue != null){
                    return defaultValue;
                }else{
                    throw new IllegalArgumentException("注解'@" + annotationType.getSimpleName() + "' 参数 '" + m.getName() + "()' 不存在默认值。");
                }

            }

        };
    }

    /**
     * 通过一个ByName注解获取创建代理对象所需要的值
     *
     * @return
     */
    private static <T extends Annotation> Map<String, BiFunction<Method, Object[], Object>> byNameField(T anno) {
        // 获取这个注解的全部方法并转化为对应的Map
        Method[] methods = anno.annotationType().getMethods();

        return new HashMap<String, BiFunction<Method, Object[], Object>>(methods.length) {{
            for (Method method : methods) {
                ByNameField byNameField = method.getAnnotation(ByNameField.class);
                if (byNameField != null) {
                    // 存在byNameField
                    // 获取对应的枚举对象
                    Class<? extends Enum<?>> enumType = byNameField.value();
                    // 获取到值
                    Object byNameValue;
                    try {
                        byNameValue = method.invoke(anno);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RobotRuntimeException(e);
                    }
                    // 判断是字符串还是字符串数组
                    if (method.getReturnType().isArray()) {
                        // 是数组 转化为String[]
                        String[] valueArr = (String[]) byNameValue;
                        // 创建对应枚举类型的数组
                        Object enumArrObj = Array.newInstance(enumType, valueArr.length);
                        // 遍历并获取值
                        for (int i = 0; i < valueArr.length; i++) {
                            Array.set(enumArrObj, i, Enum.valueOf((Class) enumType, valueArr[i]));
                        }
                        put(method.getName(), (m, o) -> enumArrObj);
                    } else {
                        // 不是数组, 转化为String，返回获取值
                        String value = (String) byNameValue;
                        Enum enumValue = Enum.valueOf((Class) enumType, value);
                        put(method.getName(), (m, o) -> enumValue);
                    }
                } else {
                    // 不存在，直接执行放入
                    put(method.getName(), (m, o) -> {
                        try {
                            return method.invoke(anno, o);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RobotRuntimeException(e);
                        }
                    });
                }
            }
        }};

    }


    /**
     * Object 的继承方法
     */
    private static Method[] ms = Object.class.getMethods();

    /**
     * 根据一个基础对象执行补全所有的Object继承方法
     * 直接修改base这个参数
     * 不会覆盖原有的值
     *
     * @param baseObj 基础对象
     * @param base    Map
     */
    private static Map<String, BiFunction<Method, Object[], Object>> withObject(Object baseObj, Map<String, BiFunction<Method, Object[], Object>> base) {
        for (Method me : ms) {

            // 如果有旧值，使用旧值
            base.merge(me.getName(), (m, o) -> {
                try {
                    return m.invoke(baseObj, o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RobotRuntimeException(e);
                }
            }, (o, v) -> o);
        }
        return base;

    }


}
