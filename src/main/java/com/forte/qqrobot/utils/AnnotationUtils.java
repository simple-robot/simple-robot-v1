package com.forte.qqrobot.utils;

import com.forte.qqrobot.anno.Constr;
import com.forte.qqrobot.exception.AnnotationException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 对于一些注解的获取等相关的工具类
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AnnotationUtils {

    /**
     * 获取类中标注了@Constr注解的方法。
     * 如果有多个，获取其中某一个；
     * 如果出现了：
     * - 注解不存在静态方法上、
     * - 方法返回值不是这个类本身或者子类
     * 则会抛出异常
     * @param clz class对象
     * @throws AnnotationException 如果不是静态方法、没有返回值、返回值不是这个类型或者这个类型的字类类型却使用了@Constr注解
     *                              便会抛出此异常
     * @return 可能存在@Constr注解的静态方法
     */
    public static Method getConstrMethod(Class clz) {
        return Arrays.stream(clz.getDeclaredMethods()).filter(m -> {
            Constr constr = m.getAnnotation(Constr.class);
            if (constr != null) {
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new AnnotationException(clz, m, Constr.class, "不是静态");
                }

                if(m.getReturnType().equals(void.class)){
                    throw new AnnotationException(clz, m, Constr.class, "需要返回值");
                }

                if (!FieldUtils.isChild(m.getReturnType(), clz)) {
                    throw new AnnotationException(clz, m, Constr.class, "返回值非该类或该类的子类类型");
                }

                return true;
            } else {
                return false;
            }
        }).findAny().orElse(null);
    }

}
