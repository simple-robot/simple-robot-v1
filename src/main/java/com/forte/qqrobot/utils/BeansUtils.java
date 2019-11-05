package com.forte.qqrobot.utils;

import com.forte.qqrobot.anno.Constr;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.RobotRuntimeException;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 *
 * 对于一些类的实例化的工具类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BeansUtils {

    /**
     * 如果存在@Constr，使用Constr，否则尝试使用无参构造
     * @param clazz 类型
     */
    public static <T> T getInstance(Class<T> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Method[] methods = Arrays.stream(clazz.getMethods())
                //找到携带@Constr注解的且又是公共静态的方法
//                .filter(m -> (m.getAnnotation(Constr.class) != null) &&
                .filter(m -> (AnnotationUtils.getAnnotation(m, Constr.class) != null) &&
                        (Modifier.isStatic(m.getModifiers()))
                )
                .toArray(Method[]::new);

        if(methods.length > 0){
            if(methods.length == 1){
                return (T) methods[0].invoke(null);
            }else{
                throw new RobotRuntimeException(clazz + "中存在多个@Constr注解。");
            }
        }else{
            //如果没有，尝试使用无参构造
            return clazz.newInstance();

        }
    }


    /**
     * 如果存在@Constr，使用Constr，否则尝试使用无参构造，如果还是不行则尝试使用依赖获取
     * @param clazz 类型
     * @param dependCenter  依赖管理器
     */
    public static <T> T getInstance(Class<T> clazz, DependCenter dependCenter) throws InvocationTargetException, IllegalAccessException {
        //先尝试普通的获取实例
        try{
            return getInstance(clazz);
        }catch (InstantiationException ignore){
        }

        //如果没有返回，则说明出现了构造异常，则尝试使用其他构造
        for (Constructor<?> constructor : clazz.getConstructors()) {
            //遍历公共构造函数
            Parameter[] parameters = constructor.getParameters();
            Object[] methodParameters = dependCenter.getMethodParameters(parameters);

            try {
                return (T) constructor.newInstance(methodParameters);
            } catch (InstantiationException ignore) {
            }
        }

        //所有构造都不能创建实例，则返回空
        return null;
    }

}
