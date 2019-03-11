package com.forte.qqrobot.utils;

import com.forte.qqrobot.beans.msgget.MsgGet;
import com.forte.qqrobot.listener.SocketListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 18:28
 * @since JDK1.8
 **/
public class ListenerUtils {

    /** 保存监听器与其中方法的对应关系，起到缓存的作用 */
    private static final Map<SocketListener, Method[]> listenerMethods = new ConcurrentHashMap<>(5);

    /** 监听器接口方法名 */
    private static final String SOCKET_LISTENER_METHOD = "onMessage";

    /** 监听器接口参数数量 */
    private static final int SOCKET_LISTENER_PARAM_LENGTH = 5;


    /**
     * 从监听器中选出可以执行从参数的监听器并执行
     * @param listenerCollection    监听器列表
     * @param params                参数列表，[MsgBean, CQCode, boolean, CQCodeUtil, QQWebSocketMsgSender]
     */
    public static void invokeListenerByParams(Collection<SocketListener> listenerCollection, Object[] params){
        listenerCollection.forEach(listener -> {
            //如果缓存中有则取缓存，如果没有则通过反射获取
            Method[] methodArray = listenerMethods.get(listener);

            if(methodArray == null) {
                //如果获取不到，获取并保存全部方法名为"onMessage"且参数数量为5的方法
                methodArray = loadListener(listener);
            }

            //根据参数过滤,第一个参数是MsgGet类型且第一个参数与第一个传入参数的类型相同
            Method[] methodFilterArray = Arrays.stream(methodArray).filter(
                    m -> FieldUtils.isChild(m.getParameterTypes()[0], MsgGet.class)
                    &&
                    m.getParameterTypes()[0].equals(params[0].getClass())).toArray(Method[]::new);

            //遍历执行
            for (Method method : methodFilterArray) {
                try {
                    method.invoke(listener, params);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println("监听器["+ listener.getClass() +"]执行方法["+ method.toString() +"]出现错误！");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 加载监听器方法
     * @param listener 监听器
     */
    public static Method[] loadListener(SocketListener listener){
        Method[] methodArray = Arrays.stream(listener.getClass().getMethods()).filter(m -> (SOCKET_LISTENER_METHOD.equals(m.getName())) && (SOCKET_LISTENER_PARAM_LENGTH == m.getParameterCount())).toArray(Method[]::new);
        //记录缓存
        listenerMethods.putIfAbsent(listener, methodArray);
        String methodArrayString = Arrays.stream(methodArray).map(Method::getName).collect(Collectors.joining(", ", "[", "]"));
        //log
        System.out.println("加载监听器["+ listener.getClass() +"]方法：" + methodArrayString);
        return methodArray;
    }

}
