package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.anno.*;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.listener.invoker.plug.ListenerPlug;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.MethodUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 监听函数扫描器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/26 10:33
 * @since JDK1.8
 **/
public class ListenerMethodScanner {

    /**
     * SocketListener接口的唯一方法名
     */
    private static final String SOCKET_LISTENER_METHOD_NAME = "onMessage";

    /**
     * 全部的监听函数set集合
     */
    private final Set<ListenerMethod> listenerMethodSet = new HashSet<>();

    /**
     * 传入一个可能是监听器对象的Class对象
     * 只对标注了@Beans的函数进行获取
     */
    public Set<ListenerMethod> scanner(Class<?> clazz, Object bean) {

        //v1.0 update
        //如果此类不存在@Bean函数，略过
        //v1.0.3 update
        //可以通过批量加载注入beans，所以现在@Beans应该不再是必须的了
//        if(clazz.getAnnotation(Beans.class) == null){
////            return result;
////        }

        //获取实例的函数
        Supplier listenerGetter;
        Function<DependGetter, ?> listenerGetterWithAddition;
        //获取类上的Beans注解
//        Beans beansAnnotation = clazz.getAnnotation(Beans.class);
        Beans beansAnnotation = AnnotationUtils.getBeansAnnotationIfListen(clazz);
        String name = beansAnnotation == null ? "" : beansAnnotation.value();
        if(name.trim().length() == 0){
            //如果没有指定名称，通过类型获取
            listenerGetter = () -> ResourceDispatchCenter.getDependCenter().get(clazz);
            listenerGetterWithAddition = add -> ResourceDispatchCenter.getDependCenter().get(clazz, add);

        }else{
            listenerGetter = () -> ResourceDispatchCenter.getDependCenter().get(name);
            listenerGetterWithAddition = add -> ResourceDispatchCenter.getDependCenter().get(name, add);
        }

        //先判断是不是实现了普通监听器接口
//        boolean isSocketListener = FieldUtils.isChild(clazz, SocketListener.class);

        //判断方法上有没有备用方法注解
        Spare spare = AnnotationUtils.getAnnotation(clazz, Spare.class);


        //**************** 以上为实现了接口的判断 ****************//
        Set<ListenerMethod> result = new HashSet<>(buildByNormal(clazz, spare, listenerGetter, listenerGetterWithAddition));


        //记录并返回结果
        listenerMethodSet.addAll(result);
        return result;
    }


    /**
     * 通过普通的注解的形式加载监听函数
     */
    private Set<ListenerMethod> buildByNormal(Class<?> clazz, Spare spare, Supplier listenerGetter, Function<DependGetter, ?> listenerGetterWithAddition) {
        boolean isSpare = (spare != null);
        //不使用else，两者不冲突
        //开始判断当前类, 判断类上是否有@Listen注解
        //判断类上可以存在的注解
//        Listen classListen = clazz.getAnnotation(Listen.class);
//        Block classBlock = clazz.getAnnotation(Block.class);
        Listen classListen = AnnotationUtils.getAnnotation(clazz, Listen.class);
        Block classBlock = AnnotationUtils.getAnnotation(clazz, Block.class);


        //提前准备方法获取过滤器
        Predicate<Method> getFilter;

        //创建过滤器，排除有忽略注解的方法和静态方法
        if(classListen != null){
            //如果存在监听, 获取所有公共方法
//            getFilter = m -> !MethodUtil.isStatic(m) && (m.getAnnotation(Ignore.class) == null);
            getFilter = m -> !MethodUtil.isStatic(m) && (AnnotationUtils.getAnnotation(m, Ignore.class) == null);
        }else{
            //如果不存在，获取有@Listen注解的公共方法
            getFilter = m -> !MethodUtil.isStatic(m) && (AnnotationUtils.getAnnotation(m, Ignore.class) == null) && AnnotationUtils.getAnnotation(m, Listen.class) != null;
        }

        //参数获取类型
        MsgGetTypes[] msgGetTypes = Optional.ofNullable(classListen).map(Listen::value).orElse(null);
        //方法集合, 排除静态方法
        Method[] publicMethods = Arrays.stream(MethodUtil.getPublicMethods(clazz, getFilter)).toArray(Method[]::new);

        //遍历并构建ListenerMethod对象
        return Arrays.stream(publicMethods).map(method -> {

            //获取方法上的@Listen注解
//            Listen methodListen = method.getAnnotation(Listen.class);
            Listen methodListen = AnnotationUtils.getAnnotation(method, Listen.class);

            //如果类上没有注解，方法上也没有，则跳过此方法
            if (classListen == null && methodListen == null) {
                return null;
            }

            //如果没有被跳过，说明此方法可以被添加
            //尝试获取实例对象

            //获取需要的注解
//            Filter filter = method.getAnnotation(Filter.class);
//            BlockFilter blockFilter = method.getAnnotation(BlockFilter.class);
            Filter filter = AnnotationUtils.getAnnotation(method, Filter.class);
            BlockFilter blockFilter = AnnotationUtils.getAnnotation(method, BlockFilter.class);
            //获取类上的阻断注解
//            Block block = method.getAnnotation(Block.class);
            Block block = AnnotationUtils.getAnnotation(method, Block.class);
            //如果方法上没有阻断注解，则使用类上注解，如果方法上存在则使用方法上的注解,此时当不存在的时候直接使用null即可
            block = (block == null) ? classBlock : block;

            //如果是全局备用，则直接备用，如果没有全局备用，获取此方法的Spare注解
//            Spare thisSpare = isSpare ? spare : method.getAnnotation(Spare.class);
            Spare thisSpare = isSpare ? spare : AnnotationUtils.getAnnotation(method, Spare.class);
            //监听类型
            MsgGetTypes[] msgGetType = Optional.ofNullable(methodListen).map(Listen::value).orElse(msgGetTypes);


            //构建对象并添加
            return build(listenerGetter, listenerGetterWithAddition, method, msgGetType, thisSpare, filter, blockFilter, block);
        }).filter(Objects::nonNull).collect(Collectors.toSet());

    }




