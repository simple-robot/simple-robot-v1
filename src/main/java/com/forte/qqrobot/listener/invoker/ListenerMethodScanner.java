package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.*;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.utils.FieldUtils;
import com.forte.qqrobot.utils.MethodUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 监听函数扫描器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/26 10:33
 * @since JDK1.8
 **/
public class ListenerMethodScanner {

    /**
     * SocketListener接口的
     */
    private static final String SOCKET_LISTENER_METHOD_NAME = "onMessage";

    /**
     * 全部的监听函数set集合
     */
    private final Set<ListenerMethod> listenerMethodSet = new HashSet<>();

    /**
     * 传入一个可能是监听器对象的Class对象
     * @return
     */
    public Set<ListenerMethod> scanner(Class<?> clazz, Object bean) throws Exception {
        Set<ListenerMethod> result = new HashSet<>();


        //先判断是不是实现了普通监听器接口
        boolean isSocketListener = FieldUtils.isChild(clazz, SocketListener.class);

        //判断方法上有没有备用方法注解
        Spare spare = clazz.getAnnotation(Spare.class);


        //如果是普通监听器，则认为此方法中全部公共的onMessage都有可能为监听器方法，获取全部onMessage方法
        //判断条件：方法名为onMessage且第一个参数的类型是MsgGet
        if(isSocketListener){
            result.addAll(buildBySocketListener(clazz, bean, spare));
        }

        //**************** 以上为实现了接口的判断 ****************//
        result.addAll(buildByNormal(clazz, bean, spare));


        //记录并返回结果
        listenerMethodSet.addAll(result);
        return result;
    }


