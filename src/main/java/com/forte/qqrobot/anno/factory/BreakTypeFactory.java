package com.forte.qqrobot.anno.factory;

import com.forte.qqrobot.beans.types.BreakType;

import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 *
 * 截断类型工厂
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BreakTypeFactory extends BaseFactory<BreakType> {
    private static final Class<BreakType> ENUM_TYPE = BreakType.class;
    private static final Class<?>[] CONSTRUCTOR_TYPES = {Predicate.class};
    private static final IntFunction<BreakType[]> TO_ARRAY_FUNCTION = BreakType[]::new;
    private static final BreakTypeFactory SINGLE = new BreakTypeFactory();
    private BreakTypeFactory(){ }

    public static BreakTypeFactory getInstance(){
        return SINGLE;
    }


    public BreakType register(String name, Predicate<Object> test){
        try {
            return super.registerEnum(name, test);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static BreakType registerType(String name, Predicate<Object> test){
        return getInstance().register(name, test);
    }


    @Override
    protected Class<BreakType> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<BreakType[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }

    /**
     * 参数判断
     * @param name          名称
     * @param params        参数列表
     */
    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数只有一个
        Predicate<Object> p = (Predicate<Object>) params[0];
        Objects.requireNonNull(p);
    }
}
