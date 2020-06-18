package com.forte.qqrobot.utils;

import cn.hutool.core.lang.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 操作注解值得工具类
 *
 * 需要注意的是，注解的实例仅存在一个，因此当你修改了注解的值，则全局生效。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class AnnotationValueUtils {

    private static final Map<Class<? extends Annotation>, Field> FIELD_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取注解对应得value map
     *
     * @param annotation 注解
     * @return value map
     * @see sun.reflect.annotation.AnnotationInvocationHandler#memberValues
     */
    private static <T extends Annotation> Map<String, Object> getValueMap(T annotation) {
        InvocationHandler ih = Proxy.getInvocationHandler(annotation);
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        // field
        Field memberValuesField = FIELD_CACHE.computeIfAbsent(annotationType, k -> {
            try {
                return ih.getClass().getDeclaredField("memberValues");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
        memberValuesField.setAccessible(true);

        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) memberValuesField.get(ih);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return memberValues;
    }

    /**
     * 修改Annotation的值
     *
     * @param annotation       注解
     * @param valueMapConsumer 注解的值
     */
    public static <T extends Annotation> void setValue(T annotation, Consumer<Map<String, Object>> valueMapConsumer) {
        valueMapConsumer.accept(getValueMap(annotation));
    }

    /**
     * 修改Annotation的值
     *
     * @param annotation 注解
     * @param key        注解的key
     * @param value      要修改的值
     */
    public static <T extends Annotation> void setValue(T annotation, String key, Object value) {
        getValueMap(annotation).put(key, value);
    }

    /**
     * 修改Annotation的值
     *
     * @param annotation 注解
     * @param values     要修改的映射列表 by hutool {@link Pair}
     */
    @SafeVarargs
    public static <T extends Annotation> void setValue(T annotation, Pair<String, Object>... values) {
        final Map<String, Object> valueMap = getValueMap(annotation);
        for (Pair<String, Object> pair : values) {
            valueMap.put(pair.getKey(), pair.getValue());
        }
    }


}

