package com.forte.qqrobot.utils;

import com.forte.qqrobot.anno.ByNameType;
import com.forte.qqrobot.anno.Constr;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.exception.AnnotationException;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * 对于一些注解的获取等相关的工具类
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AnnotationUtils {

    /**
     * java原生注解所在包路径
     */
    private static final Package JAVA_ANNOTATION_PACKAGE = Target.class.getPackage();


    /**
     * 尝试从一个类对象中获取到@Beans注解
     */
    public static Beans getBeansAnnotationIfListen(Class<?> from) {
        Beans fromClass = getAnnotation(from, Beans.class);
        if (fromClass != null) {
            return fromClass;
        }

        //类上没有，查询所有方法是否存在@Listen注解
        //因为@Listen上的@Beans注解都是一样的
        for (Method method : from.getMethods()) {
            Listen listenAnnotation = getAnnotation(method, Listen.class);
            if (listenAnnotation != null) {
                //Listen注解必定存在@Beans注解
                return listenAnnotation.annotationType().getAnnotation(Beans.class);
            }
        }

        //如果还是没有，返回null
        return null;
    }


    /**
     * 从某个类上获取注解对象，注解可以深度递归
     * 如果存在多个继承注解，则优先获取浅层第一个注解，如果浅层不存在，则返回第一个获取到的注解
     * 请尽可能保证仅存在一个或者一种继承注解，否则获取到的类型将不可控
     *
     * @param from           获取注解的某个类
     * @param annotationType 想要获取的注解类型
     * @return 获取到的第一个注解对象
     */
    public static <T extends Annotation> T getAnnotation(Class<?> from, Class<T> annotationType) {
        //先尝试直接获取
        T annotation = from.getAnnotation(annotationType);

        //如果存在直接返回，否则查询
        if (annotation != null) {
            return annotation;
        }

        // 获取target注解
        Target target = annotationType.getAnnotation(Target.class);
        // 判断这个注解能否标注在其他注解上，如果不能，则不再深入获取
        boolean annotationable = false;
        if (target != null) {
            for (ElementType elType : target.value()) {
                if (elType == ElementType.ANNOTATION_TYPE) {
                    annotationable = true;
                    break;
                }
            }
        }

        annotation = annotationable ? getAnnotationFromArrays(from.getAnnotations(), annotationType) : null;

        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        if (annotation == null) {
            annotation = getByNameAnnotation(from, annotationType);
        }

        return annotation;
    }

    /**
     * 从某个字段上获取注解，可深层获取，如果存在多个则只返回获取到的第一个注解信息
     *
     * @param from           想要获取注解对象的字段对象
     * @param annotationType 想要获取的注解类型
     * @return 获取到的第一个注解对象
     * @see #getAnnotation(Class, Class)
     */
    public static <T extends Annotation> T getAnnotation(Field from, Class<T> annotationType) {
        //先尝试直接获取
        T annotation = from.getAnnotation(annotationType);

        //如果存在直接返回，否则查询
        if (annotation != null) {
            return annotation;
        }

        // 获取target注解
        Target target = annotationType.getAnnotation(Target.class);
        // 判断这个注解能否标注在其他注解上，如果不能，则不再深入获取
        boolean annotationable = false;
        if (target != null) {
            for (ElementType elType : target.value()) {
                if (elType == ElementType.ANNOTATION_TYPE) {
                    annotationable = true;
                    break;
                }
            }
        }

        annotation = annotationable ? getAnnotationFromArrays(from.getAnnotations(), annotationType) : null;

        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        if (annotation == null) {
            annotation = getByNameAnnotation(from, annotationType);
        }

        return annotation;
    }

    /**
     * 尝试从参数对象中获取某注解
     *
     * @param from           参数对象
     * @param annotationType 注解类型
     */
    public static <T extends Annotation> T getAnnotation(Parameter from, Class<T> annotationType) {
        //先尝试直接获取
        T annotation = from.getAnnotation(annotationType);

        //如果存在直接返回，否则查询
        if (annotation != null) {
            return annotation;
        }

        // 获取target注解
        Target target = annotationType.getAnnotation(Target.class);
        // 判断这个注解能否标注在其他注解上，如果不能，则不再深入获取
        boolean annotationable = false;
        if (target != null) {
            for (ElementType elType : target.value()) {
                if (elType == ElementType.ANNOTATION_TYPE) {
                    annotationable = true;
                    break;
                }
            }
        }

        annotation = annotationable ? getAnnotationFromArrays(from.getAnnotations(), annotationType) : null;

        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        if (annotation == null) {
            annotation = getByNameAnnotation(from, annotationType);
        }

        return annotation;
    }

    /**
     * 尝试从方法上获取某个类型的注解
     *
     * @param from           方法对象
     * @param annotationType 注解的类型
     */
    public static <T extends Annotation> T getAnnotation(Method from, Class<T> annotationType) {
        //先尝试直接获取
        T annotation = from.getAnnotation(annotationType);

        //如果存在直接返回，否则查询
        if (annotation != null) {
            return annotation;
        }

        // 获取target注解
        Target target = annotationType.getAnnotation(Target.class);
        // 判断这个注解能否标注在其他注解上，如果不能，则不再深入获取
        boolean annotationable = false;
        if (target != null) {
            for (ElementType elType : target.value()) {
                if (elType == ElementType.ANNOTATION_TYPE) {
                    annotationable = true;
                    break;
                }
            }
        }

        annotation = annotationable ? getAnnotationFromArrays(from.getAnnotations(), annotationType) : null;

        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        if (annotation == null) {
            annotation = getByNameAnnotation(from, annotationType);
        }

        return annotation;

    }

    /**
     * 通过参数对象获取，且是通过byName注解获取
     * @param from              来源
     * @param annotationType    父注解类型
     */
    private static <T extends Annotation> T getByNameAnnotation(Parameter from, Class<T> annotationType){
        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        ByNameType byNameType = annotationType.getAnnotation(ByNameType.class);
        if (byNameType != null) {
            // 存在byNameType，看看是啥
            Class<? extends Annotation> byNameAnnotationType = byNameType.value();
            // 尝试通过这个ByName获取真正的对应注解
            // 获取ByName注解的时候不再使用深层获取，而是直接获取
            Annotation byNameOnFrom = from.getAnnotation(byNameAnnotationType);
            return AnnotationByNameUtils.byName(byNameOnFrom, annotationType);
        } else {
            return null;
        }
    }

    /**
     * 通过字段对象获取，且是通过byName注解获取
     * @param from              来源
     * @param annotationType    父注解类型
     */
    private static <T extends Annotation> T getByNameAnnotation(Field from, Class<T> annotationType){
        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        ByNameType byNameType = annotationType.getAnnotation(ByNameType.class);
        if (byNameType != null) {
            // 存在byNameType，看看是啥
            Class<? extends Annotation> byNameAnnotationType = byNameType.value();
            // 尝试通过这个ByName获取真正的对应注解
            // 获取ByName注解的时候不再使用深层获取，而是直接获取
            Annotation byNameOnFrom = from.getAnnotation(byNameAnnotationType);
            return AnnotationByNameUtils.byName(byNameOnFrom, annotationType);
        } else {
            return null;
        }
    }

    /**
     * 通过类对象获取，且是通过byName注解获取
     * @param from              来源
     * @param annotationType    父注解类型
     */
    private static <T extends Annotation> T getByNameAnnotation(Class<?> from, Class<T> annotationType){
        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        ByNameType byNameType = annotationType.getAnnotation(ByNameType.class);
        if (byNameType != null) {
            // 存在byNameType，看看是啥
            Class<? extends Annotation> byNameAnnotationType = byNameType.value();
            // 尝试通过这个ByName获取真正的对应注解
            // 获取ByName注解的时候不再使用深层获取，而是直接获取
            Annotation byNameOnFrom = from.getAnnotation(byNameAnnotationType);
            return AnnotationByNameUtils.byName(byNameOnFrom, annotationType);
        } else {
            return null;
        }
    }

    /**
     * 通过方法对象获取，且是通过byName注解获取
     * @param from              来源
     * @param annotationType    父注解类型
     */
    private static <T extends Annotation> T getByNameAnnotation(Method from, Class<T> annotationType) {
        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        ByNameType byNameType = annotationType.getAnnotation(ByNameType.class);
        if (byNameType != null) {
            // 存在byNameType，看看是啥
            Class<? extends Annotation> byNameAnnotationType = byNameType.value();
            // 尝试通过这个ByName获取真正的对应注解
            // 获取ByName注解的时候不再使用深层获取，而是直接获取
            Annotation byNameOnFrom = from.getAnnotation(byNameAnnotationType);
            return AnnotationByNameUtils.byName(byNameOnFrom, annotationType);
        } else {
            return null;
        }
    }


    /**
     * @param array
     * @param annotationType
     * @param <T>
     * @return
     */
    private static <T extends Annotation> T getAnnotationFromArrays(Annotation[] array, Class<T> annotationType) {
        //先浅查询第一层
        //全部注解
        Annotation[] annotations = Arrays.stream(array).filter(a -> {
            if (a == null) {
                return false;
            }
            //如果此注解的类型就是我要的，直接放过
            if (a.annotationType().equals(annotationType)) {
                return true;
            }
            //否则，过滤掉java原生注解对象
            //通过包路径判断
            if (JAVA_ANNOTATION_PACKAGE.equals(a.annotationType().getPackage())) {
                return false;
            }
            return true;
        }).toArray(Annotation[]::new);


        if (annotations.length == 0) {
            return null;
        }

        //遍历
        for (Annotation a : annotations) {
            T annotationGet = a.annotationType().getAnnotation(annotationType);
            if (annotationGet != null) {
                return annotationGet;
            }
        }

        //如果浅层查询还是没有，递归查询
        //再次遍历
        for (Annotation a : annotations) {
            T annotationGet = getAnnotation(a.annotationType(), annotationType);
            if (annotationGet != null) {
                return annotationGet;
            }
        }

        //如果还是没有找到，返回null
        return null;
    }

    /**
     * 获取类中标注了@Constr注解的方法。
     * 如果有多个，获取其中某一个；
     * 如果出现了：
     * - 注解不存在静态方法上、
     * - 方法返回值不是这个类本身或者子类
     * 则会抛出异常
     *
     * @param clz class对象
     * @return 可能存在@Constr注解的静态方法
     * @throws AnnotationException 如果不是静态方法、没有返回值、返回值不是这个类型或者这个类型的字类类型却使用了@Constr注解
     *                             便会抛出此异常
     */
    public static Method getConstrMethod(Class clz) {
        return Arrays.stream(clz.getDeclaredMethods()).filter(m -> {
            Constr constr = m.getAnnotation(Constr.class);
            if (constr != null) {
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new AnnotationException(clz, m, Constr.class, "不是静态");
                }

                if (m.getReturnType().equals(void.class)) {
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
