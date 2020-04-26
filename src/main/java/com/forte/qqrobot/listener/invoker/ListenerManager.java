package com.forte.qqrobot.listener.invoker;


import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.AdditionalDepends;
import com.forte.qqrobot.exception.ListenerException;
import com.forte.qqrobot.exception.NoSuchExceptionHandleException;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.ListenContext;
import com.forte.qqrobot.listener.MsgGetContext;
import com.forte.qqrobot.listener.MsgIntercept;
import com.forte.qqrobot.listener.error.ExceptionHandleContextImpl;
import com.forte.qqrobot.listener.error.ExceptionProcessCenter;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 监听函数管理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/26 16:23
 * @since JDK1.8
 **/
public class ListenerManager implements MsgReceiver {

    /**
     * 保存全部监听函数并进行两层分类
     * 第一层，按照接收的消息类型分类
     * 第二层，按照是否为普通函数分类
     * 理论上，这个Map的内容值将不能再变更，否则将会出现排序上的混乱。
     * 但如如果添加后再进行排序，则又会降低效率。
     */
    private final Map<MsgGetTypes, Map<Boolean, List<ListenerMethod>>> LISTENER_METHOD_MAP;

    /**
     * 空的list，用于应对空指针异常
     */
    private static final List<ListenerMethod> EMPTY_LIST = Collections.emptyList();

    /**
     * 空的map, 用于应对从监听函数获取的时候出现空的情况
     */
    private static final Map<Boolean, List<ListenerMethod>> EMPTY_MAP = new HashMap<Boolean, List<ListenerMethod>>(){{
        put(true , Collections.emptyList());
        put(false, Collections.emptyList());
    }};


    /**
     * 拦截器列表
     */
    private MsgIntercept[] intercepts;

    /**
     * 每一个manager对象内部维护一个用于msg上下文对象的全局map对象，用于初始化MsgContext
     * <br>
     * 由于全局Map可能会出现线程问题，故此使用线程安全的Map
     */
    private Map<String, Object> msgContextGlobalMap = new ConcurrentHashMap<>(2);

    
    /**
     * 每一个manager对象内部维护一个用于listen上下文对象的全局map对象，用于初始化ListenContext
     * <br>
     * 由于全局Map可能会出现线程问题，故此使用线程安全的Map
     */
    private Map<String, Object> listenContextGlobalMap = new ConcurrentHashMap<>(2);

    /**
     * 空的监听回执列表
     */
    private static final ListenResult[] EMPTY_RESULT = new ListenResult[0];

    /**
     * Bot管理器，用于筛选那些没有被注册（即获取不到的）bot的消息
     */
    private BotManager botManager;

    /**
     * 异常处理中心
     */
    private ExceptionProcessCenter exceptionProcessCenter;

    /**
     * 维护一个异常处理器所使用的全局map
     */
    private Map<String, Object> exceptionContextGlobalMap = new ConcurrentHashMap<>(2);

    /**
     * 是否验证bot
     */
    private boolean checkBot;

    /**
     * 接收到了消息
     */
    @Override
    public ListenResult[] onMsg(MsgGet msgget, SenderSendList sender, SenderSetList setter, SenderGetList getter){
        // 判断是否需要检测bot信息
        if(checkBot){
            // 首先对bot进行判断
            String thisCode = msgget.getThisCode();
            // 如果thisCode为null，则可能代表组件不支持多bot的区分
            if(thisCode != null && botManager.getBot(thisCode) == null){
                QQLog.error("listener.bot.noVerify", thisCode);
                return EMPTY_RESULT;
            }
        }

        // 构建一个监听函数上下文对象
        ListenContext context = ListenContext.getInstance(listenContextGlobalMap);

        // 消息拦截
        // 构建上下文对象
        MsgGetContext msgContext = new MsgGetContext(msgget, context, sender, setter, getter, msgContextGlobalMap);
        // 遍历所有的消息拦截器
        for (MsgIntercept intercept : intercepts) {
            if(!intercept.intercept(msgContext)){
                // 如果出现返回值false，返回一个空数组
                return EMPTY_RESULT;
            }
        }

        // 拦截结束，重新赋值MsgGet
        msgget = msgContext.getMsgGet();

        //对外接口，表示接收到了消息, 对消息进行监听分配
        //组装参数，此参数保证全部类型全部唯一, 且参数索引2的位置为是否被at
        Object[] params = getParams(msgget);
        AtDetection at = (AtDetection) params[2];

        //为消息分配监听函数
        return invoke(msgget, context, params, at, sender, setter, getter);
    }


