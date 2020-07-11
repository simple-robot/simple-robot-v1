/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AnnotationUtils.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.utils;

import com.forte.lang.Language;
import com.forte.qqrobot.anno.*;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.exception.AnnotationException;
import com.forte.utils.reflect.ProxyUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

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
     * 注解缓存，记录曾经保存过的注解与其所在类
     */
    private static final Map<AnnotatedElement, Set<Annotation>> ANNOTATION_CACHE = new ConcurrentHashMap<>();

    /**
     * 记录已经证实不存在的注解信息
     */
    private static final Map<AnnotatedElement, Set<Class<Annotation>>> NULL_CACHE = new ConcurrentHashMap<>();

    /**
     * 此工具类中使用到的异常的语言前缀
     */
    private static final String LANG_EX_TAG_HEAD = "exception.annotation";

    /**
     * 是否可以将 {@link Depend} 兼容为 {@link javax.annotation.Resource}
     */
    private static volatile boolean resourceAble = true;

    /**
     * 获取语言结果字符串
     * @param tag       tag
     * @param format    格式化参数
     */
    private static String getLang(String tag, Object... format){
        return Language.format(LANG_EX_TAG_HEAD, tag, format);
    }




    /**
     * 获取Depend注解。
     * 如果获取不到Depend, 则尝试获取{@link javax.annotation.Resource}
     * @param from
     * @return
     */
    public static Depend getDepend(AnnotatedElement from){
//        ProxyUtils.annotationProxyByDefault()
        // 先获取depend
        final Depend depend = getAnnotation(from, Depend.class);
        if(depend != null){
            return depend;
        }else{
            if(!resourceAble){
                return null;
            }
            Class resourceClass;
            try {
                resourceClass = Class.forName("javax.annotation.Resource");
            } catch (ClassNotFoundException e) {
                resourceAble = false;
                return null;
            }
            try {
                final javax.annotation.Resource resource = (javax.annotation.Resource) getAnnotation(from, resourceClass);
                if(resource == null){
                    return null;
                }else{
                    final String name = resource.name();
                    final Class<?> type = resource.type();

                    Map<String, BiFunction<Method, Object[], Object>> proxyMap = new HashMap<>();
                    proxyMap.put("value", (m, o) -> name);
                    proxyMap.put("type", (m, o) -> type);
                    proxyMap.put("equals", (m, o) -> {
                       final Object other = o[0];
                       if(other instanceof Annotation){
                           if(((Annotation) other).annotationType() != Depend.class){
                               return false;
                           }else{
                               return resource.hashCode() == other.hashCode();
                           }
                       }else{
                           return false;
                       }
                    });
                    proxyMap.put("toString", (m, o) -> "@Depend->" + resource.toString() + "("+ resource.hashCode() +")");
                    proxyMap.put("hashCode", (m, o) -> resource.hashCode());
                    proxyMap.put("annotationType", (m, o) -> Depend.class);
                    final Depend proxyDepend = ProxyUtils.annotationProxyByDefault(Depend.class, proxyMap);
                    // 计入缓存
                    mappingAndSaveCache(null, from, proxyDepend);
                    return proxyDepend;
                }
            }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
        }
    }


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
    public static <T extends Annotation> T getAnnotation(AnnotatedElement from, Class<T> annotationType) {
        return getAnnotation(from, annotationType, (Class<T>[]) new Class[]{});
    }

    /**
     * 从某个类上获取注解对象，注解可以深度递归
     * 如果存在多个继承注解，则优先获取浅层第一个注解，如果浅层不存在，则返回第一个获取到的注解
     * 请尽可能保证仅存在一个或者一种继承注解，否则获取到的类型将不可控
     *
     * @param from           获取注解的某个类
     * @param annotationType 想要获取的注解类型
     * @param ignored        获取注解列表的时候的忽略列表
     * @return 获取到的第一个注解对象
     */
    public static <T extends Annotation> T getAnnotation(AnnotatedElement from, Class<T> annotationType, Class<T>... ignored) {
        return getAnnotation(null, from, annotationType, ignored);
    }


    /**
     * 从某个类上获取注解对象，注解可以深度递归
     * 如果存在多个继承注解，则优先获取浅层第一个注解，如果浅层不存在，则返回第一个获取到的注解
     * 请尽可能保证仅存在一个或者一种继承注解，否则获取到的类型将不可控
     *
     * @param fromInstance    from的实例类，一般都是注解才需要。
     * @param from           获取注解的某个类
     * @param annotationType 想要获取的注解类型
     * @param ignored        获取注解列表的时候的忽略列表
     * @return 获取到的第一个注解对象
     */
    private static <T extends Annotation> T getAnnotation(Annotation fromInstance, AnnotatedElement from, Class<T> annotationType, Class<T>... ignored) {
        // 首先尝试获取缓存
        T cache = getCache(from, annotationType);
        if(cache != null){
            return cache;
        }

        if(isNull(from, annotationType)){
            return null;
        }


        //先尝试直接获取
        T annotation = from.getAnnotation(annotationType);

        //如果存在直接返回，否则查询
        if (annotation != null) {
            mappingAndSaveCache(fromInstance, from, annotation);
            return annotation;
        }

        // 获取target注解
        Target target = annotationType.getAnnotation(Target.class);
        // 判断这个注解能否标注在其他注解上，如果不能，则不再深入获取
        boolean annotationable = false;
        if (target != null) {
            for (ElementType elType : target.value()) {
                if (elType == ElementType.TYPE || elType == ElementType.ANNOTATION_TYPE) {
                    annotationable = true;
                    break;
                }
            }
        }

        Annotation[] annotations = from.getAnnotations();
        annotation = annotationable ? getAnnotationFromArrays(fromInstance, annotations, annotationType, ignored) : null;


        // 如果还是获取不到，看看查询的注解类型有没有对应的ByNameType
        if (annotation == null) {
            annotation = getByNameAnnotation(from, annotationType);
        }

        // 如果无法通过注解本身所指向的byName注解获取，看看有没有反向指向此类型的注解
        // 此情况下不进行深层获取
        if(annotation == null){
            annotation = getAnnotationFromByNames(annotations, annotationType);
        }

        // 如果最终不是null，计入缓存
        if(annotation != null){
            mappingAndSaveCache(fromInstance, from, annotation);
        }else{
            nullCache(from, annotationType);
        }

        return annotation;
    }


    /**
     * 逆向查询，把ByName转化为正常的注解。
     * @param annotations    获取源头上拿到的注解列表，例如类上、字段上等等。
     * @param annotationType 想要获取的注解类型。
     */
    private static <T extends Annotation> T getAnnotationFromByNames(Annotation[] annotations, Class<T> annotationType){
        // 获取所有的注解
        return Arrays.stream(annotations).map(a -> {
            // 这个注解a存在@ByNameFrom
            ByNameFrom byNameFrom = a.annotationType().getAnnotation(ByNameFrom.class);
            if(byNameFrom == null){
                return null;
            }else{
                return new AbstractMap.SimpleEntry<>(a, byNameFrom);
            }
        })
                .filter(Objects::nonNull)
                .filter(a -> a.getValue().value().equals(annotationType))
                .map(a -> AnnotationByNameUtils.byName(a.getKey(), annotationType))
                .findFirst().orElse(null);
    }


    /**
     * 通过参数对象获取，且是通过byName注解获取
     * @param from              来源
     * @param annotationType    父注解类型
     */
    private static <T extends Annotation> T getByNameAnnotation(AnnotatedElement from, Class<T> annotationType){
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
     *
     * @param from 如果是来自与另一个注解的, 此处是来源。可以为null
     * @param array
     * @param annotationType
     * @param <T>
     * @return
     */
    private static <T extends Annotation> T getAnnotationFromArrays(Annotation from, Annotation[] array, Class<T> annotationType, Class<T>... ignored) {
        //先浅查询第一层
        //全部注解
        Annotation[] annotations = Arrays.stream(array)
                .filter(a -> {
                    for (Class<? extends Annotation> aType : ignored) {
                        if (a.annotationType().equals(aType)) {
                            return false;
                        }
                    }
                    return true;
                })
                .filter(a -> {
                    if (a == null) {
                        return false;
                    }
                    //如果此注解的类型就是我要的，直接放过
                    if (a.annotationType().equals(annotationType)) {
                        return true;
                    }
                    //否则，过滤掉java原生注解对象
                    //通过包路径判断
                    return !JAVA_ANNOTATION_PACKAGE.equals(a.annotationType().getPackage());
                }).peek(a -> {
                    if(from != null){
                        mapping(from, a);
                    }
                }).toArray(Annotation[]::new);


        if (annotations.length == 0) {
            return null;
        }

        Class<? extends Annotation>[] annotationTypes = new Class[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            annotationTypes[i] = annotations[i].annotationType();
        }

        Class<T>[] newIgnored = new Class[annotationTypes.length + ignored.length];
        System.arraycopy(ignored, 0, newIgnored, 0, ignored.length);
        System.arraycopy(annotationTypes, 0, newIgnored, ignored.length, annotationTypes.length);

        //遍历
//        for (Annotation a : annotations) {
//            T annotationGet = a.annotationType().getAnnotation(annotationType);
//            if (annotationGet != null) {
//                return annotationGet;
//            }
//        }

        //如果浅层查询还是没有，递归查询
        //再次遍历

        for (Annotation a : annotations) {
            T annotationGet = getAnnotation(a, a.annotationType(), annotationType, newIgnored);
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
     *                             see lang:
     *                             <ul>
     *                              <li>exception.annotation.notStatic</li>
     *                              <li>exception.annotation.needReturn</li>
     *                              <li>exception.annotation.returnTypeWrong</li>
     *                             </ul>
     */
    public static Method getConstrMethod(Class clz) {
        return Arrays.stream(clz.getDeclaredMethods()).filter(m -> {
            Constr constr = getAnnotation(m, Constr.class);
            if (constr != null) {
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new AnnotationException(clz, m, Constr.class, getLang("notStatic"));
                }

                if (m.getReturnType().equals(void.class)) {
                    throw new AnnotationException(clz, m, Constr.class, getLang("needReturn"));
                }

                if (!FieldUtils.isChild(m.getReturnType(), clz)) {
                    throw new AnnotationException(clz, m, Constr.class, getLang("returnTypeWrong"));
                }

                return true;
            } else {
                return false;
            }
        }).findAny().orElse(null);
    }

    /**
     * 从缓存中获取缓存注解
     * @param from          来源
     * @param annotatedType 注解类型
     * @return  注解缓存，可能为null
     */
    private static <T extends Annotation> T getCache(AnnotatedElement from, Class<T> annotatedType){
        Set<Annotation> list = ANNOTATION_CACHE.get(from);
        if(list != null){
            // 寻找
            for (Annotation a : list) {
                if(a.annotationType().equals(annotatedType)){
                    return (T) a;
                }
            }
        }
        // 找不到，返回null
        return null;
    }

    /**
     * 记录一个得不到的缓存
     * @param from {@link AnnotatedElement}
     * @param annotatedType annotation class
     */
    private static <T extends Annotation> void nullCache(AnnotatedElement from, Class<T> annotatedType){
        final Set<Class<Annotation>> classes = NULL_CACHE.computeIfAbsent(from, k -> new HashSet<>());
        classes.add((Class<Annotation>) annotatedType);
    }

    /**
     * 判断是否获取不到
     * @param from {@link AnnotatedElement}
     * @param annotatedType annotation class
     */
    private static <T extends Annotation> boolean isNull(AnnotatedElement from, Class<T> annotatedType){
        final Set<Class<Annotation>> classes = NULL_CACHE.get(from);
        if(classes == null || classes.isEmpty()){
            return false;
        }
        return classes.contains(annotatedType);
    }



    /**
     * 记录一条缓存记录。
     */
    private static boolean saveCache(AnnotatedElement from, Annotation annotation){
        Set<Annotation> set;
        synchronized (ANNOTATION_CACHE) {
            set = ANNOTATION_CACHE.computeIfAbsent(from, k -> new HashSet<>());
            // 如果为空，新建一个并保存
        }
        // 记录这个注解
        return set.add(annotation);
    }

    /**
     * 执行注解映射
     */
    private static void mapping(Annotation from, Annotation to){
        final Class<? extends Annotation> fromAnnotationType = from.annotationType();
        final Method[] methods = fromAnnotationType.getMethods();
        Map<String, Object> mapping = new HashMap<>(4);
        for (Method method : methods) {
            final AnnotateMapping annotateMapping = method.getAnnotation(AnnotateMapping.class);
            if(annotateMapping != null){
                if(annotateMapping.type().equals(to.annotationType())){
                    String name = annotateMapping.name();
                    if(name.length() == 0){
                        name = method.getName();
                    }
                    try {
                        Object value = method.invoke(from);
                        AnnotationValueUtils.setValue(to, name, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new AnnotationException(e);
                    }
                }
            }
        }
    }
    
    /**
     * 进行注解值映射，并缓存，返回
     */
    private static boolean mappingAndSaveCache(Annotation fromInstance, AnnotatedElement from, Annotation annotation){
        // 如果from是一个注解
        if(fromInstance != null && from instanceof Class && ((Class) from).isAnnotation()){
            mapping(fromInstance, annotation);
        }
        return saveCache(from, annotation);
    }


    /**
     * 清除缓存
     */
    public static void cleanCache(){
        ANNOTATION_CACHE.clear();
    }

}
