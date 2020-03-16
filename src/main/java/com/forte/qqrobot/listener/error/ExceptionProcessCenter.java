package com.forte.qqrobot.listener.error;

import com.forte.qqrobot.anno.ExceptionCatch;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.exception.ExceptionProcessException;
import com.forte.qqrobot.exception.NoSuchExceptionHandleException;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre> 异常处理中心
 * <pre> 当{@link com.forte.qqrobot.listener.invoker.ListenerMethod} 执行method出现异常的时候，便会尝试捕获并处理。
 * <pre> 每一种异常类型只能注册一个处理器，未知的异常会尝试获取一个父类异常，如果能获取，则处理，否则最终不做处理。
 * <pre> 如果存在{@link Exception} 与 {@link RuntimeException} 处理器，则它们永远是最后被尝试使用的。
 * <pre> 理论上，此类在构建好后，内部的处理器不应再发生变动。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ExceptionProcessCenter {

    private static final QQLogLang LOG_LANG = new QQLogLang("exception.process.center");

    /**
     * Exception类的处理器, 如果没有则为null
     */
    private ExceptionHandle exHandle;

    /**
     * RuntimeException类的处理器， 如果没有则为null
     */
    private ExceptionHandle runtimeExHandle;

    /**
     * 其他 handles 异常处理器记录
     */
    private final Map<Class<? extends Exception>, ExceptionHandle> HANDLES;

    /**
     * 缓存记录尝试获取的时候获取不到的类型。由于此类的HANDLES不变，则获取一次没有获取到的则必然不会存在
     */
    private final Set<Class<? extends Exception>> NULL_TYPE;

    /**
     * 构造
     *
     * @param handles 处理器合集
     */
    private ExceptionProcessCenter(Map<Class<? extends Exception>, ExceptionHandle> handles) {
        this.HANDLES = new ConcurrentHashMap<>(handles);
        this.NULL_TYPE = new HashSet<>(4);
        // 尝试取出ex handle
        this.exHandle = HANDLES.remove(Exception.class);
        // 尝试取出run ex handle
        this.runtimeExHandle = HANDLES.remove(RuntimeException.class);
    }

    /**
     * 工厂方法，直接使用构造
     *
     * @param handles handles
     * @return 实例对象
     */
    public static ExceptionProcessCenter getInstance(Map<Class<? extends Exception>, ExceptionHandle> handles) {
        return new ExceptionProcessCenter(handles);
    }

    /**
     * 构建一个空的实例
     *
     * @return 实例对象
     */
    public static ExceptionProcessCenter getInstance() {
        return new ExceptionProcessCenter(new HashMap<>(1));
    }

    /**
     * 根据ExceptionHandle集来构建结果。
     * 需要Exception Handle上存在{@link com.forte.qqrobot.anno.ExceptionCatch} 注解
     * 如果不存在，则默认为处理{@link Exception}异常
     * 一般来讲数量不会很多
     *
     * @param handles handles
     * @return 实例对象
     */
    public static ExceptionProcessCenter getInstance(ExceptionHandle... handles) {
        if (handles.length == 0) {
            return getInstance();
        }
        // 准备一个数据储存
        Map<Class<? extends Exception>, ExceptionHandle> map = new HashMap<>(handles.length >> 1);

        Class<? extends Exception>[] defaultClasses = new Class[]{Exception.class};

        // 遍历, 获取注解
        for (ExceptionHandle handle : handles) {
            final ExceptionCatch catchAnnotation = AnnotationUtils.getAnnotation(handle.getClass(), ExceptionCatch.class);
            // 获取classes
            Class<? extends Exception>[] classes = catchAnnotation == null ? defaultClasses : catchAnnotation.value();

            // 遍历并添加
            for (Class<? extends Exception> clazz : classes) {
                map.merge(clazz, handle, (old, val) -> {
                    throw new ExceptionProcessException("exists", clazz, old.getClass(), val.getClass());
                });
            }
        }

        // 返回结果
        return new ExceptionProcessCenter(map);
    }


    /**
     * 获取某个类型的异常处理器，如果不存在则返回null
     * 如果是某个类型的子类，但是没有直接的类，最终在获取后会将此类型也进行缓存
     *
     * @param exType 异常类型
     */
    public ExceptionHandle getHandle(Class<? extends Exception> exType) {
        Objects.requireNonNull(exType);

        // 如果是记录在案的NULL，则直接返回null
        if (NULL_TYPE.contains(exType)) {
            return null;
        }

        // 如果就是ex
        if (exType == Exception.class) {
            return exHandle;
        }
        // 如果是runtime类型
        if (exType == RuntimeException.class) {
            if (runtimeExHandle != null) {
                return runtimeExHandle;
            } else if (exHandle != null) {
                // 缓存并返回
                return runtimeExHandle = exHandle;
            }
        }

        // 直接获取
        final ExceptionHandle handle = HANDLES.get(exType);
        if (handle != null) {
            return handle;
        }

        final Set<Class<? extends Exception>> keys = HANDLES.keySet();
        for (Class<? extends Exception> handleTypes : keys) {
            if (FieldUtils.isChild(exType, handleTypes)) {
                // 找到的第一个父类类型，记录缓存并返回
                final ExceptionHandle exceptionHandle = HANDLES.get(handleTypes);
                HANDLES.put(exType, exceptionHandle);
                return exceptionHandle;
            }
        }

        // 没有, 看看有没有runtime
        if (runtimeExHandle != null && FieldUtils.isChild(exType, RuntimeException.class)) {
            // 是runtime类型的，返回runtime
            HANDLES.put(exType, runtimeExHandle);
            return runtimeExHandle;
        }
        // 最后，如果存在Ex，直接返回，毕竟所有人的父类
        if (exHandle != null) {
            HANDLES.put(exType, exHandle);
            return exHandle;
        }

        // 没有，记录一下
        synchronized (NULL_TYPE) {
            NULL_TYPE.add(exType);
        }

        return null;
    }

    /**
     * 对一个异常信息进行处理。如果无法获取到合适的handle，则会抛出一个异常。
     */
    public Object doHandle(Exception e, ExceptionHandleContext context) throws NoSuchExceptionHandleException {
        final ExceptionHandle handle = getHandle(e.getClass());
        if(handle == null){
            throw new NoSuchExceptionHandleException(1, e.getClass().toString());
        }else{
            return handle.handle(context);
        }
    }
}
