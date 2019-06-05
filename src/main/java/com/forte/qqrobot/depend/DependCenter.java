package com.forte.qqrobot.depend;

import com.forte.qqrobot.depend.parameter.ParamGetterManager;
import com.forte.qqrobot.depend.parameter.ParamNameGetter;
import com.forte.qqrobot.depend.util.DependUtil;
import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.utils.SingleFactory;
import jdk.management.resource.ResourceRequestDeniedException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 依赖资源管理中心
 * 可以通过配置类来配置优先资源获取方法
 * 资源存入时不可覆盖，key值不可重复
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DependCenter implements DependGetter {
    /** 以防万一，此处对依赖资源管理中心的单例工厂进行计数 */
    private static final AtomicInteger singleFactoryNo = new AtomicInteger(1);

    private static int getSingleFactoryNo(){
        return singleFactoryNo.getAndAdd(1);
    }

    /** 获取方法参数名获取器 */
    private static ParamNameGetter paramNameGetter = ParamGetterManager.getParamNameGetter();

    /*
        String和8大基础数据类型的资源仓库
        以Class为键的资源仓库
        以name为键的资源仓库
     */
    /** 依赖资源管理中心所使用的单例工厂 */
    private final SingleFactory SINGLE_FACTORY;

    /** 基础数据储存仓库 */
    private final BasicResourceWarehouse basicResourceWarehouse;

    /** 以名称为key的资源仓库 */
    private final Map<String, Depend> nameResourceWarehouse;

    /** 以Class为key的资源仓库 */
    private final Map<Class, List<Depend>> classResourceWareHouse;

    /**
     * 获取资源对象实例接口实例
     * 在获取实例的时候，会优先使用此接口的获取方法，如果获取失败则再尝试通过自身来获取
     *
     * */
    private final DependGetter dependGetter;


    /**
     * 构造
     */
    public DependCenter() {
        SINGLE_FACTORY = SingleFactory.build(DependCenter.class + "_" + DependCenter.getSingleFactoryNo());
        basicResourceWarehouse = new BasicResourceWarehouse();
        nameResourceWarehouse = new ConcurrentHashMap<>();
        classResourceWareHouse = new ConcurrentHashMap<>();
        this.dependGetter = this;
    }

    /**
     * 构造
     */
    public DependCenter(DependGetter dependGetter) {
        SINGLE_FACTORY = SingleFactory.build(DependCenter.class + "_" + DependCenter.getSingleFactoryNo());
        basicResourceWarehouse = new BasicResourceWarehouse();
        nameResourceWarehouse = new ConcurrentHashMap<>();
        classResourceWareHouse = new ConcurrentHashMap<>();
        this.dependGetter = dependGetter;
    }


    //**************************************
    //
    //          以下为依赖的加载相关
    //
    //**************************************


    /**
     * 加载一批依赖对象
     */
    public DependCenter load(Collection<Class<?>> list){
        return load(list.toArray(new Class[0]));
    }

    /**
     * 加载一批依赖对象
     */
    public DependCenter load(Class<?>... loadsClasses) {
        //转化为Beans对象, class对象去重
        List<Beans> beans = BeansFactory.getBeans(Arrays.stream(loadsClasses).distinct().toArray(Class[]::new));

        //后遍历
        beans.forEach(b -> {
                    //获取依赖
                    //获取依赖所需参数的获取函数
                    Supplier<Object[]> paramsGetter = getInstanceParamsSupplier(b);
                    //封装为Depend对象
                    String name = b.getName();
                    Class<?> type = b.getType();
                    //判断是否为单例
                    Supplier<?> supplier;
                    if (b.isSingle()) {
                        //是单例，获取的时候先获取，如果获取失败则保存
                        supplier = () -> SINGLE_FACTORY.getOrSet(b.getType(), () -> b.getGetInstanceFunction().apply(paramsGetter.get()));
                    } else {
                        supplier = () -> b.getGetInstanceFunction().apply(paramsGetter.get());
                    }
                    //参数注入函数
                    Consumer<?> injectDepend = getInjectDependConsumer(b);

                    //封装为Depend对象
                    Depend<?> depend = new Depend(name, type, b.isSingle(), supplier, injectDepend);

                    //保存
                    saveDepend(depend);
                });

        //每次加载后，尝试垃圾回收释放内存
        System.gc();
        return this;
    }

    /**
     * 保存依赖
     */
    private <T> void saveDepend(Depend<T> depend) {
        //先判断是否存在此名称，如果存在直接报错
        String name = depend.getName();
        if(contains(name)){
            //如果存在，直接抛出异常
            throw new DependResourceException("已经存在名称为["+ depend +"]的依赖");
        }

        //判断类型，如果是基础数据类型，保存到基础，否则保存至其他
        if (BasicResourceWarehouse.isBasicType(depend.getType())) {
            if (basicResourceWarehouse.put(depend.getName(), depend.getInstance()) != null) {
                throw new DependResourceException("已存在常量依赖：" + depend.getName());
            }
        } else {
            //非常量数据，保存
            //先根据名称保存
            if (nameResourceWarehouse.put(depend.getName(), depend) != null) {
                throw new DependResourceException("已存在依赖：" + depend.getName());
            }

            classResourceWareHouse.merge(depend.getType(), new ArrayList<Depend>() {{
                add(depend);
            }}, (old, val) -> {
                old.add(val.get(0));
                return old;
            });
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
            if (BasicResourceWarehouse.isBasicType(nameTypeEntry.getValue())) {
                //是常量类型
                Objects.requireNonNull(nameTypeEntry.getKey(), "常量类型的name不可为空！");
                //获取常量类型
                paramSupplier = () -> constant(nameTypeEntry.getKey());
            } else {
                //不是常量类，获取参数
                if (nameTypeEntry.getKey() != null) {
                    //有名称，通过名称获取
                    paramSupplier = () -> get(nameTypeEntry.getKey());
                } else {
                    //没有名称，通过类型获取
                    paramSupplier = () -> get(nameTypeEntry.getValue());
                }
            }
            params[i] = paramSupplier;
        }

        return () -> Arrays.stream(params).map(Supplier::get).toArray(Object[]::new);
    }


    /**
     * 获取依赖注入函数
     */
    private <T> Consumer<T> getInjectDependConsumer(Beans<T> beans){
        //查找所有的字段，取到所有存在@Depend注解的字段或方法
        Class<T> type = beans.getType();
        //获取全部字段, 根据注解获取, 转化为字段值注入函数
        Consumer<T>[] consumerArray = Arrays.stream(type.getDeclaredFields()).filter(f -> f.getAnnotation(com.forte.qqrobot.anno.depend.Depend.class) != null)
                //将字段转化为Supplier函数，以获取字段值
                .map(f -> {
                    com.forte.qqrobot.anno.depend.Depend dependAnnotation = f.getAnnotation(com.forte.qqrobot.anno.depend.Depend.class);
                    //字段名称
                    String name = dependAnnotation.value();
                    //字段类型
                    Class<?> fieldType = dependAnnotation.type().length == 0 ? f.getType() : dependAnnotation.type()[0];
                    //字段值的获取函数，获取的是Depend对象
                    Supplier<Depend> fieldGetter;
                    if (name.trim().length() == 0) {
                        //如果未指定字段名称，判断是否为常量类型，如果是，尝试获取字段名，否则使用类型注入
                        if (BasicResourceWarehouse.isBasicType(f.getType())) {
                            //是常量类型，通过变量名获取
                            Object constant = constant(f.getName());
                            fieldGetter = () -> constant == null ? null : (new BasicDepend(f.getName(), constant.getClass() ,constant));
                        } else {
                            //否则，是普通的类型，通过类型获取
                            fieldGetter = () -> getDepend(fieldType);
                        }
                    } else {
                        //指定了名称，直接获取
                        fieldGetter = () -> getDepend(name, fieldType);
                    }

                    //判断字段是否可以注入的函数
                    Function<T, Boolean> canInjFunction;
                    try {
                        canInjFunction = DependUtil.canInj(type, f, dependAnnotation);
                    } catch (NoSuchMethodException e) {
                        throw new DependResourceException("字段值获取函数构建异常！", e);
                    }

                    //赋值函数
                    Consumer<T> setterConsumer;
                    try {
                        setterConsumer = DependUtil.doInj(type, f, dependAnnotation, fieldGetter);
                    } catch (NoSuchMethodException e) {
                        throw new DependResourceException("字段值赋值函数构建异常！", e);
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
            //遍历全部字段值赋值函数并赋值
            for (Consumer<T> inj : consumerArray) {
                inj.accept(b);
            }
        };
    }


    /**
     * 通过方法获取可以注入的参数
     * @param method 方法对象
     * @param addParams 额外参数
     *                  当存在额外参数的时候，优先使用额外参数进行注入
     */
    public Object[] getMethodPrarmeters(Method method, Object[] addParams){
        Parameter[] parameters = method.getParameters();
        //优先从额外参数中获取，额外参数只能根据类型来区分
        //将额外类型转化为map集合
        Map<Class, Object> addParamsMaps = addParams == null ? Collections.emptyMap() : Arrays.stream(addParams).collect(Collectors.toMap(Object::getClass, o -> o));

        return Arrays.stream(parameters).map(p -> getParameter(p, addParamsMaps)).toArray();
    }

    /**
     * 通过方法获取可以注入的参数
     * @param method 方法对象
     */
    public Object[] getMethodPrarmeters(Method method){
        return getMethodPrarmeters(method, null);
    }


    /**
     * 将parameter转化为对应的bean
     */
    private Object getParameter(Parameter parameter, Map<Class, Object> addParamsMap){
        //获取注解
        com.forte.qqrobot.anno.depend.Depend dependAnnotation = parameter.getAnnotation(com.forte.qqrobot.anno.depend.Depend.class);

        //获取到的参数
        Object param;

            //判断是否存在注解
            if(dependAnnotation != null){
                //存在注解， 获取信息
                String name = dependAnnotation.value();
                if(name.trim().length() == 0){
                    //没有名称，优先使用额外参数获取，其次使用类型查询
                    Object addParamGet = addParamsMap.get(parameter.getType());
                    if(addParamGet != null){
                        param = addParamGet;
                    }else{
                        Class type = dependAnnotation.type().length == 0 ? parameter.getType() : dependAnnotation.type()[0];
                        param = get(type);
                    }
                }else{
                    //有名称，通过名称获取
                    param = get(name);
                }
            }else{
                //没有注解, 通过使用参数名称获取
                //先尝试通过额外参数获取
                Object addParamGet = addParamsMap.get(parameter.getType());
                if(addParamGet != null){
                    param = addParamGet;
                }else{
                    //通过获取参数名获取（如果能获取到的话）
                    //如果获取不到则使用类型查询
                    String name = paramNameGetter.getParameterName(parameter);
                    if(name != null){
                        param = get(name);
                    }else{
                        param = get(parameter.getType());
                    }
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
     * 通过类型获取依赖对象
     */
    public <T> Depend<T> getDepend(Class<T> type){
        List<Depend> depends = classResourceWareHouse.get(type);
        if (depends == null || depends.size() == 0) {
            return null;
        } else if (depends.size() > 1) {
            //多于一个
            throw new DependResourceException("存在不止一个[" + type + "]类型的依赖，请使用名称获取。");
        } else {
            return depends.get(0);
        }
    }

    public <T> Depend<T> getDepend(String name, Class<T> type){
        return getDepend(name);
    }


    /**
     * 通过名称获取依赖对象
     * @param name 名称
     */
    public Depend getDepend(String name){
        Depend<?> depend = nameResourceWarehouse.get(name);
        return depend == null ? null : depend;
    }

    /**
     * 获取常量值
     * 常量只能通过名称获取
     */
    public Object getConstant(String name) {
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
     * @throws ClassCastException
     */
    public <T> T getDependInstance(String name, Class<T> type) {
        return (T) getDependInstance(name);
    }


    /**
     * 通过名称获取依赖
     */
    public Object getDependInstance(String name) {
        Depend<?> depend = getDepend(name);
        return depend == null ? null : depend.getInstance();
    }

    /**
     * 通过类型获取依赖
     */
    public <T> T getDependInstance(Class<T> type) {
        Depend<T> get = getDepend(type);
        return get == null ? null : get.getInstance();

    }


    /**
     * 获取依赖
     */
    @Override
    public <T> T get(Class<T> type){
        //如果获取器不是自己，则优先使用获取器获取
        if(!dependGetter.equals(this)){
            T result = dependGetter.get(type);
            if(result != null){
                return result;
            }
        }

        if(BasicResourceWarehouse.isBasicType(type)){
            throw new DependResourceException("常量类型["+ type +"]只能通过指定名称获取！");
        }else{
            return getDependInstance(type);
        }
    }

    /**
     * 获取依赖
     */
    @Override
    public <T> T get(String name, Class<T> type){
        //如果获取器不是自己，则优先使用获取器获取
        if(!dependGetter.equals(this)){
            T result = dependGetter.get(name, type);
            if(result != null){
                return result;
            }
        }

        if(BasicResourceWarehouse.isBasicType(type)){
            return getConstant(name, type);
        }else{
            return getDependInstance(name, type);
        }
    }

    /**
     * 获取依赖，当使用名称获取时，以非常量类型依赖为准
     */
    @Override
    public Object get(String name){
        //如果获取器不是自己，则优先使用获取器获取
        if(!dependGetter.equals(this)){
            Object result = dependGetter.get(name);
            if(result != null){
                return result;
            }
        }

        Object dependInstance = getDependInstance(name);
        return dependInstance == null ? getConstant(name) : dependInstance;
    }

    /**
     * 获取常量值
     * 常量只能通过名称获取
     */
    @Override
    public Object constant(String name) {
        //如果获取类不是自己，则优先通过获取器获取
        if(!dependGetter.equals(this)){
            Object constant = dependGetter.constant(name);
            if(constant != null){
                return constant;
            }
        }

        return basicResourceWarehouse.get(name);
    }

    /**
     * 获取常量值，转化数据类型
     */
    @Override
    public <T> T constant(String name, Class<T> type) {
        return (T) constant(name);
    }

    /**
     * 查询三个池子中是否存在某名称的依赖
     */
    private boolean contains(String name){
        return (basicResourceWarehouse.get(name) != null)
                ||
                (nameResourceWarehouse.get(name) != null)
                ;
    }

    /**
     * 根据类型判断是否存在
     */
    private boolean contains(Class<?> type){
        //如果是基础数据类型，默认存在
        if(BasicResourceWarehouse.isBasicType(type)){
            return true;
        }else{
            return classResourceWareHouse.getOrDefault(type, Collections.emptyList()).size() > 0;
        }

    }



}
