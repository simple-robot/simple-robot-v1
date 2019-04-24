package com.forte.qqrobot.utils;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.listener.invoker.ListenerMethod;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 监听函数助手
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/1 15:12
 * @since JDK1.8
 **/
public class ListenerMethodHelper {

    /**
     * 将监听函数根据类型分类
     */
    public static Map<MsgGetTypes, Set<ListenerMethod>> groupByMsgGetTypes(Collection<ListenerMethod> listenerMethods){
        //第一层分组
        Map<MsgGetTypes[], Set<ListenerMethod>> collect = listenerMethods.stream().collect(Collectors.groupingBy(ListenerMethod::getTypes, Collectors.toSet()));
        //结果集
        Map<MsgGetTypes, Set<ListenerMethod>> result = new HashMap<>();
        //遍历第一层分组结果
        collect.forEach((kArr, set) -> {
            //遍历名称列表, 如果存在这个名称，添加所有，如果不存在，创建并保存
            Arrays.stream(kArr).forEach(k -> Maputer.peek(result, k, s -> s.addAll(set), () -> new HashSet<>(set)));
        });

        //返回结果
        return result;
    }

}
