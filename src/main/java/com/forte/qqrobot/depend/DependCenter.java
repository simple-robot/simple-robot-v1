/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     DependCenter.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend;

import cn.hutool.core.convert.Convert;
import com.forte.lang.Language;
import com.forte.qqrobot.anno.depend.FilterValue;
import com.forte.qqrobot.constant.PriorityConstant;
import com.forte.qqrobot.depend.util.DependUtil;
import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.FieldUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;

/**
 * 依赖资源管理中心
 * 可以通过配置类来配置优先资源获取方法
 * 资源存入时不可覆盖，key值不可重复
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SuppressWarnings({"WeakerAccess", "unchecked", "UnusedReturnValue", "JavaDoc"})
public class DependCenter implements DependGetter, DependInjector, Closeable {

    /**
     * 获取方法参数名获取器
     */
//    @Deprecated
//    private static ParamNameGetter paramNameGetter = ParamGetterManager.getParamNameGetter();


    // 以依赖名称为key的单例Map
    private final Map<String, Object> SINGLE_FACTORY;

    /**
     * 基础数据储存仓库
     */
    private final BasicResourceWarehouse basicResourceWarehouse;

    /**
     * 以名称为key的资源仓库
     */
    private final Map<String, Depend> nameResourceWarehouse;

    /**
     * 以Class为key的资源仓库
     * 一般来讲，应当只存在一个。但是如果名称唯一则是有可能存在多个的。
     */
    private final Map<Class, List<Depend>> classResourceWareHouse;

    /**
     * 获取资源对象实例接口实例
     * 在获取实例的时候，会优先使用此接口的获取方法，如果获取失败则再尝试通过自身来获取
     */
    private final DependGetter dependGetter;

    /**
     * 记录需要初始化的依赖的列表
     */
    private Queue<Depend> initQueue = new LinkedList<>();

    /**
     * 　初始化一次需要被初始化的Depend
     */
    public void initDependWhoNeed() {
        synchronized (this) {
            Depend polled;
            do {
                polled = initQueue.poll();
                if (polled != null) {
                    polled.getInstance();
                }
            } while (polled != null);
        }
    }

    /**
     * 构造
     */
    public DependCenter() {
        basicResourceWarehouse = new BasicResourceWarehouse();
        SINGLE_FACTORY = new LinkedHashMap<>(8);
        nameResourceWarehouse = new ConcurrentHashMap<>(8);
        classResourceWareHouse = new ConcurrentHashMap<>(8);
        this.dependGetter = this;
    }

    /**
     * 构造
     */
    public DependCenter(DependGetter dependGetter) {
        basicResourceWarehouse = new BasicResourceWarehouse();
        SINGLE_FACTORY = new LinkedHashMap<>(8);
        nameResourceWarehouse = new ConcurrentHashMap<>(8);
        classResourceWareHouse = new ConcurrentHashMap<>(8);
        this.dependGetter = dependGetter;
    }

    /**
     * 记录一个单例
     */
    private Object putSingle(String key, Object value) {
        synchronized (SINGLE_FACTORY) {
            return SINGLE_FACTORY.put(key, value);
        }
    }

    //**************************************
    //
    //          以下为依赖的加载相关
    //
    //**************************************

    /**
     * 根据一个对象直接添加，默认为单例模式
     * 依赖名称默认使用类名
     *
     * @param bean 实例对象
     * @return
     */
    public <T> DependCenter load(T bean) {
        return load(FieldUtils.headLower(bean.getClass().getSimpleName()), bean);
    }

    /**
     * 根据一个对象直接添加，默认为单例模式
     * 依赖名称默认使用类名 <br>
     * 无视出现的异常
     *
     * @param bean 实例对象
     * @return
     */
    public <T> DependCenter loadIgnoreThrow(T bean) {
        try {
            return load(bean.getClass().getSimpleName(), bean);
        } catch (Exception e) {
            return this;
        }
    }

    /**
     * 根据一个对象直接添加，默认为单例模式
     *
     * @param name 依赖名称
     * @param bean 实例对象
     */
    public <T> DependCenter load(String name, T bean) {
        buildDepend(name, bean);
        return this;
    }


    /**
     * 根据一个单独的实例构建一个Depend实例
     * 默认为单例
     *
     * @param bean 实例对象
     * @return 构建对应的单例Depend对象
     */
    private <T> void buildDepend(String name, T bean) {
        Beans<T> beans = BeansFactory.getBeansSingle(name, bean);
        saveBeanToDepend(beans);
    }


    /**
     * 加载一批依赖对象
     */
    public DependCenter load(Predicate<Class> classTest, Collection<Class<?>> list) {
        return load(classTest, list.toArray(new Class[0]));
    }

    /**
     * 判断规则默认为非接口、抽象类且存在@Beans注解的class对象
     *
     * @param list classes对象列表
     */
    public DependCenter load(Collection<Class<?>> list) {
        return load(list.toArray(new Class[0]));
    }

    /**
     * 判断规则默认为非接口、抽象类且存在@Beans注解的class对象
     *
     * @param loadsClasses classes对象列表
     */
    public DependCenter load(Class<?>... loadsClasses) {
        return load(null, c ->
                        //不是接口、不是抽象类
                        (!c.isInterface()) && (!Modifier.isAbstract(c.getModifiers()))
                                &&
                                //存在注解
                                //注解：Beans、Listen
                                (AnnotationUtils.getBeansAnnotationIfListen(c) != null)
                , loadsClasses);
    }

    /**
     * 判断规则默认为非接口、非抽象类且存在@Beans注解的class对象
     *
     * @param loadsClasses classes对象列表
     */
    public DependCenter load(com.forte.qqrobot.anno.depend.Beans beanAnno, Class<?>... loadsClasses) {
        return load(beanAnno, c ->
                        //不是接口、不是抽象类
                        (!c.isInterface()) && (!Modifier.isAbstract(c.getModifiers()))
                                &&
                                //存在注解
                                //注解：Beans、Listen
//                (c.getAnnotation(com.forte.qqrobot.anno.depend.Beans.class) != null)
                                (AnnotationUtils.getBeansAnnotationIfListen(c) != null)
                , loadsClasses);
    }

    /**
     * 加载一批依赖对象
     *
     * @param classTest class判断类，如果有特殊规则则填写，例如全部包扫描的时候，没有注解的类也可以通过
     */
    public DependCenter load(Predicate<Class> classTest, Class<?>... loadsClasses) {
        return load(null, classTest, loadsClasses);
    }


    /**
     * 加载一批依赖对象
     *
     * @param classTest class判断类，如果有特殊规则则填写，例如全部包扫描的时候，没有注解的类也可以通过
     */
    public DependCenter load(com.forte.qqrobot.anno.depend.Beans BeansAnno, Predicate<Class> classTest, Class<?>... loadsClasses) {
        //转化为Beans对象, class对象去重并根据规则过滤
        List<Beans> beans = BeansFactory.getBeans(BeansAnno, Arrays.stream(loadsClasses).distinct().filter(classTest).toArray(Class[]::new));
        //后遍历
        beans.forEach(this::saveBeanToDepend);
        return this;
    }

    private <T> void saveBeanToDepend(Beans<T> b) {
        //获取依赖
        //获取依赖所需参数的获取函数
        Supplier<Object[]> paramsGetter = getInstanceParamsSupplier(b);
        //封装为Depend对象
        String name = b.getName();
        Class<?> type = b.getType();
        //判断是否为单例
        Supplier<?> supplier;
        Function<Object[], T> beanGetInstanceFunction = b.getGetInstanceFunction();

        if (b.isSingle()) {
            //是单例，获取的时候先获取，如果获取失败则保存
            supplier = () -> {
                Object get = SINGLE_FACTORY.get(name);
                if (get == null) {
                    get = beanGetInstanceFunction.apply(paramsGetter.get());
                    putSingle(name, get);
//                    SINGLE_FACTORY.put(name, get);
                }
                return get;
            };
        } else {
            supplier = () -> beanGetInstanceFunction.apply(paramsGetter.get());
        }
        //参数注入函数
        Consumer<?> injectDepend = getInjectDependConsumer(b);

        //额外参数注入函数
        BiConsumer<?, DependGetter> additional = getAddInjectDependConsumer(b);

        //封装为Depend对象
        Depend<?> depend = new Depend(name, type, b.isSingle(), supplier, injectDepend, additional, b.isInit(), b.getPriority());

        //保存
        saveDepend(depend);
    }

    /**
     * 保存依赖
     */
    private <T> void saveDepend(Depend<T> depend) {
        //先判断是否存在此名称，如果存在直接报错
        String name = depend.getName();
        if (contains(name)) {
            //如果存在，直接抛出异常
            throw new DependResourceException("dependExistByName", depend);
        }

        QQLog.debug("run.depend.load", depend);

        //判断类型，如果是基础数据类型，保存到基础，否则保存至其他
        if (BasicResourceWarehouse.isBasicType(depend.getType())) {
            if (basicResourceWarehouse.put(depend.getName(), depend.getInstance()) != null) {
                // 存在常量依赖名称
                throw new DependResourceException("basicDependExistByName", depend.getName());
            }
        } else {
            //非常量数据，保存
            //先根据名称保存
            if (nameResourceWarehouse.put(depend.getName(), depend) != null) {
                throw new DependResourceException("dependExistByName", depend.getName());
            }

            classResourceWareHouse.merge(depend.getType(), new ArrayList<Depend>(2) {{
                add(depend);
            }}, (old, val) -> {
                //noinspection ConstantConditions
                old.add(val.get(0));
                // 根据优先级排序
                Collections.sort(old);
                return old;
            });

            if (depend.isInit()) {
                // 需要初始化，记录至初始化队列
                initQueue.add(depend);
            }
        }
    }


    /**
     * 根据Beans的参数需求，获取她们实例化所需要参数的获取函数
     * 如果无法全部获取，返回null
     */
    private Supplier<Object[]> getInstanceParamsSupplier(Beans beans) {
        //获取beans所需的参数列表, 遍历
        NameTypeEntry[] instanceNeed = beans.getInstanceNeed();
        //参数获取函数列表
        Supplier<?>[] params = new Supplier<?>[instanceNeed.length];

        //遍历并获取参数
        for (int i = 0; i < instanceNeed.length; i++) {
            NameTypeEntry nameTypeEntry = instanceNeed[i];
            Supplier<?> paramSupplier;
            //先判断是否为常量类型
            String name = nameTypeEntry.getKey();
            Class value = nameTypeEntry.getValue();
            if (BasicResourceWarehouse.isBasicType(value)) {
                //是常量类型, 验证空指针
                Objects.requireNonNull(name, Language.format("exception.nullPointer.basicNameCannotEmpty"));
                //获取常量类型
                paramSupplier = () -> constant(name);
            } else {
                //不是常量类，获取参数
                if (name != null) {
                    //有名称，通过名称获取
                    paramSupplier = () -> get(name);
                } else {
                    //没有名称，通过类型获取
                    paramSupplier = () -> get(value);
                }
            }
            params[i] = paramSupplier;
        }

        return () -> Arrays.stream(params).map(Supplier::get).toArray(Object[]::new);
    }


    /**
     * 获取依赖注入函数
     */
    private <T> Consumer<T> getInjectDependConsumer(Beans<T> beans) {
        //查找所有的字段，取到所有存在@Depend注解的字段或方法
        Class<T> type = beans.getType();
        //获取全部字段, 根据注解获取(或全部注入), 转化为字段值注入函数
        BeansData beansData = beans.getBeans();

        boolean allDepend = beansData.allDepend();
        com.forte.qqrobot.anno.depend.Depend depend = beansData.depend();

        Consumer<T>[] consumerArray = Arrays.stream(FieldUtils.getFields(type, true))
//                .filter(f -> allDepend || AnnotationUtils.getAnnotation(f, com.forte.qqrobot.anno.depend.Depend.class) != null)
                .filter(f -> allDepend || AnnotationUtils.getDepend(f) != null)
                //将字段转化为Supplier函数，以获取字段值
                .map(f -> {
                    com.forte.qqrobot.anno.depend.Depend dependAnnotation = AnnotationUtils.getDepend(f);
                    if (allDepend && (dependAnnotation == null)) {
                        dependAnnotation = depend;
                    }

                    //字段名称
                    //noinspection ConstantConditions
                    String name = dependAnnotation.value();
                    boolean orNull = dependAnnotation.orNull();
                    //字段类型
                    Class<?> fieldType = dependAnnotation.type().length == 0 ? f.getType() : dependAnnotation.type()[0];

                    //字段值的获取函数，获取的是Depend对象
                    Supplier<Depend> fieldGetter;
                    if (name.trim().length() == 0) {
                        //如果未指定字段名称，判断是否为常量类型，如果是，尝试获取字段名，否则使用类型注入
                        if (BasicResourceWarehouse.isBasicType(f.getType())) {
                            //是常量类型，通过变量名获取
                            Object constant = constant(f.getName());
                            fieldGetter = () -> constant == null ? null : (new BasicDepend(f.getName(), constant.getClass(), constant));
                        } else {
                            //否则，是普通的类型，通过类型获取
                            fieldGetter = () -> getDepend(fieldType, orNull);
                        }
                    } else {
                        //指定了名称，直接获取
                        fieldGetter = () -> getDepend(name, fieldType, orNull);
                    }

                    //判断字段是否可以注入的函数
                    Function<T, Boolean> canInjFunction;
                    try {
                        canInjFunction = DependUtil.canInj(type, f, dependAnnotation);
                    } catch (NoSuchMethodException e) {
                        throw new DependResourceException("fieldGetterFuncFailed", e);
                    }

                    //赋值函数
                    Consumer<T> setterConsumer;
                    try {
                        setterConsumer = DependUtil.doInj(type, f, dependAnnotation, fieldGetter);
                    } catch (NoSuchMethodException e) {
                        throw new DependResourceException("fieldSetterFuncFailed", e);
                    }

                    //赋值函数
                    return (Consumer<T>) b -> {
                        //值判断, 判断此字段是否可以进行注入
                        if (canInjFunction.apply(b)) {
                            //值赋值
                            setterConsumer.accept(b);
                        }
                    };
                }).toArray(Consumer[]::new);

        //全部字段赋值
        return b -> {
            if(b != null){
                //遍历全部字段值赋值函数并赋值
                for (Consumer<T> inj : consumerArray) {
                    inj.accept(b);
                }
            }
        };
    }

    /**
     * 获取依赖注入函数
     * 提供额外的参数注入函数并且仅使用额外参数进行注入
     */
    private <T> BiConsumer<T, DependGetter> getAddInjectDependConsumer(Beans<T> beans) {
        //查找所有的字段，取到所有存在@Depend注解的字段或方法
        Class<T> type = beans.getType();
        //获取全部字段, 根据注解获取(或全部注入), 转化为字段值注入函数
        BeansData beansData = beans.getBeans();
        BiConsumer<T, DependGetter>[] consumerArray = Arrays.stream(FieldUtils.getFields(type, true))
//                .filter(f -> beansData.allDepend() || AnnotationUtils.getAnnotation(f, com.forte.qqrobot.anno.depend.Depend.class) != null)
                .filter(f -> beansData.allDepend() || AnnotationUtils.getDepend(f) != null)
                //将字段转化为Supplier函数，以获取字段值
                .map(f -> {
                    //获取字段注解
                    com.forte.qqrobot.anno.depend.Depend dependAnnotation = AnnotationUtils.getDepend(f);
                    //如果没有注解且allDepend为true，获取默认注解
                    if ((beansData.allDepend()) && (dependAnnotation == null)) {
                        dependAnnotation = beansData.depend();
                    }

                    //字段名称
                    //noinspection ConstantConditions
                    String name = dependAnnotation.value();
                    //字段类型
                    Class<?> fieldType = dependAnnotation.type().length == 0 ? f.getType() : dependAnnotation.type()[0];

                    //字段值的获取函数，获取的是Depend对象
                    Function<DependGetter, Depend> fieldGetterFunction;
                    boolean init = beans.isInit();
                    boolean orNull = dependAnnotation.orNull();
                    int priority = beans.getPriority();
                    if (name.trim().length() == 0) {
                        //如果未指定字段名称，判断是否为常量类型，如果是，尝试获取字段名，否则使用类型注入
                        if (BasicResourceWarehouse.isBasicType(f.getType())) {
                            //是常量类型，通过变量名获取
                            String fieldName = f.getName();
                            Object thisConstant = this.constant(fieldName);
                            fieldGetterFunction = (add) -> {
                                if (add.equals(this)) {
                                    //就是自身
                                    return thisConstant == null ? null : BasicDepend.getInstance(fieldName, thisConstant);
                                } else {
                                    //不是自身，先额外获取
                                    Object addConstant = add.constant(fieldName);
                                    if (addConstant == null) {
                                        //如果获取不到，直接返回自己获取的值
                                        return thisConstant == null ? null : BasicDepend.getInstance(fieldName, thisConstant);
                                    } else {
                                        //有值
                                        return new BasicDepend(f.getName(), addConstant.getClass(), addConstant);
                                    }

                                }
                            };
                        } else {
                            //否则，是普通的类型，通过类型获取
                            fieldGetterFunction = (add) -> (add.equals(this)) ? this.getDepend(fieldType, orNull) : (add.get(fieldType) == null) ? this.getDepend(fieldType, orNull) : new Depend(fieldType.getSimpleName(), fieldType, false, () -> add.get(fieldType), v -> {
                            }, (v, a) -> {
                            }, init, priority);
                        }
                    } else {
                        //指定了名称，直接获取
                        fieldGetterFunction = (add) -> (add.equals(this)) ? this.getDepend(name, fieldType, orNull) : (add.get(name, fieldType) == null) ? this.getDepend(name, fieldType, orNull) : new Depend(name, fieldType, false, () -> add.get(name, fieldType), v -> {
                        }, (v, a) -> {
                        }, init, priority);
                    }

                    //判断字段是否可以注入的函数
                    Function<T, Boolean> canInjFunction;
                    try {
                        canInjFunction = DependUtil.canInj(type, f, dependAnnotation);
                    } catch (NoSuchMethodException e) {
                        throw new DependResourceException("fieldGetterFuncFailed", e);
                    }

                    //赋值函数
                    BiConsumer<T, DependGetter> setterConsumer;
                    try {
                        setterConsumer = DependUtil.doInj(type, f, dependAnnotation, fieldGetterFunction);
                    } catch (NoSuchMethodException e) {
                        throw new DependResourceException("fieldSetterFuncFailed", e);
                    }

                    //赋值函数
                    return (BiConsumer<T, DependGetter>) (T b, DependGetter add) -> {
                        //值判断, 判断此字段是否可以进行注入
                        if (canInjFunction.apply(b)) {
                            //值赋值
                            setterConsumer.accept(b, add);
                        }
                    };
                }).toArray(BiConsumer[]::new);

        //全部字段赋值
        return (b, add) -> {
            if(b != null){
                //遍历全部字段值赋值函数并赋值
                for (BiConsumer<T, DependGetter> inj : consumerArray) {
                    inj.accept(b, add);
                }
            }
        };
    }


    /**
     * 通过方法获取可以注入的参数
     *
     * @param method    方法对象
     * @param addParams 额外参数
     *                  当存在额外参数的时候，优先使用额外参数进行注入
     */
    public Object[] getMethodParameters(Method method, AdditionalDepends addParams) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0) {
            return new Object[0];
        }
        //优先从额外参数中获取
        return getMethodParameters(parameters, addParams);
    }

    /**
     * 获取可以注入的参数
     *
     * @param parameters 参数列表
     * @param addParams  额外参数， 当存在额外参数的时候，优先使用额外参数进行注入
     * @return
     */
    public Object[] getMethodParameters(Parameter[] parameters, AdditionalDepends addParams) {
        //优先从额外参数中获取
        return Arrays.stream(parameters).map(p -> {
            if (addParams != null) {
                // 如果存在filterValue, 先尝试直接通过Additional获取并转化类型
                final FilterValue filterValue = AnnotationUtils.getAnnotation(p, FilterValue.class);
                if (filterValue != null) {
                    final String name = filterValue.value();
                    final Object addGet = addParams.get(name);
                    // 获取到了
                    if (addGet != null) {
                        return Convert.convert(p.getType(), addGet);
                    }
                }
            }
            return getParameter(p, addParams == null ? AdditionalDepends.getEmpty() : addParams);
        }).toArray();
    }

    /**
     * 通过方法获取可以注入的参数
     *
     * @param method 方法对象
     */
    public Object[] getMethodParameters(Method method) {
        return getMethodParameters(method, null);
    }

    /**
     * 通过方法获取可以注入的参数
     *
     * @param parameters 方法对象
     */
    public Object[] getMethodParameters(Parameter[] parameters) {
        return getMethodParameters(parameters, null);
    }


    /**
     * 将parameter转化为对应的bean
     */
    private Object getParameter(Parameter parameter, AdditionalDepends additionalDepends) {
        //获取注解
        com.forte.qqrobot.anno.depend.Depend dependAnnotation = AnnotationUtils.getDepend(parameter);
        FilterValue filterValueAnnotation = AnnotationUtils.getAnnotation(parameter, FilterValue.class);

        //获取到的参数
        Object param;

        //判断是否存在注解
        if (dependAnnotation != null) {
            //存在注解， 获取信息
            String name = dependAnnotation.value();
            if (name.trim().length() == 0) {
                if (filterValueAnnotation != null) {
                    final String filterValueName = filterValueAnnotation.value();
                    if (filterValueName.trim().length() > 0) {
                        name = filterValueName;
                    }
                }
            }

            if (name.trim().length() == 0) {
                //没有名称，优先使用额外参数获取，其次使用类型查询
                Class<?> type = dependAnnotation.type().length == 0 ? parameter.getType() : dependAnnotation.type()[0];
                type = FieldUtils.basicToBox(type);
                Object addParamGet = additionalDepends.get(type);
                if (addParamGet != null) {
                    param = addParamGet;
                } else {
                    param = this.get(type, additionalDepends);
                }
            } else {
                //有名称，通过名称获取
                Object addParamGet = additionalDepends.get(name);
                if (addParamGet != null) {
                    param = addParamGet;
                } else {
                    param = this.get(name, additionalDepends);
                }
            }
        } else {
            //没有注解, 通过使用参数类型获取
            //先尝试通过额外参数获取
            Class<?> pt = FieldUtils.basicToBox(parameter.getType());
            Object addParamGet = additionalDepends.get(pt);
            if (addParamGet != null) {
                param = addParamGet;
            } else {
                param = this.get(pt, additionalDepends);
            }
        }

        return param;
    }


    //**************************************
    //
    //          以下为依赖的获取相关
    //
    //**************************************

    /**
     * 根据父类类型寻找依赖池中的存在的子类类型列表
     * 只能得到当前依赖中心中的内容
     *
     * @param superType 父类类型
     */
    public <T> List<Class<? extends T>> getTypesBySuper(Class<T> superType) {
        List<Class<? extends T>> list = new ArrayList<>(4);
        // 使用类型工厂
        Set<Class> keySet = classResourceWareHouse.keySet();
        for (Class keyClass : keySet) {
            if (keyClass.equals(superType) || FieldUtils.isChild(keyClass, superType)) {
                // 类型下的depend唯一
                List<Depend> depends = classResourceWareHouse.get(keyClass);
                if (depends.size() > 0) {
                    list.add(keyClass);
                }
            }
        }
        return list;
    }

    /**
     * 根据一个父类类型，获取所有存在的子类。理论上效率较低
     *
     * @param <T> 父类类型，例如一个接口类型
     * @return 所有实现类
     */
    public <T> List<T> getByType(Class<T> superType) {
        return getListByType(superType);
    }

    /**
     * 根据一个父类类型，获取所有存在的子类。理论上效率较低
     *
     * @param superType 父类类型，例如一个接口类型
     * @param a         转化为数组
     * @return 所有实现类
     * @see #getByType(Class, Object[])
     */
    public <T> T[] getByType(Class<T> superType, T[] a) {
        return getByType(superType).toArray(a);
    }

    /**
     * @param type
     * @param orNull
     * @param <T>
     * @return
     */
    public <T> Depend<T> getDepend(Class<T> type, boolean orNull){
        Throwable outDependGetterThrow = null;
        //优先尝试使用外部依赖获取
        if (!dependGetter.equals(this)) {
            try {
                T t = dependGetter.get(type);
                if (t != null) {
                    // 或许可以考虑做个缓存？
                    return new Depend<>(type.getSimpleName(), type, true, () -> t, ti -> {
                    }, (ti, a) -> {
                    }, false, PriorityConstant.SECOND_LAST);
                }
            } catch (Throwable e) {
                outDependGetterThrow = e;
            }
        }

        // 先直接获取
        List<Depend> depends = classResourceWareHouse.get(type);
        if (depends == null) {
            depends = Collections.emptyList();
        }

        // 是否需要重新保存
        boolean save = false;

        //没有获取到，尝试通过子类型获取
        if (depends.size() == 0) {
            Set<Class> keys = classResourceWareHouse.keySet();
            Class[] classes = keys.stream().filter(k -> FieldUtils.isChild(k, type)).toArray(Class[]::new);
            if (classes.length == 0) {
                //还是没有，返回null
                depends = Collections.emptyList();
            } else if (classes.length > 1) {
                //　多个子类，全部获取并排序
                Depend[] dependsByClasses = Arrays.stream(classes).flatMap(c -> classResourceWareHouse.get(c).stream()).sorted().toArray(Depend[]::new);

                if (dependsByClasses[0].getPriority() == dependsByClasses[1].getPriority()) {
                    //不止1个且最高优先级有多个，抛出异常
                    throw new DependResourceException("moreChildType", type, Arrays.toString(classes));
                } else {
                    depends = new ArrayList<Depend>() {{
                        add(dependsByClasses[0]);
                    }};
                    save = true;
                }
            } else {
                depends = classResourceWareHouse.get(classes[0]);
                // 需要标记为重新保存
                save = true;
            }
        } else if (depends.size() > 1) {
            // 依赖多于1个
            if (depends.get(0).getPriority() == depends.get(1).getPriority()) {
                //不止1个且最高优先级有多个，抛出异常
                throw new DependResourceException("moreChildType", type, depends);
            } else {
                Depend first = depends.get(0);
                // 否则，只留下最后一个并标记重新记录
                depends = new ArrayList<Depend>() {{
                    add(first);
                }};
                save = true;

            }

        }
        //判断
        if (depends.size() == 0) {
            //如果还是没有，返回null
            // 2020/4/5 v1.11.0 改为抛出异常
            if (outDependGetterThrow == null) {
                if(orNull){
                    return NullDepend.INSTANCE;
                }
                throw new DependResourceException("noDepend", type);
            } else {
                throw new DependResourceException("noDependAndOut", outDependGetterThrow, type, outDependGetterThrow.getLocalizedMessage());
            }
        } else if (depends.size() > 1) {
            // 多于一个，判断优先级
            final Depend first = depends.get(0);
            Depend second = depends.get(1);
            if (first.getPriority() == second.getPriority()) {
                // 最高优先级相同，抛出异常。
                //多于一个, 一般情况下是使用父类类型获取的时候会存在的情况
                throw new DependResourceException("moreDepend", type);
            } else {
                // 否则仅留下第一个，并标记重新保存。
                depends = new ArrayList<Depend>() {{
                    add(first);
                }};
                save = true;
            }
        }

        // 获取唯一的一个，即第一个
        Depend single = depends.get(0);
        if (save) {
            // 需要重新保存以实现缓存
            // 一般来讲，既然能够拿到，则说明这个依赖必定存在于name中，所以直接保存类型
            classResourceWareHouse.put(type, new ArrayList<Depend>(1) {{
                add(single);
            }});
        }
        return single;

    }

    /**
     * 通过类型获取依赖对象
     */
    public <T> Depend<T> getDepend(Class<T> type) {
        return getDepend(type, false);
    }


    public <T> Depend<T> getDepend(String name, Class<T> type, boolean orNull) {
        return getDepend(name, orNull);
    }

    public <T> Depend<T> getDepend(String name, Class<T> type) {
        return getDepend(name);
    }


    /**
     * TODO orNull
     * @param name
     * @param orNull
     * @return
     */
    @Deprecated
    public Depend getDepend(String name, boolean orNull){
        Throwable outDependGetterThrow = null;
        //优先尝试使用外部依赖获取
        if (!dependGetter.equals(this)) {
            try {
                Object t = dependGetter.get(name);
                if (t != null) {
                    Class<?> type = t.getClass();
                    return new Depend(type.getSimpleName(), type, true, () -> t, ti -> {
                    }, (ti, a) -> {
                    }, false, PriorityConstant.SECOND_LAST);
                }
            } catch (Throwable e) {
                outDependGetterThrow = e;
            }
        }
        Depend<?> depend = nameResourceWarehouse.get(name);

        if (depend == null) {
            if (outDependGetterThrow == null) {
                if(orNull){
                    return NullDepend.INSTANCE;
                }
                throw new DependResourceException("noDepend", "'" + name + "'");
            } else {
                throw new DependResourceException("noDependAndOut", outDependGetterThrow, "'" + name + "'", outDependGetterThrow.getLocalizedMessage());
            }
        }

        return depend;
    }

    /**
     * 通过名称获取依赖对象
     *
     * @param name 名称
     */
    public Depend getDepend(String name) {
        return getDepend(name, false);
    }

    /**
     * 获取常量值
     * 常量只能通过名称获取
     */
    public Object getConstant(String name) {
        //优先尝试使用外部依赖获取
        if (!dependGetter.equals(this)) {
            Object constant = dependGetter.constant(name);
            if (constant != null) {
                return constant;
            }
        }

        return basicResourceWarehouse.get(name);
    }

    /**
     * 获取常量值，转化数据类型
     */
    public <T> T getConstant(String name, Class<T> type) {
        return (T) getConstant(name);
    }

    /**
     * 通过名称获取依赖，转化为指定类型
     *
     * @throws ClassCastException
     */
    @SuppressWarnings("unused")
    public <T> T getDependInstance(String name, Class<T> type) {
        return (T) getDependInstance(name);
    }


    /**
     * 通过名称获取依赖
     */
    public Object getDependInstance(String name) {
        Depend<?> depend = getDepend(name);
        return depend.getInstance();
    }

    /**
     * 通过类型获取依赖
     */
    public <T> T getDependInstance(Class<T> type) {
        Depend<T> depend = getDepend(type);
        return depend.getInstance();

    }


    /**
     * 通过额外参数对某个对象进行强制注入
     */
    public <T> T get(Class<T> type, DependGetter additionalDepends) {
        if (additionalDepends == null) {
            return this.get(type);
        }

        if (additionalDepends instanceof AdditionalDepends) {
            if (((AdditionalDepends) additionalDepends).isEmpty()) {
                return this.get(type);
            }
        }

        //先获取依赖
        Depend<T> depend = getDepend(type);
        //使用额外参数注入依赖对象，额外参数内部不存在其他参数依赖
        //拆分步骤，先获取依赖实例
        T instance = depend.getEmptyInstance();
        //注入额外参数
        depend.injectAdditional(instance, additionalDepends);
        return instance;

    }


    /**
     * 通过额外参数对某个对象进行强制注入
     */
    public Object get(String name, DependGetter additionalDepends) {
        if (additionalDepends == null) {
            return this.get(name);
        }

        if (additionalDepends instanceof AdditionalDepends) {
            if (((AdditionalDepends) additionalDepends).isEmpty()) {
                return this.get(name);
            }
        }

        //先获取依赖
        Depend depend = getDepend(name);
        //使用额外参数注入依赖对象，额外参数内部不存在其他参数依赖
        //拆分步骤，先获取依赖实例
        Object instance = depend.getEmptyInstance();
        //注入额外参数
        depend.injectAdditional(instance, additionalDepends);
        return instance;

    }


    /**
     * 通过额外参数对某个对象进行强制注入
     */
    public <T> T get(String name, Class<T> type, DependGetter additionalDepends) {
        if (additionalDepends == null) {
            return this.get(name, type);
        }

        if (additionalDepends instanceof AdditionalDepends) {
            if (((AdditionalDepends) additionalDepends).isEmpty()) {
                return this.get(name, type);
            }
        }

        //先获取依赖
        Depend<T> depend = getDepend(name);
        //使用额外参数注入依赖对象，额外参数内部不存在其他参数依赖
        //拆分步骤，先获取依赖实例
        T instance = depend.getEmptyInstance();
        //注入额外参数
        depend.injectAdditional(instance, additionalDepends);
        return instance;

    }


    /**
     * 获取依赖
     */
    @Override
    public <T> T get(Class<T> type) {
        if (BasicResourceWarehouse.isBasicType(type)) {
            throw new DependResourceException("basicGet", type);
        } else {
            return getDependInstance(type);
        }
    }

    /**
     * 获取依赖
     */
    @Override
    public <T> T get(String name, Class<T> type) {
//        //如果获取器不是自己，则优先使用获取器获取
        if (BasicResourceWarehouse.isBasicType(type)) {
            return getConstant(name, type);
        } else {
            return getDependInstance(name, type);
        }
    }

    /**
     * 获取依赖，当使用名称获取时，以非常量类型依赖为准
     */
    @Override
    public Object get(String name) {
        Object dependInstance = getDependInstance(name);
        return dependInstance == null ? getConstant(name) : dependInstance;
    }

    /**
     * 获取常量值
     * 常量只能通过名称获取
     */
    @Override
    public Object constant(String name) {
        Throwable out = null;
        //如果获取类不是自己，则优先通过获取器获取
        if (!dependGetter.equals(this)) {
            try {
                Object constant = dependGetter.constant(name);
                if (constant != null) {
                    return constant;
                }
            } catch (Throwable e) {
                out = e;
            }
        }

        final Object basic = basicResourceWarehouse.get(name);
        if (basic == null) {
            if (out == null) {
                throw new DependResourceException("noDepend", "'" + name + "'");
            } else {
                throw new DependResourceException("noDependAndOut", out, "'" + name + "'", out.getLocalizedMessage());
            }
        }
        return basic;
    }

    /**
     * 获取常量值，转化数据类型
     */
    @Override
    public <T> T constant(String name, Class<T> type) {
        return (T) constant(name);
    }

    @Override
    public <T> List<T> getListByType(Class<T> type) {
        List<T> outList = null;
        if (!dependGetter.equals(this)) {
            // 先通过额外的dependGetter获取
            outList = dependGetter.getListByType(type);
        }
        final List<T> list = getThisListByType(type);
        if (outList != null) {
            list.addAll(outList);
        }
        return list;
    }

    /**
     * 获取当前依赖工厂的list
     */
    private <T> List<T> getThisListByType(Class<T> superType) {
        List<T> list = new ArrayList<>(4);
        // 使用类型工厂
        Set<Class> keySet = classResourceWareHouse.keySet();
        for (Class keyClass : keySet) {
            if (keyClass.equals(superType) || FieldUtils.isChild(keyClass, superType)) {
                list.add((T) get(keyClass));
            }
        }
        return list;
    }

    /**
     * 查询三个池子中是否存在某名称的依赖
     */
    private boolean contains(String name) {
        return (basicResourceWarehouse.get(name) != null)
                ||
                (nameResourceWarehouse.get(name) != null)
                ;
    }

    /**
     * 根据类型判断是否存在
     */
    private boolean contains(Class<?> type) {
        //如果是基础数据类型，默认存在
        if (BasicResourceWarehouse.isBasicType(type)) {
            return true;
        } else {
            return classResourceWareHouse.getOrDefault(type, Collections.emptyList()).size() > 0;
        }

    }


    @Override
    public void close() {
        // 单例工厂中，如果为closeable的，关闭
        synchronized (SINGLE_FACTORY) {
            SINGLE_FACTORY.forEach((k, v) -> {
                if (v instanceof Closeable) {
                    Closeable cl = (Closeable) v;
                    try {
                        cl.close();
                        QQLog.debug("depend.center.close", k);
                    } catch (IOException e) {
                        QQLog.error("depend.center.close.failed", e, k, v.getClass(), e.getLocalizedMessage());
                    }
                }
            });
            // 清除
            SINGLE_FACTORY.clear();
        }
    }

    /**
     * 值为null的depend
     */
    static class NullDepend extends Depend {
        public static final NullDepend INSTANCE = new NullDepend();
        /**
         * 构造
         */
        private NullDepend() {
            super("", NullDepend.class, true, () -> null, b -> {}, (a,b) -> {}, false, 0);
        }
    }

}
