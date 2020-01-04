package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.anno.Ignore;
import com.forte.qqrobot.intercept.Interceptor;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * 送信器代理工厂，通过动态代理实现对送信器的拦截。
 * 一般来讲，三大送信器不会频繁变更，所以仅会在启动的时候进行代理。<br>
 * <p>
 *     代理后的对象会进行缓存，缓存仅根据送信器对象而不会根据拦截器对象。
 * </p>
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SenderInterceptFactory {

    //**************************************
    //* 三个缓存Map记录已经代理过的SenderList对象
    //**************************************


    private static Map<SenderSendList, SenderSendList> SENDER_CACHE = new HashMap<>(2);
    private static Map<SenderGetList, SenderGetList>  GETTER_CACHE = new HashMap<>(2);
    private static Map<SenderSetList, SenderSetList>  SETTER_CACHE = new HashMap<>(2);


    /**
     * 使用动态代理创建代理
     * @param sender        送信器实例对象
     * @param intercepts    拦截接口
     * @return  send 代理
     */
    public static SenderSendList doSenderIntercept(SenderSendList sender, SenderSendIntercept... intercepts){
        SenderSendList cacheProxy = SENDER_CACHE.get(sender);
        if(cacheProxy != null){
            return cacheProxy;
        }else{
            SenderSendList proxySender = doIntercept(SenderSendList.class, sender, SendContext::new, intercepts);
            SENDER_CACHE.put(sender, proxySender);
            return proxySender;
        }
    }

    /**
     * 使用动态代理创建代理
     * @param sender        送信器实例对象
     * @param intercepts    拦截接口
     * @return  get 代理
     */
    public static SenderGetList doGetterIntercept(SenderGetList sender, SenderGetIntercept... intercepts){
        SenderGetList cacheProxy = GETTER_CACHE.get(sender);
        if(cacheProxy != null){
            return cacheProxy;
        }else{
            SenderGetList proxySender = doIntercept(SenderGetList.class, sender, GetContext::new, intercepts);
            GETTER_CACHE.put(sender, proxySender);
            return proxySender;
        }
    }

    /**
     * 使用动态代理创建代理
     * @param sender        送信器实例对象
     * @param intercepts    拦截接口
     * @return  set 代理
     */
    public static SenderSetList doSetterIntercept(SenderSetList sender, SenderSetIntercept... intercepts){
        SenderSetList cacheProxy = SETTER_CACHE.get(sender);
        if(cacheProxy != null){
            return cacheProxy;
        }else{
            SenderSetList proxySender = doIntercept(SenderSetList.class, sender, SetContext::new, intercepts);
            SETTER_CACHE.put(sender, proxySender);
            return proxySender;
        }
    }

    /**
     * 送信器代理
     *
     * @param type
     * @param sender 送信器对象
     * @param contextGetter 获取上下文对象的函数, 有两个参数：1：送信器实例对象，2：执行参数列表
     * @param intercepts 拦截器
     * @return 代理对象
     */
    public static <S extends SenderList> S doIntercept(Class<? extends S> type, S sender, BiFunction<S, Object[], SenderContext<? extends S>> contextGetter, Interceptor[] intercepts){
        // 先对数组进行排序
        if(intercepts.length > 0){
            Arrays.sort(intercepts);
            // 排序后，执行代理
            return senderProxy(type, sender, (pro, m, args) -> {
                //如果是接口中的默认方法，使用特殊方法执行
                //代码源于网络: http://www.it1352.com/988865.html
                if(m.isDefault()){
                    Class<?> declaringClass = m.getDeclaringClass();
                    Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                    constructor.setAccessible(true);

                    return constructor.
                            newInstance(declaringClass, MethodHandles.Lookup.PRIVATE).
                            unreflectSpecial(m, declaringClass).
                            bindTo(pro).invokeWithArguments(args);
                }

                // 不代理private方法
                if(Modifier.isPrivate(m.getModifiers())){
                    return m.invoke(sender, args);
                }

                // 否则，代理未携带@Ignore注解的方法
                // @Ignore直接获取
                if(m.getAnnotation(Ignore.class) != null){
                    // 如果是需要忽略的方法，忽略不代理
                    return m.invoke(sender, args);
                }

                // 代理
                SenderContext<?> context = contextGetter.apply(sender, args);
                // 执行前置
                boolean success = true;
                for (Interceptor intercept : intercepts) {
                    success = intercept.intercept(context);
                    // 如果出现失败情况，终止执行
                    if(!success){
                        break;
                    }
                }
                // 如果允许执行，执行
                if(success){
                    return m.invoke(sender, args);
                }else{
                    // 如果不允许执行，则日志中打印一个警告信息并返回null
                    QQLog.warning("API["+ m.getName() +"]已被阻止");
                    // 先尝试获取@InterceptValue注解
                    InterceptValue deValue = m.getAnnotation(InterceptValue.class);
                    Class<?> returnType = m.getReturnType();
                    if(deValue == null){
                        // 判断返回值类型，如果是boolean，返回false, 否则为null
                        if(returnType.equals(boolean.class) || returnType.equals(Boolean.class)){
                            return false;
                        }else{
                            return null;
                        }
                    }else{
                        if(deValue.value().trim().length() == 0 && !returnType.equals(String.class)){
                            // 如果参数为空，且返回值不是String类型，则认为值为null
                            return null;
                        }else{
                            // 根据注解值返回
                            return ConvertUtils.convert(deValue.value(), returnType);
                        }
                    }
                }
            });
        }else{
            // 拦截接口数量为0，不代理
            return sender;
        }
    }


    private static final Class[] senderListInterfaceClasses = new Class[]{SenderList.class};

    /**
     * 实现代理
     * @param type     送信器的接口类型
     * @param sender   sender送信器对象
     * @param handler  代理控制器
     * @return          代理对象
     */
    private static <S extends SenderList> S senderProxy(Class<? extends SenderList> type, SenderList sender, InvocationHandler handler){
        return (S) Proxy.newProxyInstance(sender.getClass().getClassLoader(), new Class[]{type}, handler);
    }

}
