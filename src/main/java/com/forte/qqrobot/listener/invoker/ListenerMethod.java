package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.Block;
import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Spare;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.utils.FieldUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 监听器方法封装类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/25 18:28
 * @since JDK1.8
 **/
public class ListenerMethod {

    //**************** 所有字段均不可改变 ****************//

    /** 为监听函数创建一个UUID */
    private final String UUID;

    /** 监听器对象实例，用于执行方法 */
    private final Object listener;

    /** 过滤器注解，如果没有则为null */
    private final Filter filter;

    /** 阻塞过滤器注解，如果没有则为null */
    private final BlockFilter blockFilter;

    /** 默认方法注解，如果没有则为null */
    private final Spare spare;

    /** 阻塞注解，如果没有则为null */
    private final Block block;

    /** 方法本体 */
    private final Method method;

    /** 此方法所属的监听类型, 多种类型 */
    private final MsgGetTypes[] type;



    /**
     * 全参数构造
     * @param bean          方法所在实例对象
     * @param filter        过滤注解
     * @param blockFilter   阻塞过滤注解
     * @param spare         备用方法注解
     * @param method        方法本体
     * @param type          监听类型
     */
    private ListenerMethod(Object bean, Filter filter, BlockFilter blockFilter, Spare spare, Block block, Method method, MsgGetTypes[] type) {
        this.listener = bean;
        this.filter = filter;
        this.blockFilter = blockFilter;
        this.spare = spare;
        this.block = block;
        this.method = method;
        this.type = type;
        //生成一个UUID
        UUID = createUUIDString();
    }

    /**
     * 生成UUID
     */
    private static String createUUIDString(){
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }




    //**************************************
    //*      方法执行相关，方法权限仅包内
    //**************************************

    /**
     * 执行方法
     * 方法执行成功与否判断：
     *  - 如果返回值是Boolean或boolean类型，原样返回
     *  - 如果返回值为null，则认为执行失败
     *  - 如果为其他任意返回值，认为执行成功
     * @param giveArgs 可以提供的参数
     */
    boolean invoke(Set<Object> giveArgs) throws InvocationTargetException, IllegalAccessException {
        //获取方法的参数数组，根据数组顺序准备参数，如果没有的参数使用null
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];

        //遍历参数类型数组
        for (int i = 0; i < parameterTypes.length; i++) {
            //当前类型
            Class<?> type = parameterTypes[i];
            //从提供的数组中查找相同类型，理论上讲不会有相同类型，如果没有则使用null
            args[i] = giveArgs.stream().filter(p -> FieldUtils.isChild(p.getClass(), type)).findAny().orElse(null);
        }

        //执行方法
        Object invoke = method.invoke(listener, args);
        if(invoke == null){
            return false;
        }else if(invoke.getClass().equals(Boolean.class)){
            return (Boolean) invoke;
        }else if(invoke.getClass().equals(boolean.class)) {
            return (boolean) invoke;
        }else{
            return true;
        }

    }

    /**
     * 使用ListenerMethod对象构建
     */
    static ListenerMethodBuilder build(Object bean, Method method, MsgGetTypes[] type){
        return new ListenerMethodBuilder(bean, method, type);
    }


    //**************************************
    //*               获取、判断等方法
    //**************************************

    /**
     * 是否存在过滤器注解，如果存在则为true
     */
    public boolean hasFilter(){
        return filter != null;
    }

    /**
     * 是否存在阻塞过滤器，如果存在则为true
     */
    public boolean hasBlockFilter(){
        return blockFilter != null;
    }

    /**
     * 是否为备用方法，如果是则为true
     */
    public boolean isSpare(){
        return spare != null;
    }

    /**
     * 是否为普通方法，如果是则为true
     */
    public boolean isNormal(){
        return spare == null;
    }


    public Block getBlock() {
        return block;
    }

    /**
     * 获取方法本体的toString字符串
     */
    public String getMethodToString(){
        return method.getName() + "("+ Arrays.stream(method.getParameters()).map(p -> p.getType().getSimpleName()).collect(Collectors.joining(", ")) +")";
    }

    /**
     * 获取实例对象的toString字符串
     * @return
     */
    public String getBeanToString(){
        return listener.toString();
    }

    /**
     * 判断方法是否为某种监听类型
     * @param isTypes 某种监听类型
     */
    public boolean isType(MsgGetTypes isTypes){
        return type.equals(isTypes);
    }

    /**
     * 获取此方法的监听方法
     */
    public MsgGetTypes[] getTypes(){
        return this.type;
    }


    //**************************************
    //*         getter & setter
    //**************************************

    public Object getListener() {
        return listener;
    }

    public Method getMethod() {
        return method;
    }

    public Filter getFilter() {
        return filter;
    }

    public BlockFilter getBlockFilter() {
        return blockFilter;
    }

    public Spare getSpare() {
        return spare;
    }

    public String getUUID() {
        return UUID;
    }

    /**
     * 内部类，对象构建类
     */
    static class ListenerMethodBuilder{
        /** 监听器对象实例，用于执行方法 */
        private final Object listener;
        /** 方法本体 */
        private final Method method;
        /** 此方法所属的监听类型 */
        private final MsgGetTypes[] type;
        /** 过滤器注解，如果没有则为null */
        private Filter filter = null;
        /** 阻塞过滤器注解，如果没有则为null */
        private BlockFilter blockFilter = null;
        /** 默认方法注解，如果没有则为null */
        private Spare spare = null;
        /** 阻塞注解，如果没有则为null */
        private Block block = null;
        /**
         * 构造
         */
        public ListenerMethodBuilder(Object listener, Method method, MsgGetTypes[] type) {
            this.listener = listener;
            this.method = method;
            this.type = type;
        }

        public ListenerMethodBuilder filter(Filter filter){
            this.filter = filter;
            return this;
        }

        public ListenerMethodBuilder blockFilter(BlockFilter blockFilter){
            this.blockFilter = blockFilter;
            return this;
        }

        public ListenerMethodBuilder spare(Spare spare){
            this.spare = spare;
            return this;
        }

        public ListenerMethodBuilder block(Block block){
            this.block = block;
            return this;
        }


        /**
         * 构建对象
         */
        public ListenerMethod build(){
            return new ListenerMethod(listener, filter, blockFilter, spare, block, method, type);
        }


        //**************************************
        //*          equals & hashCode
        //**************************************


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListenerMethodBuilder that = (ListenerMethodBuilder) o;
            return method.equals(that.method);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method);
        }
    }






}
