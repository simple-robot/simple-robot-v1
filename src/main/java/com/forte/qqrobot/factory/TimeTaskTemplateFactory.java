package com.forte.qqrobot.factory;

import com.forte.qqrobot.beans.types.TimeTaskTemplate;
import org.quartz.Trigger;

import java.util.function.Function;
import java.util.function.IntFunction;

/**
 *
 * 用于创建一个新的{@link TimeTaskTemplate} 枚举类型的工厂
 * 注释写累了，反正都大差不差的，里面的注释参考其他工厂吧
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TimeTaskTemplateFactory extends BaseFactory<TimeTaskTemplate> {


    private static final Class<TimeTaskTemplate> ENUM_TYPE = TimeTaskTemplate.class;
    // Function<String, Trigger>
    private static final Class<?>[] CONSTRUCTOR_TYPES = {Function.class};
    private static final IntFunction<TimeTaskTemplate[]> TO_ARRAY_FUNCTION = TimeTaskTemplate[]::new;
    private static final TimeTaskTemplateFactory SINGLE = new TimeTaskTemplateFactory();
    private TimeTaskTemplateFactory(){}
    public static TimeTaskTemplateFactory getInstance(){
        return SINGLE;
    }

    public TimeTaskTemplate register(String name, Function<String, Trigger> triggerCreator){
        try {
            return super.registerEnum(name, triggerCreator);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static TimeTaskTemplate registerType(String name, Function<String, Trigger> triggerCreator){
        return getInstance().register(name, triggerCreator);
    }

    @Override
    protected Class<TimeTaskTemplate> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<TimeTaskTemplate[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }

    @Override
    protected void requireCanUse(String name, Object[] params) {
        // only one and must can be case
        Function<String, Trigger> p = (Function<String, Trigger>) params[0];
        if(p == null){
            throw new NullPointerException();
        }

    }
}