    // TODO 方法分离
    private Set<ListenerMethod> buildBySocketListener(Class<?> clazz, Object bean, Spare spare) throws InstantiationException, IllegalAccessException {
        //尝试获取实例对象
        Object obj = getBean(clazz, bean);
        boolean isSpare = (spare != null);

        Method[] methods = MethodUtil.getPublicMethods(clazz, m -> m.getName().equals(SOCKET_LISTENER_METHOD_NAME) && FieldUtils.isChild(m.getParameterTypes()[0], MsgGet.class));

        //遍历，根据第一个参数判断函数的监听类型并封装
        Set<ListenerMethod> socketListenerCollection = Arrays.stream(methods)
                .map(m -> {
                    Class<MsgGet> msgGetClass = (Class<MsgGet>) m.getParameterTypes()[0];
                    //监听器实现来的函数仅会有一个监听类型
                    MsgGetTypes singleType = MsgGetTypes.getByType(msgGetClass);
                    MsgGetTypes[] byType = new MsgGetTypes[]{singleType};
                    if(singleType != null){
                        //如果不是未知的, 则认定其是正确的onMessage对象，开始封装
                        //获取其他注解
                        Filter filter = m.getAnnotation(Filter.class);
                        BlockFilter blockFilter = m.getAnnotation(BlockFilter.class);
                        Block block = m.getAnnotation(Block.class);
                        //尝试获取实例对象
                        if (isSpare) {
                            //添加
                            return build(obj, m, byType, spare, filter, blockFilter, block);
                        } else {
                            //如果没有类上的Spare注解，则方法单独获取
                            Spare thisSpare = m.getAnnotation(Spare.class);
                            return build(obj, m, byType, thisSpare, filter, blockFilter, block);
                        }
                    }else{
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        //添加所有
        return socketListenerCollection;
    }


    private Set<ListenerMethod> buildByNormal(Class<?> clazz, Object bean, Spare spare) throws IllegalAccessException {
        boolean isSpare = (spare != null);
        //不使用else，两者不冲突
        //开始判断当前类, 判断类上是否有@Listen注解
        //判断类上可以存在的注解
        Listen classListen = clazz.getAnnotation(Listen.class);
        Block classBlock = clazz.getAnnotation(Block.class);



        //提前准备方法获取过滤器
        Predicate<Method> getFilter;

        //创建过滤器，排除有忽略注解的方法和静态方法
        if(classListen != null){
            //如果存在监听, 获取所有公共方法
            getFilter = m -> !MethodUtil.isStatic(m) && (m.getAnnotation(Ignore.class) == null);
        }else{
            //如果不存在，获取有@Listen注解的公共方法
            getFilter = m -> !MethodUtil.isStatic(m) && (m.getAnnotation(Ignore.class) == null) && m.getAnnotation(Listen.class) != null;
        }

        //参数获取类型
        MsgGetTypes[] msgGetTypes = Optional.ofNullable(classListen).map(Listen::value).orElse(null);
        //方法集合, 排除静态方法，
        Method[] publicMethods = Arrays.stream(MethodUtil.getPublicMethods(clazz, getFilter)).toArray(Method[]::new);

        //遍历并构建ListenerMethod对象
        return Arrays.stream(publicMethods).map(method -> {

            //获取方法上的@Listen注解
            Listen methodListen = method.getAnnotation(Listen.class);

            //如果类上没有注解，方法上也没有，则跳过此方法
            if (classListen == null && methodListen == null) {
                return null;
            }

            //如果没有被跳过，说明此方法可以被添加
            //尝试获取实例对象
            Object obj;
            try {
                obj = getBean(clazz, bean);
            } catch (Exception e) {
                throw new RuntimeException(new IllegalAccessException("无法为[" + clazz + "]创建实例对象"));
            }

            //获取需要的注解
            Filter filter = method.getAnnotation(Filter.class);
            BlockFilter blockFilter = method.getAnnotation(BlockFilter.class);
            Block block = method.getAnnotation(Block.class);
            //如果是全局备用，则直接备用，如果没有全局备用，获取此方法的Spare注解
            Spare thisSpare = isSpare ? spare : method.getAnnotation(Spare.class);
            //监听类型
            MsgGetTypes[] msgGetType = Optional.ofNullable(methodListen).map(Listen::value).orElse(msgGetTypes);
            //构建对象并添加
            return build(obj, method, msgGetType, thisSpare, filter, blockFilter, block);
        }).filter(Objects::nonNull).collect(Collectors.toSet());

    }




    /**
     * 没有实例对象的方法扫描
     * @param clazz class对象
     * @return 扫描结果
     * @throws Exception
     */
    public Set<ListenerMethod> scanner(Class<?> clazz) throws Exception {
        return scanner(clazz, null);
    }

    /**
     * 有初始实例对象的方法扫描
     * @param bean  初始实例对象
     * @return 扫描结果
     * @throws Exception
     */
    public Set<ListenerMethod> scanner(Object bean) throws Exception {
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
    public ListenerPlug buildPlug(){
        return new ListenerPlug(listenerMethodSet);
    }

    /**
     * 构建ListenerMethod对象
     * @param obj       方法执行用的实例对象
     * @param method    方法本体
     * @param types     监听类型
     * @param spare         Spare注解
     * @param filter        filter注解
     * @param blockFilter   blockFilter注解
     * @return  ListenerMethod实例对象
     */
    private ListenerMethod build(Object obj, Method method, MsgGetTypes[] types, Spare spare, Filter filter, BlockFilter blockFilter, Block block){
        return ListenerMethod.build(obj, method, types)
                             .spare(spare)
                             .filter(filter)
                             .blockFilter(blockFilter)
                             .block(block)
                             .build();
    }

    /**
     * 尝试获取实例对象
     * @param clazz clazz对象
     * @param bean  可能存在的实例对象
     * @return
     */
    private Object getBean(Class clazz, Object bean) throws IllegalAccessException, InstantiationException {
        //如果参数的bean不为null，直接赋值
        if(bean != null){
           return bean;
        }else{
            //如果参数的bean为null，说明没有实例对象
            //尝试实例化,先查询是存在@Constr注解的静态方法，返回值应与Clazz相同
            Method[] constrs = MethodUtil.getMethods(clazz, m -> MethodUtil.isStatic(m) && (m.getReturnType().equals(clazz)) && (m.getAnnotation(Constr.class) != null));

            //如果存在，遍历执行，直到执行成功
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
