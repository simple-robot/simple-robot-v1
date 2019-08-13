package com.forte.qqrobot.utils.proxyhelper;

import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.anno.Ignore;
import com.forte.qqrobot.anno.Key;
import com.forte.utils.reflect.ProxyUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * JSON格式参数获取助手
 *
 *  使用代理获取有几点需求：
 *  1、返回值要么是一个可以通过JSON转化的Bean，要么是一个字符串，否则将大概率转化失败
 *  2、参数如果不添加@Key注解则请保证pom中添加了-parameters参数
 *  3、必须使用接口类型的类
 *  4、如果有不需要代理的方法，请添加@Ignore注解
 *
 *  @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class JSONParameterCreatorHelper {


    /**
     * 将一个接口中的方法转化为Json字符串或者指定返回值bean
     * @param interfaceType 接口类型
     * @param ifIgnore      如果为忽略的方法，自定义处理
     */
    public static <T> T toJsonParameterCreator(Class<T> interfaceType, BiFunction<Method, Object[], Object> ifIgnore){
        if(!interfaceType.isInterface()){
            throw new IllegalArgumentException("type should be an interface, but: " + interfaceType);
        }

        //是接口类型，创建代理对象
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

            //构建JSONMap
            JSONObject jsonObject = new JSONObject(){{
                Parameter[] parameters = m.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    Key keyAnnotation = parameter.getAnnotation(Key.class);
                    String key;
                    if(keyAnnotation == null || keyAnnotation.value().trim().length() == 0){
                        key = parameter.getName();
                    }else{
                        key = keyAnnotation.value();
                    }
                    put(key, o[i]);
                }
            }};

            //如果不是default方法，尝试JSON转化
            Class<?> returnType = m.getReturnType();
            if(returnType.equals(JSONObject.class) || returnType.equals(Map.class)){
                return jsonObject;
            }else if(returnType.equals(String.class)){
                //如果返回值是String，转化为String
                return jsonObject.toJSONString();
            }else{
                //否则封装为指定bean
                return jsonObject.toJavaObject(returnType);
            }
        });
    }


    /**
     * 将一个接口中的方法转化为Json字符串或者指定返回值bean
     * 忽略的方法默认返回null
     * @param interfaceType 接口类型
     */
    public static <T> T toJsonParameterCreator(Class<T> interfaceType){
        return toJsonParameterCreator(interfaceType, (m, o) -> null);
    }




}
