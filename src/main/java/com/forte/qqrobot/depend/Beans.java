/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Beans.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend;

import com.forte.lang.Language;
import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.FieldUtils;
import com.forte.qqrobot.utils.ObjectsPlus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 标注了Beans注解的类、方法<br>
 * 封装了他们所携带的各种条件、参数以及成为实例所需要的各类参数、条件<br>
 * 名称绝不可以相同，当名称不同的时候，类名可以相同
 * 但是假如已经有一个此类型存在于单例工厂，将无法添加其他异名单例
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Beans<T> {

    /** 名称 */
    private String name;

    /** 类型 */
    private Class<T> type;

    /** 是否为单例 */
    private boolean single;

    /** 类下所有字段是否都作为依赖注入 */
    private boolean allDepend;

    /** 实例化所需要的参数列表及其对应的name */
    private NameTypeEntry[] instanceNeed;

    /** 获取实例对象的方法 */
    private Function<Object[], T> getInstanceFunction;

    /** 假如此Beans是类上的，那么children代表了其类中存在的其他Beans */
    private Beans[] children;

    /** 父类，只有方法上标注的@Beans才会有值 */
    private Class<?> father;
    
    /** Beans注解对象中的参数，用来代替Beans注解 */
    private BeansData beans;

    /** 是否需要初始化一次 */
    private boolean init;

    /** 优先级 */
    private int priority;




    /**
     * 构造
     */
    public Beans(String name, Class<T> type, boolean single, boolean allDepend, NameTypeEntry[] instanceNeed,
                 Function<Object[], T> getInstanceFunction, Beans[] children, Class<?> father,
                 BeansData beans, boolean init, int priority) {
        this.name = name;
        this.type = type;
        this.single = single;
        this.allDepend = allDepend;
        this.instanceNeed = instanceNeed == null ? new NameTypeEntry[0] : instanceNeed;
        this.getInstanceFunction = getInstanceFunction;
        this.children = children == null ? new Beans[0] : children;
        this.father = father;
        this.beans = beans;
        this.init = init;
        this.priority = priority;

        //判断除了数组之外的类型是否存在null值
        ObjectsPlus.allNonNull("com.forte.qqrobot.depend.Beans对象参数不可出现null值", this.name, this.type, this.single, getInstanceFunction, this.beans);
    }

    //**************** 默认值参数 ****************//

    private static boolean defaultSingle = true;
    private static boolean defaultAllDepend = false;

    //实例化需要的参数列表, 默认为空
    private static NameTypeEntry[] defaultInstanceNeed = new NameTypeEntry[0];

    /**
     * 获取值全部为默认值的Beans对象
     * 需要提供类型
     * @param <U>
     * @return
     */
    public static <U> Beans<U> getDefaultBeans(Class<U> type){
        //默认值
        //名称, 类名，开头小写
        String name = FieldUtils.headLower(type.getSimpleName());

        // 默认无参
        NameTypeEntry[] instanceNeed = defaultInstanceNeed;

        //获取实例的函数
        Function<Object[], U> getInstanceFunction;

        //判断是否存在@Constr注解，如果存在，获取其中某一个
        Method constrMethod = AnnotationUtils.getConstrMethod(type);

        //@Constr注解是否存在
        if(constrMethod == null){
            //不存在, 尝试获取构造方法
            //设置参数：instanceNeed
            Class[] constructorParams = new Class[0];
            Constructor constructor;
            try {
                constructor = type.getConstructor(constructorParams);

                //构建实例化函数
                getInstanceFunction = args -> {
                    try {
                        return (U) constructor.newInstance(args);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                        throw new DependResourceException("["+ type +"]实例化错误", e);
                        throw new DependResourceException("initFailed", e, type);
                    }
                };

            } catch (NoSuchMethodException e) {
//                String message = "无法获取["+ type +"]的无参构造";
                String message = Language.format("exception.noSuchMethod.noSuchNoParamConstructor", type);
                NoSuchMethodException firstNoSuchMethodEx = new NoSuchMethodException(message);
                //如果指定的为无参构造，且如果获取不到指定构造函数，查看此类全部的构造，假如只有一个构造，则使用此构造，否则抛出异常
                    Constructor<?>[] constructors = type.getConstructors();
                    if(constructors.length == 1){
                        //如果仅存在一个构造, 使用这个构造函数
                        Constructor findConstructor = constructors[0];

                        //构建实例化函数
                        getInstanceFunction = args -> {
                            try {
                                return (U)findConstructor.newInstance(args);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e2) {
                                throw new DependResourceException("initFailed", e2, type);
                            }
                        };

                    }else{
                        throw new DependResourceException("moreConstructor", firstNoSuchMethodEx);
                    }
            }
        }else{
            //如果存在, 使用@Constr的方法获取实例
            //设置参数：instanceNeed
            instanceNeed = BeansFactory.getNameTypeArrayByParameters(constrMethod.getParameters());
            //配置实例获取函数
            getInstanceFunction = args -> {
                try {
                    constrMethod.setAccessible(true);
                    U invoke = (U) constrMethod.invoke(null, args);
                    constrMethod.setAccessible(false);
                    return invoke;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new DependResourceException("initFailed", e, name + "("+ type +")");
                }
            };

        }

        return new Beans<>(name, type, defaultSingle, defaultAllDepend, instanceNeed, getInstanceFunction, null, null, BeansData.getInstance(), false, Integer.MAX_VALUE);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isAllDepend() {
        return allDepend;
    }

    public void setAllDepend(boolean allDepend) {
        this.allDepend = allDepend;
    }

    public NameTypeEntry[] getInstanceNeed() {
        return instanceNeed;
    }

    public void setInstanceNeed(NameTypeEntry[] instanceNeed) {
        this.instanceNeed = instanceNeed;
    }

    public Function<Object[], T> getGetInstanceFunction() {
        return getInstanceFunction;
    }

    public void setGetInstanceFunction(Function<Object[], T> getInstanceFunction) {
        this.getInstanceFunction = getInstanceFunction;
    }

    public Beans[] getChildren() {
        return children;
    }

    public void setChildren(Beans[] children) {
        this.children = children;
    }

    public Class<?> getFather() {
        return father;
    }

    public void setFather(Class<?> father) {
        this.father = father;
    }

    public BeansData getBeans() {
        return beans;
    }

    public void setBeans(BeansData beans) {
        this.beans = beans;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static boolean isDefaultSingle() {
        return defaultSingle;
    }

    public static void setDefaultSingle(boolean defaultSingle) {
        Beans.defaultSingle = defaultSingle;
    }

    public static boolean isDefaultAllDepend() {
        return defaultAllDepend;
    }

    public static void setDefaultAllDepend(boolean defaultAllDepend) {
        Beans.defaultAllDepend = defaultAllDepend;
    }

    public static NameTypeEntry[] getDefaultInstanceNeed() {
        return defaultInstanceNeed;
    }

    public static void setDefaultInstanceNeed(NameTypeEntry[] defaultInstanceNeed) {
        Beans.defaultInstanceNeed = defaultInstanceNeed;
    }

    @Override
    public String toString() {
        return "Beans{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", single=" + single +
                ", allDepend=" + allDepend +
                ", instanceNeed=" + Arrays.toString(instanceNeed) +
                ", getInstanceFunction=" + getInstanceFunction +
                ", children=" + Arrays.toString(children) +
                ", father=" + father +
                ", beans=" + beans +
                ", init=" + init +
                ", priority=" + priority +
                '}';
    }
}
