package com.forte.config.resolve;

import com.forte.config.Conf;
import com.forte.config.InjectableConfig;
import com.forte.config.InjectableConfiguration;
import com.forte.qqrobot.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * 配置转化器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ConfigResolvor {

    /**
     * 解析其内部保存的仓库，记录解析过的内容。一般来讲，这个map只会增加与获取
     */
    private static final Map<Class, InjectableConfig> WAREHOUSE = new HashMap<>(4);

    /**
     * 转化为可注入的
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> InjectableConfig<T> toInjectable(Class<T> type) {
        // 先获取
        InjectableConfig injectableConfig = WAREHOUSE.get(type);

        // 如果有值，直接返回
        if (injectableConfig != null) {
            return injectableConfig;
        }

        // 没有值，解析并保存
        InjectableConfig<T> injectable = resolvor(type);
        WAREHOUSE.put(type, injectable);
        return injectable;
    }

    /**
     * 解析为Injectable
     *
     * @param type 类型
     */
    private static <T> InjectableConfig<T> resolvor(Class<T> type) {
        // 先获取类上的Conf
        Conf classConf = type.getAnnotation(Conf.class);

        Map<String, Map.Entry<Class, BiFunction<Object, T, T>>> map = toInjectFunctionMap(type, classConf);

        return new InjectableConfiguration<>(map);
    }

    /**
     * 转化为name : inject函数
     * inject函数：获取一个值和一个实例对象，注入并返回
     *
     * @param type     主要类型
     * @param baseConf 基础Conf注解
     */
    private static <T> Map<String, Map.Entry<Class, BiFunction<Object, T, T>>> toInjectFunctionMap(Class<T> type, Conf baseConf) {
        // 获取所有的字段。包括父类字段，过滤出存在Conf注解的并遍历
        return FieldUtils
                .getFieldsStream(type, true)
                // 不是静态的字段
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .map(f -> {
                    Conf conf = f.getAnnotation(Conf.class);
                    if (conf == null) {
                        return null;
                    } else {
                        // 此处统一set true
                        f.setAccessible(true);
                        return new AbstractMap.SimpleEntry<>(f, conf);
                    }
                })
                .filter(Objects::nonNull)
                .map(fc -> {
                    Field field = fc.getKey();
                    Conf conf = fc.getValue();

                    // 对应的键名称
                    String keyName;
                    String baseValue = baseConf == null ? "" : baseConf.value();
                    if (baseValue.trim().length() == 0) {
                        keyName = conf.value();
                    } else {
                        keyName = baseValue + "." + conf.value();
                    }


                    // 构建函数
                    // 判断是否可以注入的函数
                    String getterName = conf.getterName();
                    if(getterName.trim().length() == 0){
                        getterName = null;
                    }
                    Predicate<T> canInject = canInject(type, field, conf.onlyNull(), conf.getter(), getterName);
                    // 实际进行注入的函数, 一个实例，一个参数

                    String setterName = conf.setterName();
                    if(setterName.trim().length() == 0){
                        setterName = null;
                    }
                    Class<?> setterParamType = conf.setterParameterType();
                    if(setterParamType == Object.class){
                        setterParamType = field.getType();
                    }

                    BiConsumer<T, Object> doInject = doInject(type, field, conf.setter(), setterName, setterParamType);


                    AbstractMap.SimpleEntry<Class, BiFunction<Object, T, T>> typeFunction
                            = new AbstractMap.SimpleEntry<>(setterParamType, ((o, b) -> {
                        // 如果可以注入
                        if (canInject.test(b)) {
                            // 执行注入
                            doInject.accept(b, o);
                        }
                        return b;
                    }));

                    // 返回两函数的合并
                    return new AbstractMap.SimpleEntry<>(keyName, typeFunction);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    /**
     * 根据参数构建函数用于判断是否可以进行注入
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> canInject(Class<T> type, Field field, boolean onlyNull, boolean getter, String getterName){
        // 实际值获取的方法
        Predicate<T> can ;
        // 如果只有null的时候注入
        if(onlyNull){
            Function<T, Object> valueGetter = null;
            if(getter){
                // 优先通过getter获取
                if(getterName != null){
                    // 如果有getterName，尝试获取getterName
                    try {
                        Method method = type.getMethod(getterName);
                        // 找到了，构建值获取函数函数
                        valueGetter = b -> {
                            try {
                                return method.invoke(b);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        };
                    } catch (NoSuchMethodException ignore) {
                    }


                }

                if(valueGetter == null){
                    // 通过指定名称获取失败了，尝试直接找getter
                    Method fieldGetter = FieldUtils.getFieldGetter(type, field);
                    if(fieldGetter != null){
                        // 找到了
                        valueGetter = b -> {
                            try {
                                return fieldGetter.invoke(b);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        };
                    }
                }
            }

            if(valueGetter == null){
                // 通过直接getter获取失败了，尝试反射直接获取
                valueGetter = b -> {
                    try {
                        return field.get(b);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                };
            }

            // 构建函数
            Function<T, Object> valueGetterFinal = valueGetter;
            can = b -> valueGetterFinal.apply(b) == null;

        }else{
            // 即什么时候都可以注入
            can = b -> true;
        }

        return can;
    }

    /**
     * 构建实际注入值的函数
     * @param type          类型
     * @param field         字段
     * @param setter        是否使用setter
     * @param setterName    是否优先使用setter
     * @param setterParamType setter参数类型
     */
    private static <T> BiConsumer<T, Object> doInject(Class<T> type, Field field, boolean setter, String setterName, Class<?> setterParamType){
        // 实际进行注入的函数, 一个实例，一个参数

        BiConsumer<T, Object> doInject = null;

        if(setter){
            // 优先使用setter
            if(setterName != null){
                try {
                    // 尝试获取指定setterName
                    Method setterMethod = type.getMethod(setterName, setterParamType);
                    // 构建函数
                    doInject = (b, o) -> {
                        try {
                            setterMethod.invoke(b, o);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    };
                } catch (NoSuchMethodException ignore) {
                }
            }

            if(doInject == null){
                // 不使用指定的setter了
                String setterName2 = "set" + FieldUtils.headUpper(field.getName());
                try {
                    Method setterMethod = type.getMethod(setterName2, setterParamType);
                    // 构建函数
                    doInject = (b, o) -> {
                        try {
                            setterMethod.invoke(b, o);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    };
                } catch (NoSuchMethodException ignore) {
                }

            }

        }

        if(doInject == null){
            // 直接使用字段
            doInject = (b, o) -> {
                try {
                    field.set(b, o);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        return doInject;
    }


}
