package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.anno.Spare;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGet;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.FieldUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 监听器执行器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 14:36
 * @since JDK1.8
 **/
public class ListenerInvoker {

    //key为true的为没有@spare注解的，为false的是有注解的
    private final Map<SocketListener, Map<Boolean, List<Method>>> listenerMethods = new ConcurrentHashMap<>(5);

    /** 监听器接口方法名 */
    private final String SOCKET_LISTENER_METHOD = "onMessage";

    /** 监听器接口参数数量 */
    private final int SOCKET_LISTENER_PARAM_LENGTH = 5;


    /**
     * 从监听器中选出可以执行从参数的监听器并执行
     * @param listenerCollection    监听器列表
     * @param params                参数列表，[MsgBean, CQCode, boolean, CQCodeUtil, QQWebSocketMsgSender]
     */
    public void invokeListenerByParams(Collection<SocketListener> listenerCollection, Object[] params){
        //备用方法集
        Map<SocketListener, List<Method>> spareMethods = new HashMap<>(listenerCollection.size());
        //执行过的方法数量
        int invokeNum = invokeNormalListeners(listenerCollection, params, spareMethods);

        if(invokeNum <= 0){
            //没有执行过的普通监听器，则执行备用监听器
            spareMethods.forEach((listener, methods) -> invokeListener(listener, filterMethods(methods.stream(), params[0]), params));
        }
    }

    /**
     * 尝试执行普通的监听器
     * @return 执行的数量
     */
    private int invokeNormalListeners(Collection<SocketListener> listenerCollection, Object[] params,  Map<SocketListener, List<Method>> spareMap){
        //执行过的方法数量
        AtomicInteger count = new AtomicInteger(0);

        //多线程同步遍历
        listenerCollection.parallelStream().forEach(listener -> {
            boolean allSpare = false;
            if(listener.getClass().getAnnotation(Spare.class) != null){
                //如果注解在类上，它的所有方法都标记为备用
                allSpare = true;
            }
            //如果缓存中有则取缓存，如果没有则通过反射获取
            Map<Boolean, List<Method>> methodMap = listenerMethods.get(listener);
            if(methodMap == null) {
                //如果获取不到，获取并保存全部方法名为"onMessage"且参数数量为5的方法
                methodMap = loadListener(listener);
            }

            //如果类被标记，全部记录
            if(allSpare){
                //记录备用方法集
                List<Method> list = spareMap.get(listener);
                if(list == null){
                    list = new ArrayList<>();
                    list.addAll(methodMap.getOrDefault(false, new ArrayList<>()));
                    list.addAll(methodMap.getOrDefault(true, new ArrayList<>()));
                    spareMap.putIfAbsent(listener, list);
                }else{
                    list.addAll(methodMap.getOrDefault(false, new ArrayList<>()));
                    list.addAll(methodMap.getOrDefault(true, new ArrayList<>()));
                }
            }else{
                //分组，根据是否拥有@Spare注解分组
                //key为true的为没有注解的，为false的是有注解的
                //有Spare注解的方法将会再最后判断，如果在那之前没有其他监听器被调用的话再执行备用监听器
                //根据参数过滤,第一个参数是MsgGet类型且第一个参数与第一个传入参数的类型相同
                Method[] methodFilterArray = filterMethods(methodMap.get(true).stream(), params[0]);

                //记录备用方法集
                List<Method> list = spareMap.get(listener);
                if(list == null){
                    list = new ArrayList<>(methodMap.getOrDefault(false, new ArrayList<>()));
                    spareMap.putIfAbsent(listener, list);
                }else{
                    list.addAll(methodMap.getOrDefault(false, new ArrayList<>()));
                }

                //遍历执行的数量
                int invokeNums = invokeListener(listener, methodFilterArray, params);
                //计数
                count.addAndGet(invokeNums);
            }
        });
        return count.get();
    }

    /**
     * 执行监听器方法
     * @param listener  监听器
     * @param methodFilterArray 监听器的方法
     * @param params    参数
     * @return          执行成功的次数
     */
    private int invokeListener(SocketListener listener, Method[] methodFilterArray, Object[] params){
        //取出需要的参数
        MsgGet msgget = (MsgGet) params[0];
        CQCode[] cqCode = (CQCode[]) params[1];
        boolean at = (boolean) params[2];
        //计数
        AtomicInteger num = new AtomicInteger(0);
        //遍历，根据方法上的注解过滤方法
        Arrays.stream(methodFilterArray)
                .parallel()
                .filter(m -> ResourceDispatchCenter.getListenerFilter().filter(m, msgget, cqCode, at))
                .forEach(m -> {
                    //过滤后，遍历执行
                    try {
                        boolean runTrue = (boolean) m.invoke(listener, params);
                        //如果计为执行，计数
                        if(runTrue) num.addAndGet(1);
                    } catch (Exception e) {
                        System.err.println("监听器["+ listener.getClass() +"]执行方法["+ m.toString() +"]出现错误！");
                        e.printStackTrace();
                    }
                });
        //返回计数
        return num.get();
    }

    /**
     * 加载监听器方法
     * @param listener 监听器
     */
    public Map<Boolean, List<Method>> loadListener(SocketListener listener){
        Method[] methodArray = Arrays.stream(listener.getClass().getMethods()).parallel().filter(m -> (SOCKET_LISTENER_METHOD.equals(m.getName())) && (SOCKET_LISTENER_PARAM_LENGTH == m.getParameterCount())).toArray(Method[]::new);
        //根据是否为备用监听器分类
        Map<Boolean, List<Method>> collect = Arrays.stream(methodArray).collect(Collectors.groupingBy(m -> m.getAnnotation(Spare.class) == null));

        //记录缓存
        listenerMethods.putIfAbsent(listener, collect);
        //log
        QQLog.info("加载监听器["+ listener.getClass() +"]方法" + methodArray.length + "个");
        return collect;
    }

    /**
     * 根据第一个参数来过滤方法
     * @param methods   方法流
     * @param params0   第一个参数
     * @return  过滤出来的方法
     */
    private Method[] filterMethods(Stream<Method> methods, Object params0){
        //根据参数过滤,第一个参数是MsgGet类型且第一个参数与第一个传入参数的类型相同
        return methods.parallel().filter(
                m -> FieldUtils.isChild(m.getParameterTypes()[0], MsgGet.class)
                        &&
                        m.getParameterTypes()[0].equals(params0.getClass())).toArray(Method[]::new);
    }

}
