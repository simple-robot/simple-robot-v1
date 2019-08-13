package com.forte.qqrobot.utils.proxyhelper;

import com.forte.qqrobot.anno.Ignore;
import com.forte.qqrobot.anno.Key;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.StringJoiner;
import java.util.function.BiFunction;

/**
 *
 * 将返回值拼接为地址栏请求类型，例如：aaa=2222&ccc=123&name=123
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class UrlParameterCreatorHelper {


    private static StringJoiner getUrlParamegerJoiner(){
        return new StringJoiner("&");
    }

    /**
     * 将一个接口中的方法转化为路径参数类型
     * @param interfaceType 接口类型
     * @param ifNotString   如果返回值不是字符串
     * @param ifIgnore      如果方法被忽略
     */
    public static <T> T toUrlParameterCreator(Class<T> interfaceType, BiFunction<Method, Object[], Object> ifNotString, BiFunction<Method, Object[], Object> ifIgnore){
        if(!interfaceType.isInterface()){
            throw new IllegalArgumentException("type should be an interface, but: " + interfaceType);
        }

        //创建代理
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, (e, m, o) -> {
            //如果是接口中的默认方法，使用特殊方法执行
            //代码源于网络: http://www.it1352.com/988865.html
            if(m.isDefault()){
                Class<?> declaringClass = m.getDeclaringClass();
                Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                constructor.setAccessible(true);

                return constructor.
                        newInstance(declaringClass, MethodHandles.Lookup.PRIVATE).
                        unreflectSpecial(m, declaringClass).
                        bindTo(e).
                        invokeWithArguments(o);
            }

            //如果携带@Ignore注解
            if(m.getAnnotation(Ignore.class) != null){
                return ifIgnore.apply(m, o);
            }

            Class<?> returnType = m.getReturnType();

            //如果返回值不是String
            if(!returnType.equals(String.class)){
                return ifNotString.apply(m, o);
            }

            StringJoiner joiner = getUrlParamegerJoiner();
            //遍历参数，构建返回值
            Parameter[] parameters = m.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                String key;
                Key keyAnnotation = parameters[i].getAnnotation(Key.class);
                if(keyAnnotation == null || keyAnnotation.value().trim().length() == 0){
                    key = parameters[i].getName();
                }else{
                    key = keyAnnotation.value();
                }

                joiner.add(key + '=' + (o[i] == null ? "" : String.valueOf(o[i])));
            }

            return joiner.toString();
        });


    }


    /**
     *
     * @see #toUrlParameterCreator(Class, BiFunction, BiFunction)
     * @param interfaceType 接口类型
     */
    public static <T> T toUrlParameterCreator(Class<T> interfaceType){
        return toUrlParameterCreator(interfaceType, (m, o) -> null, (m, o) -> null);
    }


}
