package com.forte.qqrobot.depend;

import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.depend.parameter.ParamGetterManager;
import com.forte.qqrobot.depend.parameter.ParamNameGetter;
import com.forte.qqrobot.exception.AnnotationException;
import com.forte.qqrobot.exception.DependResourceException;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.FieldUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Bean工厂，用于将扫描出来的所有标注了@Beans和@Factory注解的类转化为预注入的Beans对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BeansFactory {

    /** 参数名获取器 */
    private static final ParamNameGetter paramNameGetter = ParamGetterManager.getParamNameGetter();

    /**
     * 从Class列表中过滤并转化为Beans列表
     * 传入的calss默认认为全部可以转化为Beans对象，过滤判断交给调用方
     */
    public static List<Beans> getBeans(Class<?>... classCollection){
        return getBeans(null, classCollection);
    }

    /**
     * 从Class列表中过滤并转化为Beans列表
     * 传入的calss默认认为全部可以转化为Beans对象，过滤判断交给调用方
     */
    public static List<Beans> getBeans(com.forte.qqrobot.anno.depend.Beans beansAnno, Class<?>... classCollection) {
        //两个set，用于对于依赖的去重判断
        //名称绝不可以相同，当名称不同的时候，类是可以相同的
        Set<String> nameSet = new HashSet<>();
        //保存Beans集合
        List<Beans> beansList = new ArrayList<>();


        Arrays.stream(classCollection)
                //不再进行过滤，将过滤交给调用方
                //转化为Bean对象
                .map(c -> BeansFactory.toBeans(c, beansAnno))
                //将children也添加进来
                .flatMap(b -> Stream.concat(Stream.of(b), Stream.of(b.getChildren())))
                .forEach(b -> {
                    //当出现同样的beans的时候，抛出异常
                    //判断名称是否存在, 如果存在直接抛出异常
                    if(!nameSet.add(b.getName())){
                        //出现重复的Beans，查询并获取之前的此名称的Beans
                        Beans equalsBeans = beansList.stream().filter(be -> be.getName().equals(b.getName())).findAny().orElse(null);
                        //抛出异常
                        throw new DependResourceException("不可出现重复的name：["+ b.getName() +"]:\n" +
                                b + "\n" + equalsBeans);
                    }
                    //没有抛出异常，保存对应信息
                    beansList.add(b);
        });



        //将beans和children进行合并

        return beansList;
    }

    /**
     * 将一个类转化为Beans对象
     */
    private static <T> Beans<T> toBeans(Class<T> clazz){
        return toBeans(clazz, null);
    }

    /**
     * 将一个类转化为Beans对象
     */
    private static <T> Beans<T> toBeans(Class<T> clazz, com.forte.qqrobot.anno.depend.Beans beansAnno){
        //如果参数中不存在注解对象，则尝试获取类上的注解对象。获取此类上的@Beans注解
        if(beansAnno == null){
            beansAnno = clazz.getAnnotation(com.forte.qqrobot.anno.depend.Beans.class);
        }
        BeansData beansData;
        if(beansAnno == null){
            //不存在注解，使用默认值
            beansData = BeansData.getInstance();
        }else{
            //存在注解，通过注解获取参数
            beansData = BeansData.getInstance(beansAnno);
        }


        //准备参数
        //类型
        Class<T> type = clazz;
        //名称, 如果是类名，开头小写
        String name = beansData.value().trim().length() == 0 ?
                FieldUtils.headLower(type.getSimpleName()) :
                beansData.value();

        //是否为单例
        boolean single = beansData.single();

        //是否全部字段标注@Depend
        boolean allDepend = beansData.allDepend();

        //实例化需要的参数列表
        NameTypeEntry[] instanceNeed = null;

        //获取实例的函数
        Function<Object[], T> getInstanceFunction = null;

        //判断是否存在@Constr注解，如果存在，获取其中某一个
        Method constrMethod = AnnotationUtils.getConstrMethod(clazz);

        //@Constr注解是否存在
        if(constrMethod == null){
            //不存在, 尝试获取构造方法
            //设置参数：instanceNeed
            Class[] constructorParams = beansData.constructor();
            Constructor constructor;
            try {
                constructor = clazz.getConstructor(constructorParams);
                //通过构造函数的参数封装所需参数列表
                instanceNeed = getNameTypeArrayByParameters(constructor.getParameters());

                //构建实例化函数
                getInstanceFunction = args -> {
                    try {
                        return (T)constructor.newInstance(args);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new DependResourceException("["+ clazz +"]实例化错误", e);
                    }
                };

            } catch (NoSuchMethodException e) {
                String message = "无法获取["+ clazz +"]";
                if(constructorParams.length == 0){
                    message += "的无参构造";
                }else{
                    message += "的构造函数：" + Arrays.toString(constructorParams);
                }
                NoSuchMethodException firstNoSuchMethodEx = new NoSuchMethodException(message);
                //如果指定的为无参构造，且如果获取不到指定构造函数，查看此类全部的构造，假如只有一个构造，则使用此构造，否则抛出异常
                if(constructorParams.length == 0){
                    Constructor<?>[] constructors = clazz.getConstructors();
                    if(constructors.length == 1){
                        //如果仅存在一个构造, 使用这个构造函数
                        Constructor findConstructor = constructors[0];
                        //通过构造函数的参数封装所需参数列表
                        instanceNeed = getNameTypeArrayByParameters(findConstructor.getParameters());

                        //构建实例化函数
                        getInstanceFunction = args -> {
                            try {
                                return (T)findConstructor.newInstance(args);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e2) {
                                throw new DependResourceException("["+ clazz +"]实例化错误", e2);
                            }
                        };

                    }else{
                        throw new DependResourceException("存在不止一个构造函数，无法定位", firstNoSuchMethodEx);
                    }
                }
            }
        }else{
            //如果存在, 使用@Constr的方法获取实例
            //设置参数：instanceNeed
            instanceNeed = getNameTypeArrayByParameters(constrMethod.getParameters());
            //配置实例获取函数
            getInstanceFunction = args -> {
                try {
                    constrMethod.setAccessible(true);
                    T invoke = (T) constrMethod.invoke(null, args);
                    constrMethod.setAccessible(false);
                    return invoke;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new DependResourceException(name + " ["+ clazz +"]实例化错误", e);
                }
            };

        }

        //参数实例完成，获取children并封装对象
        Beans[] children = getChildren(type, name);

        //实例化需要的参数列表, 转为final类型
        final NameTypeEntry[] finalInstanceNeed = instanceNeed;
        //获取实例的函数，转为final类型
        final Function<Object[], T> finalGetInstanceFunction = getInstanceFunction;

        return new Beans<>(name, type, single, allDepend, finalInstanceNeed, finalGetInstanceFunction, children, beansData);
    }


    /**
     * 获取类级Beans下的Beans数组
     * 需要保证clazz是携带@Beans注解的类
     * @return
     */
    private static <T> Beans<?>[] getChildren(Class<T> clazz, String faName){
        //获取此类下所有标注了@Beans的方法并进行过滤
        //方法上的@Beans不可省略
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> {
            com.forte.qqrobot.anno.depend.Beans beanAnnotation = m.getAnnotation(com.forte.qqrobot.anno.depend.Beans.class);
            if(beanAnnotation != null){
                //如果存在此注解，判断内容：是否存在返回值
                if(m.getReturnType().equals(void.class)){
                    throw new AnnotationException(clazz, m, com.forte.qqrobot.anno.depend.Beans.class, "无返回值");
                }
                return true;
            }
            return false;

        }).map(m -> {
            //是否为静态
            final boolean isStatic = Modifier.isStatic(m.getModifiers());
            //注解信息
            com.forte.qqrobot.anno.depend.Beans beanAnnotation = m.getAnnotation(com.forte.qqrobot.anno.depend.Beans.class);
            //转化为Beans对象

            Class<?> type = m.getReturnType();
            //如果注解没有指定名称，从方法名中进行获取而不是从类中，如果是get或set开头，去除get、set并开头小写
            String name = beanAnnotation.value().trim().length()==0 ?
                    FieldUtils.getMethodNameWithoutGetterAndSetter(m) :
                    beanAnnotation.value();
            boolean single = beanAnnotation.single();
            boolean alldepend = beanAnnotation.allDepend();
            //获取此方法的参数列表
            NameTypeEntry[] instanceNeed = getNameTypeArrayByParameters(m.getParameters());
            //如果不是静态的，将父类对象作为所需参数放在第一位
            if(!isStatic){
                //在参数第一位放置为父类的nameType对象
                instanceNeed = Stream.concat(Arrays.stream(new NameTypeEntry[]{NameTypeEntry.getInstanceLower(faName, clazz)}) , Arrays.stream(instanceNeed)).toArray(NameTypeEntry[]::new);
            }

            //获取实例
            Function<Object[], ?> getInstanceFunction = args -> {
                Object father;
                Object[] params;
                //如果不是静态的，获取第一个父类对象，截取剩下的参数作为方法执行参数
                if(!isStatic){
                    father = args[0];
                    params = Arrays.stream(args).skip(1).toArray();
                }else{
                    father = null;
                    params = args;
                }

                try {
                    return m.invoke(father, params);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new DependResourceException(name + " ["+ type +"]实例化错误", e);
                }
            };

            return new Beans(name, type, single, alldepend, instanceNeed, getInstanceFunction, null, BeansData.getInstance(beanAnnotation));
        }).toArray(Beans[]::new);


    }


    /**
     * 根据参数数组获取name-entry对象数据
     */
    static NameTypeEntry[] getNameTypeArrayByParameters(Parameter[] parameters){
        return Arrays.stream(parameters).map(p -> {
            //判断参数上是否存在@Depend注解
            Depend dependAnnotation = p.getAnnotation(Depend.class);
            String pName = paramNameGetter.getParameterName(p);
            Class<?> pType = p.getType();
            if(dependAnnotation != null){
                //存在注解
                String paramName = dependAnnotation.value().trim().length() == 0 ? pName : dependAnnotation.value();
                Class<?> paramType = dependAnnotation.type().length == 0 ? pType : dependAnnotation.type()[0];
                return NameTypeEntry.getInstanceLower(paramName, paramType);
            }else{
                //没有注解，获取参数名和类型并封装
                return NameTypeEntry.getInstanceLower(pName, pType);
            }
        }).toArray(NameTypeEntry[]::new);
    }

}
