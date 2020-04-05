package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.anno.Block;
import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Spare;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.BreakType;
import com.forte.qqrobot.depend.AdditionalDepends;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.listener.result.BasicResultParser;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.listener.result.ListenResultParser;
import com.forte.utils.basis.MD5Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 监听器方法封装类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/25 18:28
 * @since JDK1.8
 **/
public class ListenerMethod<T> implements Comparable<ListenerMethod> {

    //**************** 所有字段均不可改变 ****************//

    /** 为监听函数创建一个UUID */
    private final String UUID;

    /**
     * 监听函数实例对象的获取函数
     * */
    private final Supplier<T> listenerGetter;

    /**
     * 监听函数实例对象的获取函数，并提供额外参数
     */
    private final Function<DependGetter, T> listenerGetterWithAddition;

    /** 过滤器注解，如果没有则为null */
    private final Filter filter;

    /** filter注解对应的封装对象 */
    private final com.forte.qqrobot.anno.data.Filter filterData;

    /** 正则匹配值，提前解析好 */
    private final Pattern[] patternValue;

    private final Pattern[] patternCodeValue;
    private final Pattern[] patternGroupValue;
    private final Pattern[] patternBotValue;

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

    /** 排序索引 */
    private final int sort;

    /** 是否监听截断, 将invoke的结果传入，返回一个结果 */
    private final Predicate<Object> listenBreak;

    /** 是否插件截断，将invoke的结果传入，返回一个结果 */
    private final Predicate<Object> listenBreakPlugin;

    /** 结果转化器 */
    private final ListenResultParser resultParser;

