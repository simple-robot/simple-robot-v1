package com.forte.qqrobot.factory;

import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;
import com.forte.qqrobot.log.QQLog;

import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.regex.Pattern;

/**
 *
 * 用于新建 {@link com.forte.qqrobot.beans.types.KeywordMatchType} 枚举类型实例的
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class KeywordMatchTypeFactory extends BaseFactory<KeywordMatchType> {

    /** class bean for enum type {@link KeywordMatchType} */
    private static final Class<KeywordMatchType> ENUM_TYPE = KeywordMatchType.class;

    /** constructor types */
    // BiPredicate<String, String> filter
    private static final Class<?>[] CONSTRUCTOR_TYPES = {BiPredicate.class};

    /** function to array */
    private static final IntFunction<KeywordMatchType[]> TO_ARRAY_FUNCTION = KeywordMatchType[]::new;

    /** single bean */
    private static final KeywordMatchTypeFactory FACTORY = new KeywordMatchTypeFactory();

    private KeywordMatchTypeFactory(){
        if(FACTORY != null){
            throw new RuntimeException("No! You don't need more examples.");
        }
    }

    public static KeywordMatchTypeFactory getInstance() {
        return FACTORY;
    }


    /**
     * 注册一个新的 {@link KeywordMatchType} 实例
     * @see #register(String, BiPredicate)
     * @param name      枚举名称
     * @param filter    字符串过滤规则
     * @return 枚举实例
     * @throws EnumInstantiationRequireException 参数权限验证失败
     * @throws EnumInstantiationException        实例化异常
     */
    public KeywordMatchType register(String name, BiPredicate<String, Pattern> filter) throws EnumInstantiationRequireException, EnumInstantiationException {
            return super.registerEnum(name, filter);
    }



    /**
     * 注册一个新的 {@link KeywordMatchType} 实例 - 静态窗口
     * @param name      枚举名称
     * @param filter    字符串过滤规则
     * @return
     */
    public static KeywordMatchType registerType(String name, BiPredicate<String, Pattern> filter) throws EnumInstantiationRequireException, EnumInstantiationException {
        return getInstance().register(name, filter);
    }


    @Override
    protected Class<KeywordMatchType> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<KeywordMatchType[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }

    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数只可能有一个，即BiPredicate<String, String>
        // 所以只要不报错，就说明参数没问题
        BiPredicate<String, String> filter = (BiPredicate<String, String>) params[0];
    }
}
