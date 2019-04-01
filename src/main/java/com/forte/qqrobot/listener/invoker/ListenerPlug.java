package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.Block;
import com.forte.qqrobot.beans.msgget.MsgGet;
import com.forte.qqrobot.beans.types.MsgGetTypes;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.utils.Maputer;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 监听器阻塞器
 * 每种种类的监听器同一时间仅只能有一个阻塞中的监听器，且阻塞的监听器只能由自己解除阻塞（或定时解除）
 * 阻塞后，同类型的监听器不论是普通监听器还是备用监听器都将失去作用，无法接收到消息，直到解除阻塞
 * 同时过滤器注解也将失去作用，并且提供为阻塞的时候专用的阻塞过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:45
 * @since JDK1.8
 **/
public class ListenerPlug {

    /**
     *  全局阻塞容器
     **/
    private final AtomicReference<ListenerMethod> GLOBAL_BLOCK = new AtomicReference<>(null);

    /**
     * 分类型的阻塞容器
     */
    private final AtomicReference<Map<MsgGetTypes, Set<ListenerMethod>>> ON_BLOCK = new AtomicReference<>(null);

    /**
     * 保存全部监听函数的集合，根据block的名称分组
     */
    private final Map<String, Map<MsgGetTypes, Set<ListenerMethod>>> allListeners;


    /**
     * 为监听函数取一个默认名称
     */
    private static String getDefaultName(ListenerMethod listenerMethod){
        String lisName = listenerMethod.getListener().getClass().getName();
        String mName = listenerMethod.getMethod().getName();
        String pName = Arrays.stream(listenerMethod.getMethod().getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(","));
        return lisName + "#" + mName + "(" + pName + ")";
    }


    private static <K, V> void mapPut(Map<K, V> map, K key, V value){
        V v = map.get(key);
        if(v != null){

        }else{


        }

    }


    /**
     * 构造，接收全部的
     * @param listenerMethodSet
     */
    public ListenerPlug(Set<ListenerMethod> listenerMethodSet){
        //接收到全部的监听函数，根据Block注解开始分组
        //第一层分组，根据名称数组分类
        Map<String[], Set<ListenerMethod>> firstMap = listenerMethodSet.stream()
                .collect(Collectors.groupingBy(
                        lisM -> Optional.ofNullable(lisM.getBlock()).map(Block::value).orElseGet(() -> new String[]{getDefaultName(lisM)}),
                        Collectors.toSet()
                ));


        //创建map保存结果，初始长度为firstMap的两倍
        Map<String, Map<MsgGetTypes, Set<ListenerMethod>>> finalMap = new HashMap<>(firstMap.size() * 2);

        //遍历firstMap
        firstMap.forEach((kArr, set) -> {
            //遍历名称数组
            Arrays.stream(kArr).forEach(name -> {
                //给当前的set，根据分类追加
                Map<MsgGetTypes[], Set<ListenerMethod>> collect = set.stream().collect(Collectors.groupingBy(ListenerMethod::getTypes, Collectors.toSet()));
                //如果name存在, 追加, 不存在, 创建并保存
                Maputer.peek(finalMap, name,
                        //如果存在
                        m -> {
                            //遍历这个set, 根据MsgGetTypes添加
                            //如果存在，遍历这个key，查看全部监听类型并添加
                            collect.forEach((k, v) -> Arrays.stream(k).forEach(msgT -> Maputer.peek(m, msgT, sv -> sv.addAll(v), () -> new HashSet<>(v))));
                        } ,
                        //如果不存在，创建一个新的保存
                        () -> {
                            //遍历collect，转化为msgType, Set
                            Map<MsgGetTypes, Set<ListenerMethod>> nullResult = new HashMap<>();
                            collect.forEach((k, v) -> Arrays.stream(k).forEach(msgT -> Maputer.peek(nullResult, msgT, sv -> sv.addAll(v), () -> new HashSet<>(v))));
                            return nullResult;
                        });

            });

        });

        //赋值保存
        this.allListeners = finalMap;

    }



}