    /**
     * 接收到了消息响应
     * @param msgGet    接收的消息
     * @param args      参数列表
     * @param at        是否被at
     * @return 执行的结果集，已经排序了。
     */
    private ListenResult[] invoke(MsgGet msgGet, ListenContext context, Set<Object> args, AtDetection at,
                                  SenderSendList sendList , SenderSetList setList, SenderGetList getList){

        //参数获取getter
        Function<ListenerMethod, AdditionalDepends> paramGetter = buildParamGetter(msgGet, args, at, sendList, setList, getList, context);
        //获取消息类型
        MsgGetTypes type = MsgGetTypes.getByType(msgGet.getClass());


        //先查看是否存在阻断函数，如果存在阻断函数则执行仅执行阻断函数
        //获取阻断器
        Plug plug = ResourceDispatchCenter.getPlug();
        Set<ListenerMethod> blockMethod = plug.getBlockMethod(type);
        if(blockMethod != null){
            //如果存在阻断，执行阻断
            return invokeBlock(blockMethod, paramGetter, msgGet, at, context);
//            ListenResult<Object> blockResult = ListenResultImpl.result(1, null, true, false, false, null);
//            return new ListenResult[]{blockResult};
        }else{
            //如果不存在阻断，正常执行
            //先执行普通监听函数

            Map.Entry<Integer, List<ListenResult>> normalIntResult = invokeNormal(type, paramGetter, msgGet, at, context);
            int invokeNum = normalIntResult.getKey();

            //如果没有普通监听函数执行成功，则尝试执行备用监听函数
            if(invokeNum <= 0){
                Map.Entry<Integer, List<ListenResult>> spareIntList = invokeSpare(type, paramGetter, msgGet, at, context);
                normalIntResult.getValue().addAll(spareIntList.getValue());
            }
            // 将结果转化为数组并返回
            return normalIntResult.getValue().toArray(new ListenResult[0]);
        }
    }

    /**
     * 接收到了消息响应
     * @param msgGet    接收的消息
     * @param args      参数列表
     * @param at        是否被at
     */
    private ListenResult[] invoke(MsgGet msgGet, ListenContext context, Object[] args, AtDetection at, SenderSendList sendList , SenderSetList setList, SenderGetList getList){
        return invoke(msgGet, context, Arrays.stream(args).collect(Collectors.toSet()), at, sendList, setList, getList);
    }

    /**
     * 执行阻断函数
     * @param blockMethod   阻断函数列表
     * @param paramGetter   参数获取函数
     * @param msgGet        接收到的消息
     * @param at            是否被at
     */
    private ListenResult[] invokeBlock(Set<ListenerMethod> blockMethod, Function<ListenerMethod, AdditionalDepends> paramGetter,
                             MsgGet msgGet, AtDetection at, ListenContext context){
        //过滤
        //获取过滤器
        ListenerFilter filter = ResourceDispatchCenter.getListenerFilter();
        return blockMethod.stream().filter(lisM -> filter.blockFilter(lisM, msgGet, at, context)).map(lisM -> {
            //剩下的为过滤好的监听函数
            AdditionalDepends addition = null;
            try{
                //执行函数
                addition = paramGetter.apply(lisM);
                addition.put("context", context);
                return lisM.invoke(addition);
            }catch (Exception e){
                MsgSender sender = addition == null ? null : addition.get(MsgSender.class);
                final ExceptionHandleContextImpl<Exception> exContext =
                        new ExceptionHandleContextImpl<>(lisM.getUUID(), msgGet, lisM.getSort(), sender, exceptionContextGlobalMap, context, e);
                try {
                    return exceptionProcessCenter.doHandleResult(e, exContext);
                } catch (NoSuchExceptionHandleException ex) {
                    QQLog.error("阻断函数["+ lisM.getBeanToString() +"]执行函数["+ lisM.getMethodToString() +"]出现错误！", e);
                    return null;
                }
            } catch (Throwable throwable) {
                QQLog.error("阻断函数["+ lisM.getBeanToString() +"]执行函数["+ lisM.getMethodToString() +"]出现错误！", throwable);
                return null;
            }
        }).filter(Objects::nonNull).toArray(ListenResult[]::new);
    }