    //**************** 以下为可以动态变更的值 ****************//
    
    
    /**
     * 全参数构造
     * @param listenerGetter 监听器对象实例获取函数
     * @param filter        过滤注解
     * @param blockFilter   阻塞过滤注解
     * @param spare         备用方法注解
     * @param method        方法本体
     * @param type          监听类型
     */
    private ListenerMethod(Supplier<T> listenerGetter,
                           Function<DependGetter, T> listenerGetterWithAddition,
                           Filter filter,
                           BlockFilter blockFilter,
                           Spare spare,
                           Block block,
                           Method method,
                           MsgGetTypes[] type,
                           int sort,
                           Predicate<Object> listenBreak,
                           Predicate<Object> listenBreakPlugin,
                           ListenResultParser resultParser,
                           String name
                           ) {
        this.listenerGetter = listenerGetter;
        this.listenerGetterWithAddition  = listenerGetterWithAddition;
        this.filter = filter;

        if(filter == null){
            this.filterData = null;
            Pattern[] emptyArray = new Pattern[0];
            this.patternValue = emptyArray;
            this.patternCodeValue = emptyArray;
            this.patternGroupValue = emptyArray;
            this.patternBotValue = emptyArray;
        }else{
            this.filterData = com.forte.qqrobot.anno.data.Filter.build(filter);
            this.patternValue = filterData.patternValue();
            this.patternCodeValue = filterData.patternCodeValue();
            this.patternGroupValue = filterData.patternGroupValue();
            this.patternBotValue = filterData.patternBotValue();
        }



        this.blockFilter = blockFilter;
        this.spare = spare;
        this.block = block;
        this.method = method;
        this.type = type;
        this.sort = sort;
        this.listenBreak = listenBreak;
        this.listenBreakPlugin = listenBreakPlugin;
        this.resultParser = resultParser;
        // UUID 使用 方法包路径 + (方法名 + 参数类型列表 | name)

        String id;

        if(name == null || name.trim().length() == 0){
            String s1 = Arrays.toString(method.getParameterTypes());
            String s = s1.substring(1, s1.length() - 1);
            String fromID = method.getDeclaringClass().getTypeName() + "#" + method.getName() + "("+ s +")";
            id = method.getName() + "#" + MD5Utils.toMD5(fromID);
        }else{
            id = name;
        }

        this.UUID = id;
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
     *
     *  反回值机制改变，或者通过增加一个动态参数来控制返回值。
     *
     * @param additionalDepends 可以提供的额外参数(动态参数)
     */
    ListenResult invoke(AdditionalDepends additionalDepends) throws Exception {
        Object invoke = null;
        Exception error = null;

        // 获取监听函数所在类实例
        // 考虑到系统优化，
        //  后期将监听函数的实例修改为单例，并且在获取监听函数实例的时候不再提供额外参数。
        // 2019/12/27 已修改

        T listener = getListener();

        //遍历参数类型数组, 进行参数注入
        //将参数注入单独提出
        //获取方法执行的参数
        Object[] args = null;
        try {
            args = BotRuntime.getRuntime().getDependCenter().getMethodParameters(method, additionalDepends);
        }catch (Exception e){
            error = e;
        }

        ListenResult result;

        // 如果在参数获取阶段就出现了异常，跳过执行
        if (error == null){
            // 捕获异常
            try {
                //执行方法
                invoke = method.invoke(listener, args);
            }catch (Exception e){
                // 出现异常，判定为执行失败
                if(e instanceof InvocationTargetException){
                    final Throwable targetException = ((InvocationTargetException) e).getTargetException();
                    if(targetException instanceof Exception){
                        error = (Exception) targetException;
                    }
                }
                if(error == null){
                    error = e;
                }
            }
        }


        // 根据返回值判断是否需要截断
        boolean toBreak = listenBreak.test(invoke);
        boolean toBreakPlugin = listenBreakPlugin.test(invoke);

        result = resultParser.parse(invoke, sort, toBreak, toBreakPlugin, error);

        return result;
    }

    /**
     * 使用ListenerMethod对象构建
     */
    static <T> ListenerMethodBuilder<T> build(Supplier<T> listenerGetter, Function<DependGetter, T> listenerGetterWithAddition, Method method, MsgGetTypes[] type){
        return new ListenerMethodBuilder<>(listenerGetter, listenerGetterWithAddition, method, type);
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
     */
    public String getBeanToString(){
        return listenerGetter.get().toString();
    }

    /**
     * 判断方法是否为某种监听类型
     * @param isTypes 某种监听类型
     */
    public boolean isType(MsgGetTypes isTypes){
        for (MsgGetTypes types : type) {
            if(types.equals(isTypes)){
                return true;
            }
        }
        return false;
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


    public Function<DependGetter, T> getListenerGetterWithAddition() {
        return listenerGetterWithAddition;
    }

    /**
     * 使用copy的对象
     */
    public MsgGetTypes[] getType() {
        return Arrays.copyOf(type, type.length);
    }

    public Predicate<Object> getListenBreak() {
        return listenBreak;
    }

    public Predicate<Object> getListenBreakPlugin() {
        return listenBreakPlugin;
    }

    public ListenResultParser getResultParser() {
        return resultParser;
    }

    public T getListener() {
        return listenerGetter.get();
    }

    public Supplier<T> getListenerGetter() {
        return listenerGetter;
    }

    public Method getMethod() {
        return method;
    }

    public Filter getFilter() {
        return filter;
    }



    /**
     * 得到的是原本的数组而非复制的
     * @return
     */
    public Pattern[] getPatternValue(){
        return patternValue;
    }
    public Pattern[] getPatternCodeValue() {
        return patternCodeValue;
    }

    public Pattern[] getPatternGroupValue() {
        return patternGroupValue;
    }
    public Pattern[] getPatternBotValue(){
        return patternBotValue;
    }

    public BlockFilter getBlockFilter() {
        return blockFilter;
    }

    public Spare getSpare() {
        return spare;
    }

    public int getSort() {
        return sort;
    }

    public String getUUID() {
        return UUID;
    }

    @Override
    public int compareTo(ListenerMethod o) {
        return Integer.compare(sort, o.sort);
    }


    public com.forte.qqrobot.anno.data.Filter getFilterData() {
        return filterData;
    }


    /**
     * 内部类，对象构建类
     */
    static class ListenerMethodBuilder<T> {
        /** 监听器对象实例，用于执行方法 */
        private final Supplier<T> listenerGetter;
        /** 提供额外参数来获取监听器实例 */
        private final Function<DependGetter, T> listenerGetterWithAddition;
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
        private int sort = 1;
        /** 是否监听截断，默认为false - 不截断 */
        private Predicate<Object> listenBreak = BreakType.ALWAYS_BREAK.getResultTest();
        /** 是否插件截断，默认为false - 不截断 */
        private Predicate<Object> listenBreakPlugin = BreakType.ALWAYS_BREAK.getResultTest();
        /** 结果转化器，默认为特殊转化器 */
        private ListenResultParser resultParser = BasicResultParser.getInstance();
        /** id, 默认使用内部自动创建 */
        private String id = "";
        /**
         * 构造
         */
        public ListenerMethodBuilder(Supplier<T> listenerGetter, Function<DependGetter, T> listenerGetterWithAddition, Method method, MsgGetTypes[] type) {
            this.listenerGetter = listenerGetter;
            this.listenerGetterWithAddition = listenerGetterWithAddition;
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

        public ListenerMethodBuilder sort(int sort){
            this.sort = sort;
            return this;
        }

        public ListenerMethodBuilder listenBreak(Predicate<Object> toBreak){
            this.listenBreak = toBreak;
            return this;
        }

        public ListenerMethodBuilder listenBreakPlugin(Predicate<Object> toBreakPlugin){
            this.listenBreakPlugin = toBreakPlugin;
            return this;
        }

        public ListenerMethodBuilder resultParser(ListenResultParser parser){
            this.resultParser = parser;
            return this;
        }

        public ListenerMethodBuilder id(String id){
            this.id = id;
            return this;
        }



        /**
         * 构建对象
         */
        public ListenerMethod build(){
            return new ListenerMethod<>(
                    listenerGetter,
                    listenerGetterWithAddition,
                    filter,
                    blockFilter,
                    spare,
                    block,
                    method,
                    type,
                    sort,
                    listenBreak,
                    listenBreakPlugin,
                    resultParser,
                    id
            );
        }


        //**************************************
        //*          equals & hashCode
        //**************************************


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ListenerMethodBuilder that = (ListenerMethodBuilder) o;
            return method.equals(that.method);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method);
        }
    }






}