    /**
     * 没有实例对象的方法扫描
     * @param clazz class对象
     * @return 扫描结果
     * @throws Exception
     */
    public Set<ListenerMethod> scanner(Class<?> clazz) {
        return scanner(clazz, null);
    }

    /**
     * 有初始实例对象的方法扫描
     * @param bean  初始实例对象
     * @return 扫描结果
     * @throws Exception
     */
    public Set<ListenerMethod> scanner(Object bean) {
        return scanner(bean.getClass(), bean);
    }


    /**
     * 构建监听函数管理器实例
     * @return 监听函数管理器实例
     */
    public ListenerManager buildManager(){
        return new ListenerManager(listenerMethodSet);
    }

    /**
     * 构建监听函数阻断器
     * @return 监听函数阻断器
     */
    public Plug buildPlug(){
        return new ListenerPlug(listenerMethodSet);
    }

    /**
     * 构建ListenerMethod对象
     * @param listenerGetter 监听器实例获取函数
     * @param method    方法本体
     * @param types     监听类型
     * @param spare         Spare注解
     * @param filter        filter注解
     * @param blockFilter   blockFilter注解
     * @return  ListenerMethod实例对象
     */
    private ListenerMethod build(Supplier listenerGetter, Function<DependGetter, ?> listenerGetterWithAddition, Method method, MsgGetTypes[] types, Spare spare, Filter filter, BlockFilter blockFilter, Block block){
        return ListenerMethod.build(listenerGetter, listenerGetterWithAddition, method, types)
                             .spare(spare)
                             .filter(filter)
                             .blockFilter(blockFilter)
                             .block(block)
                             .build();
    }

    /**
     * 尝试获取实例对象
     *
     * v1.0 update
     * 使用依赖获取方式获取实例对象
     *
     * @param clazz clazz对象
     * @param bean  可能存在的实例对象
     * @return
     */
    @Deprecated
    private Object getBean(Class clazz, Object bean) throws IllegalAccessException, InstantiationException {
        //如果参数的bean不为null，直接赋值
        if(bean != null){
           return bean;
        }else{
            //如果参数的bean为null，说明没有实例对象
            //尝试实例化,先查询是存在@Constr注解的静态方法，返回值应与Clazz相同
            Method[] constrs = MethodUtil.getMethods(clazz, m -> MethodUtil.isStatic(m) &&
                    (m.getReturnType().equals(clazz)) && (AnnotationUtils.getAnnotation(m, Constr.class) != null));

            //如果存在，遍历执行，直到执行成功，否则抛出异常
            if(constrs.length > 0){
                for (int i = 0; i < constrs.length; i++) {
                    try{
                        Method constr = constrs[0];
                        //判断参数数量, 如果存在参数则全部使用null
                        Object[] args = new Object[constr.getParameterCount()];
                        return args.length > 0 ? constr.invoke(null, args) : constr.invoke(null);
                    }catch (Exception ignored){ }
                }
                throw new IllegalAccessException();
            }else{
                //如果不存在@Constr注解的静态方法，尝试使用无参构造
                return clazz.newInstance();
            }
        }
    }


}