    /**
     * 组装可以提供给监听函数的基础参数，其中需要保证：
     * 所有参数的数据类型不相同
     * 参数索引为2的位置上为是否被 at 的信息
     * @param msgGet 消息接口
     * @return 参数集合
     */
    private Object[] getParams(MsgGet msgGet){
        List<Object> plist = new ArrayList<>();
        String msg = msgGet.getMsg();
        // 获取当前接收到消息的Code
        String code = msgGet.getThisCode();
        //配置参数
        //获取cqCodeUtil
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();
        //判断是否at自己
        //获取本机QQ号
        // 获取AT判断函数
        final Function<MsgGet, AtDetection> atDetectionFunction = ListenerFilter.getAtDetectionFunction();
        AtDetection atDetection = atDetectionFunction.apply(msgGet);
        //0 : msgGet
        plist.add(msgGet);
        //1 : codeUtil
        plist.add(cqCodeUtil);
        //2 : atDetection
        plist.add(atDetection);
        //组装参数
        //* 组装参数不再携带QQWebSocketSender对象和QQHttpSender对象，而是交给Manager动态创建
        if(msgGet.getThisCode() != null){
            BotInfo bot = BotRuntime.getRuntime().getBotManager().getBot(msgGet.getThisCode());
            plist.add(bot);
        }
        return plist.toArray(new Object[0]);
    }

    /**
     * 构建参数获取getter
     * 额外参数作为 {@link AdditionalDepends} 类进行封装
     */
    private Function<ListenerMethod, AdditionalDepends> buildParamGetter(MsgGet msgGet,
                                                                         Set<Object> args,
                                                                         AtDetection at,
                                                                         SenderSendList sendList ,
                                                                         SenderSetList setList,
                                                                         SenderGetList getList,
                                                                         ListenContext ListenContext

    ){
        //增加参数:MsgGetTypes
        MsgGetTypes msgType = MsgGetTypes.getByType(msgGet.getClass());

        //参数获取getter
        return lm -> {
            Map<String, Object> map = new HashMap<>(16);
            map.put("msgGet", msgGet);
            map.put(msgGet.getClass().getSimpleName(), msgGet);
            map.put("atDetection", at);
            map.put("msgType", msgType);
            map.put(msgType.toString(), msgType);
            MsgSender msgSender = MsgSender.build(sendList, setList, getList, lm, BotRuntime.getRuntime());
            map.put("msgSender", msgSender);
            map.put("context", ListenContext);
            //将整合的送信器与原生sender都传入
            if(sendList != null){
                map.put("sender", sendList);
            }
            if(setList != null){
                map.put("setter", setList);
            }
            if(getList != null){
                map.put("getter", getList);
            }

            for (Object arg : args) {
                if(arg != null){
                    map.put(arg.getClass().getSimpleName(), arg);
                }
            }

            return AdditionalDepends.getInstance(map);
        };
    }

