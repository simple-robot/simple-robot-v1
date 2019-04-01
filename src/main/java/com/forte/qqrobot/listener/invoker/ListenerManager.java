package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.msgget.MsgGet;
import com.forte.qqrobot.beans.types.MsgGetTypes;
import com.forte.qqrobot.log.QQLog;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 监听函数管理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/26 16:23
 * @since JDK1.8
 **/
public class ListenerManager {

    /**保存全部监听函数并进行两层分类
     * 第一层，按照接收的消息类型分类
     * 第二层，按照是否为普通函数分类
     */
    private final Map<MsgGetTypes, Map<Boolean, List<ListenerMethod>>> LISTENER_METHOD_SET;

    /**
     * 空的map, 用于应对从监听函数获取的时候出现空的情况
     */
    private static final Map<Boolean, List<ListenerMethod>> EMPTY_MAP;

    /**
     * 空的list，用于应对空指针异常
     */
    private static final List<ListenerMethod> EMPTY_LIST = Collections.EMPTY_LIST;

    static{
        Map<Boolean, List<ListenerMethod>> emptyMap = new HashMap<>(2);
        emptyMap.put(true , EMPTY_LIST);
        emptyMap.put(false, EMPTY_LIST);
        EMPTY_MAP = emptyMap;
    }

    /**
     * 接收到了消息响应
     * @param msgGet    接收的消息
     * @param args      参数列表
     * @param at        是否被at
     */
    public void invoke(MsgGet msgGet, Set<Object> args, boolean at){
        //获取消息类型
        MsgGetTypes type = MsgGetTypes.getByType(msgGet.getClass());

        //todo 先查看是否存在阻断函数



        //先执行普通监听函数
        int invokeNum = invokeNormal(type, args, msgGet, at);

        //如果没有普通监听函数执行成功，则尝试执行备用监听函数
        if(invokeNum <= 0){
            invokeSpare(type, args, msgGet, at);
        }
    }

    /**
     * 接收到了消息响应
     * @param msgGet    接收的消息
     * @param args      参数列表
     * @param at        是否被at
     */
    public void invoke(MsgGet msgGet, Object[] args, boolean at){
        invoke(msgGet, Arrays.stream(args).collect(Collectors.toSet()), at);
    }

    /**
     * 执行默认监听函数
     * @param msgGetTypes   消息类型
     * @param args          参数集合
     * @param msgGet        接收的消息
     * @param at            是否被at
     * @return              执行成功函数数量
     */
    private int invokeNormal(MsgGetTypes msgGetTypes, Set<Object> args, MsgGet msgGet, boolean at){
        //执行过的方法数量
        AtomicInteger count = new AtomicInteger(0);
        //获取监听函数过滤器
        ListenerFilter listenerFilter = ResourceDispatchCenter.getListenerFilter();

        //获取这个消息分类下的普通方法
        List<ListenerMethod> normalMethods = getNormalMethods(msgGetTypes);

        normalMethods.stream().filter(lm -> listenerFilter.filter(lm, msgGet, at)).forEach(lm -> {
            //过滤后，执行
            try {
                boolean runTrue = lm.invoke(args);
                //如果执行成功，计数+1
                count.addAndGet(runTrue ? 1 : 0);
            } catch (InvocationTargetException | IllegalAccessException e) {
                QQLog.error("监听器["+ lm.getBeanToString() +"]执行方法["+ lm.getMethodToString() +"]出现错误！", e);
            }
        });

        return count.get();
    }


    /**
     * 执行备用监听函数
     * @param msgGetTypes   消息类型
     * @param args          参数集合
     * @param msgGet        接收的消息
     * @param at            是否被at
     */
    private int invokeSpare(MsgGetTypes msgGetTypes, Set<Object> args, MsgGet msgGet, boolean at){
        //执行过的方法数量
        AtomicInteger count = new AtomicInteger(0);
        //获取监听函数过滤器
        ListenerFilter listenerFilter = ResourceDispatchCenter.getListenerFilter();

        //获取这个消息分类下的备用方法
        List<ListenerMethod> spareMethods = getSpareMethods(msgGetTypes);

        spareMethods.stream().filter(lm -> listenerFilter.filter(lm, msgGet, at)).forEach(lm -> {
            //过滤完成后执行方法
            try {
                boolean runTrue = lm.invoke(args);
                //如果执行成功，计数+1
                count.addAndGet(runTrue ? 1 : 0);
            } catch (InvocationTargetException | IllegalAccessException e) {
                QQLog.error("监听器["+ lm.getBeanToString() +"]执行方法["+ lm.getMethodToString() +"]出现错误！", e);
            }
        });

        return count.get();
    }


    /**
     * 获取某分类下的普通方法
     * @param msgGetTypes 消息类型
     * @return 监听函数列表
     */
    private List<ListenerMethod> getNormalMethods(MsgGetTypes msgGetTypes){
        return LISTENER_METHOD_SET.getOrDefault(msgGetTypes, EMPTY_MAP).getOrDefault(true, EMPTY_LIST);
    }


    /**
     * 获取某分类下的备用方法
     * @param msgGetTypes 消息类型
     * @return 监听函数列表
     */
    private List<ListenerMethod> getSpareMethods(MsgGetTypes msgGetTypes){
        return LISTENER_METHOD_SET.getOrDefault(msgGetTypes, EMPTY_MAP).getOrDefault(false, EMPTY_LIST);
    }




    /**
     * 构造方法，对函数进行分组保存
     * @param methods 函数集合
     */
    public ListenerManager(Collection<ListenerMethod> methods){
        //分组后赋值
        //第一层分组后
        Map<MsgGetTypes[], Set<ListenerMethod>> collect = methods.stream()
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
        this.LISTENER_METHOD_SET = firstMap.entrySet().stream().flatMap(e -> {
            //准备数据
            Map<MsgGetTypes, Map<Boolean, List<ListenerMethod>>> result = new HashMap<>(firstMap.size());
            result.put(e.getKey(), e.getValue().stream().collect(Collectors.groupingBy(lm -> !lm.isSpare())));
            return result.entrySet().stream();
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



    }
}
