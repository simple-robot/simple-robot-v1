package com.forte.qqrobot.anno.factory;

import com.forte.qqrobot.beans.function.MostTypeFilter;
import com.forte.qqrobot.beans.types.MostType;

import java.util.function.IntFunction;

/**
 *
 * 为创建 {@link com.forte.qqrobot.beans.types.MostType} 枚举类型的工厂
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MostTypeFactory extends BaseFactory<MostType> {

    /** enum type */
    private static final Class<MostType> ENUM_TYPE = MostType.class;

    /** constructor types array */
    private static final Class<?>[] CONSTRUCTOR_TYPES = {MostTypeFilter.class};

    /** to array function */
    private static final IntFunction<MostType[]> TO_ARRAY_FUNCTION = MostType[]::new;

    private static final MostTypeFactory SINGLE = new MostTypeFactory();

    private MostTypeFactory(){}

    public static MostTypeFactory getInstance() {
        return SINGLE;
    }

    /**
     * 注册一个新的mostType类型枚举
     * @param name              name
     * @param mostTypeFilter    枚举所需构造
     * @return
     */
    public MostType register(String name, MostTypeFilter mostTypeFilter){
        try {
            return super.registerEnum(name, mostTypeFilter);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static MostType registerType(String name, MostTypeFilter mostTypeFilter){
        return getInstance().register(name, mostTypeFilter);
    }


    @Override
    protected Class<MostType> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<MostType[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }



    /** 判断参数是否合规 */
    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数只有一个
        if(!(params[0] instanceof MostTypeFilter)){
            throw new IllegalArgumentException("参数不是["+ MostTypeFilter.class +"]类型！");
        }
    }
}