    /**
     * 执行默认监听函数
     * @param msgGetTypes   消息类型
     * @param paramGetter   获取参数集合的函数
     * @param msgGet        接收的消息
     * @param at            是否被at
     * @return              执行成功函数数量 & 结果集合(排过序的)
     */
    private Map.Entry<Integer, List<ListenResult>> invokeNormal(MsgGetTypes msgGetTypes, Function<ListenerMethod, AdditionalDepends> paramGetter,
                                                                MsgGet msgGet, AtDetection at, ListenContext context){
        //执行过的方法数量
        AtomicInteger count = new AtomicInteger(0);
        //执行结果集合
        List<ListenResult> results = new ArrayList<>();

        //获取监听函数过滤器
        ListenerFilter listenerFilter = ResourceDispatchCenter.getListenerFilter();

        //获取这个消息分类下的普通方法
        List<ListenerMethod> normalMethods = getNormalMethods(msgGetTypes);

        // 这个first就是第一个出现的ListenBreak。但是，没啥用
        Optional<ListenResult> first = normalMethods.stream()
                // 先过滤掉不符合条件的函数
                .filter(lm -> listenerFilter.filter(lm, msgGet, at, context))
                // 在根据是否截断进行过滤，当出现了第一个截断返回值的时候停止执行
                // 通过filter与findFirst组合使用来实现。
                .map(lm -> {
                    try {
                        final AdditionalDepends additionalDepends = paramGetter.apply(lm);
                        ListenResult result = lm.invoke(additionalDepends);
                        // 如果有异常，處理或输出这个异常
                        Exception error = result.getError();
                        if(error != null){
                            MsgSender sender = additionalDepends.get(MsgSender.class);
                            final ExceptionHandleContextImpl<Exception> exContext =
                                    new ExceptionHandleContextImpl<>(lm.getUUID(), msgGet, lm.getSort(), sender, exceptionContextGlobalMap, context, error);
                            try {
                                result = exceptionProcessCenter.doHandleResult(error, exContext);
                            }catch (NoSuchExceptionHandleException e){
                                QQLog.error("listener.run.error", error, lm.getUUID(), error.getLocalizedMessage());
                            }
                        }
                        results.add(result);
                        // 如果执行成功，计数+1
                        if(result.isSuccess()){
                            count.addAndGet(1);
                        }
                        return result;
                    } catch (Throwable e) {
                        // invoke里已经对方法的执行做了处理，如果还是会出错则代表是其他步骤出现了异常。
                       throw new RobotRuntimeException(e);
                    }
                })
                .filter(ListenResult::isToBreak)
                .findFirst();

        // 对结果集进行排序
        Collections.sort(results);
        return new AbstractMap.SimpleEntry<>(count.get(), results);
    }


    /**
     * 执行备用监听函数
     * @param msgGetTypes   消息类型
     * @param paramGetter          获取参数集合的方法
     * @param msgGet        接收的消息
     * @param at            是否被at
     */
    private Map.Entry<Integer, List<ListenResult>> invokeSpare(MsgGetTypes msgGetTypes, Function<ListenerMethod, AdditionalDepends> paramGetter,
                                                               MsgGet msgGet, AtDetection at, ListenContext context){
        //执行过的方法数量
        AtomicInteger count = new AtomicInteger(0);

        //执行结果集合
        List<ListenResult> results = new ArrayList<>();

        //获取监听函数过滤器
        ListenerFilter listenerFilter = ResourceDispatchCenter.getListenerFilter();

        //获取这个消息分类下的备用方法
        List<ListenerMethod> spareMethods = getSpareMethods(msgGetTypes);

        // 这个first就是第一个出现的break。但是，没啥用
        Optional<ListenResult> first = spareMethods.stream()
                // 先过滤掉不符合条件的函数
                .filter(lm -> listenerFilter.filter(lm, msgGet, at, context))
                // 通过filter与findFirst组合使用来实现。
                .map(lm -> {
                    try {
                        final AdditionalDepends additionalDepends = paramGetter.apply(lm);
                        ListenResult result = lm.invoke(additionalDepends);
                        // 如果有异常，输出这个异常
                        Exception error = result.getError();
                        if(error != null){
                            MsgSender sender = additionalDepends.get(MsgSender.class);
                            final ExceptionHandleContextImpl<Exception> exContext =
                                    new ExceptionHandleContextImpl<>(lm.getUUID(), msgGet, lm.getSort(), sender, exceptionContextGlobalMap, context, error);
                            try {
                                result = exceptionProcessCenter.doHandleResult(error, exContext);
                            }catch (NoSuchExceptionHandleException e){
                                QQLog.error("listener.run.error", error, lm.getUUID(), error.getLocalizedMessage());
                            }
                        }
                        results.add(result);
                        // 如果执行成功，计数+1
                        if(result.isSuccess()){
                            count.addAndGet(1);
                        }
                        return result;
                    } catch (Throwable e) {
                        // 如果出现异常，暂时先抛出，后期使用异常管理
                        throw new RobotRuntimeException(e);
                    }

                })
                .filter(ListenResult::isToBreak)
                .findFirst();

        Collections.sort(results);
        return new AbstractMap.SimpleEntry<>(count.get(), results);
    }


    /**
     * 获取某分类下的普通方法
     * @param msgGetTypes 消息类型
     * @return 监听函数列表
     */
    private List<ListenerMethod> getNormalMethods(MsgGetTypes msgGetTypes){
        return LISTENER_METHOD_MAP.getOrDefault(msgGetTypes, EMPTY_MAP).getOrDefault(true, EMPTY_LIST);
    }


    /**
     * 获取某分类下的备用方法
     * @param msgGetTypes 消息类型
     * @return 监听函数列表
     */
    private List<ListenerMethod> getSpareMethods(MsgGetTypes msgGetTypes){
        return LISTENER_METHOD_MAP.getOrDefault(msgGetTypes, EMPTY_MAP).getOrDefault(false, EMPTY_LIST);
    }


    public boolean isCheckBot() {
        return checkBot;
    }

    public void setCheckBot(boolean checkBot) {
        this.checkBot = checkBot;
    }

    public ListenerManager(Collection<ListenerMethod> methods, BotManager botManager, ExceptionProcessCenter exceptionProcessCenter, MsgIntercept[] intercepts){
        this(methods, botManager, exceptionProcessCenter, intercepts, true);
    }

        /**
         * 构造方法，对函数进行分组保存
         * @param methods 函数集合
         * @param intercepts 消息拦截器数组 nullable
         */
    public ListenerManager(Collection<ListenerMethod> methods, BotManager botManager, ExceptionProcessCenter exceptionProcessCenter, MsgIntercept[] intercepts, boolean checkBot){
        // bot管理器
        this.botManager = botManager;

        // 异常处理中心
        this.exceptionProcessCenter = exceptionProcessCenter;

        this.checkBot = true;

        // 排序并构建消息拦截器
        if(intercepts != null){
            MsgIntercept[] msgInterceptsCopy = Arrays.copyOf(intercepts, intercepts.length);

            Arrays.sort(msgInterceptsCopy);
            this.intercepts = msgInterceptsCopy;
        }else{
            this.intercepts = new MsgIntercept[0];
        }


        /*
             构建的时候需要进行排序
         */
        //如果没有东西
        if(methods == null || methods.isEmpty()){
            this.LISTENER_METHOD_MAP = Collections.EMPTY_MAP;
        }else{
            //分组后赋值
            //第一层分组后
            Map<MsgGetTypes[], Set<ListenerMethod>> collect = methods.stream()
                    // 分组的同时，初始化一次所有的监听函数
                    .peek(lm -> {
                        final double initStart = System.currentTimeMillis();
                        try {
                            lm.getListener();
                        }catch (Exception e){
                            throw new ListenerException("init.failed", e);
                        }
                        QQLog.debug("listener.init", lm.getUUID(), (System.currentTimeMillis() - initStart));
                    })
                    //第一层分组
                    .collect(Collectors.groupingBy(ListenerMethod::getTypes, Collectors.toSet()));

            //按照分类进行转化
            HashMap<MsgGetTypes, Set<ListenerMethod>> firstMap = new HashMap<>(collect.size());

            //遍历
            collect.forEach((k, v) -> {
                //遍历类型
                for (MsgGetTypes types : k) {
                    Set<ListenerMethod> listenerMethods = firstMap.get(types);
                    if(listenerMethods != null){
                        //如果存在，追加
                        listenerMethods.addAll(v);
                    }else{
                        //如果不存在，创建并保存
                        listenerMethods = new HashSet<>(v);
                        firstMap.put(types, listenerMethods);
                    }
                }
            });

            //第二层，将参数按照是否为普通函数转化，转化完成后保存
            this.LISTENER_METHOD_MAP = firstMap.entrySet().stream().flatMap(e -> {
                //准备数据
                Map<MsgGetTypes, Map<Boolean, List<ListenerMethod>>> result = new HashMap<>(firstMap.size() / 2);
                Map<Boolean, List<ListenerMethod>> groupBySpare = e.getValue().stream().collect(Collectors.groupingBy(lm -> !lm.isSpare()));
                // 将结果集进行排序
                groupBySpare.forEach((k, v) -> Collections.sort(v));
                result.put(e.getKey(), groupBySpare);
                return result.entrySet().stream();
                // 使用EnumMap
                // 使用EnumMap的话，在用工厂创建额外的枚举类的时候会出问题。换回hashMap
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), HashMap::new));
        }
    }


    /**
     * Returns a merge function, suitable for use in
     * throws {@code IllegalStateException}.  This can be used to enforce the
     * assumption that the elements being collected are distinct.
     *
     * @param <T> the type of input arguments to the merge function
     * @return a merge function which always throw {@code IllegalStateException}
     */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }
}
