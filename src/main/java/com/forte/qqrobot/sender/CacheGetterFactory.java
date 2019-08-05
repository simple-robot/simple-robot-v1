package com.forte.qqrobot.sender;

import com.forte.qqrobot.beans.types.CacheTimeTypes;
import com.forte.qqrobot.beans.types.CacheTypes;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.utils.collections.MethodCacheMap;
import com.forte.utils.reflect.ProxyUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 可缓存的GETTER构建工厂
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CacheGetterFactory {
    /*
        getter的缓存通过动态代理来实现。
        理论上，同一个GETTER对象，在通过获取缓存对象的时候应当都是同一个缓存对象，只是每次缓存的时长不同。
     */

    /**
     * 原始的GETTER对象，默认为当前线程第一次创建代理对象的时候进行初始化
     * 此字段为了防止出现通过代理对象二次创建代理，当使用cache相关方法的时候，如果不是原版getter
     * 则直接通过原版的getter创建代理而并非通过代理对象二次创建
     *
     */
    private static final ThreadLocal<SenderGetList> LOCAL_GETTER = new ThreadLocal<>();

    /**
     * <p>此处保存当前线程中getter对象对应的代理对象</p>
     * <p>
     *  <s>由于不能保证日后的对象必定获取的时候只有一个，所以使用Map来保存</s>
     * </p>
     * <p>
     *     不再缓存所有的代理，为了减少资源的占用，仅仅保存上一次的值
     * </p>
     *
     */
    private static final ThreadLocal<Map.Entry<ProxyType, SenderGetList>> LOCAL_PROXY_GETTER = new ThreadLocal<>();

    /**
     * <s>直接使用SenderGetList对象作为key</s> <br>
     * 由于代理对象的缓存与否与缓存参数有关，则缓存Map则也根据缓存参数挂钩而并非GETTER对象自身
     * 方法缓存Map作为value
     */
    private static final Map<ProxyType, MethodCacheMap> CACHE_MAP_RECORD = new ConcurrentHashMap<>();

    /**
     * Object中继承来的Method对象数组
     */
    private static final Method[] OBJECT_METHODS = Object.class.getMethods();

    /**
     * 获取缓存Map
     */
    private synchronized static MethodCacheMap getCacheMap(ProxyType type) {
        MethodCacheMap cacheMap = CACHE_MAP_RECORD.get(type);
        if (cacheMap == null) {
            cacheMap = new MethodCacheMap();
            CACHE_MAP_RECORD.put(type, cacheMap);
        }
        return cacheMap;
    }

    /**
     * 构建缓存的代理Getter
     *
     * @param getter     getter对象
     * @param time       时间类型，可以为null
     * @param cacheTypes 时间获取器与类型获取器及其对应枚举
     */
    public static <T extends SenderGetList> SenderGetList toCacheableGetter(T getter, Long time, CacheTypes cacheTypes) {
        return toCacheableGetter(getter, time, cacheTypes == null ? null : cacheTypes.getTimeCreator());
    }

    /**
     * 构建缓存的代理Getter
     *
     * @param getter getter对象
     * @param time   时间类型，可以为null
     */
    public static <T extends SenderGetList> SenderGetList toCacheableGetter(T getter, Long time) {
        return toCacheableGetter(getter, time, (Function<Long, Map.Entry<Supplier<LocalDateTime>, CacheTimeTypes>>) null);
    }

    /**
     * 构建缓存的代理Getter
     *
     * @param getter getter对象
     */
    public static <T extends SenderGetList> SenderGetList toCacheableGetter(T getter) {
        return toCacheableGetter(getter, (Long) null);
    }


    /**
     * 构建缓存的代理Getter
     *
     * @param toCacheGetter         getter对象
     * @param time           时间类型，可以为null
     * @param timeTypeGetter 时间获取器与类型获取器，可以通过枚举CacheTypes指定，可以为null
     */
    public static SenderGetList toCacheableGetter(SenderGetList toCacheGetter, Long time, Function<Long, Map.Entry<Supplier<LocalDateTime>, CacheTimeTypes>> timeTypeGetter) {
        Objects.requireNonNull(toCacheGetter, "GETTER对象自身不可为null");

        SenderGetList getter;

        //如果原版getter为null，初始化值
        if(LOCAL_GETTER.get() == null){
            LOCAL_GETTER.set(toCacheGetter);
            getter = toCacheGetter;
        }else{
            //看看是否一致，如果不一致则使用本地保存的原本的getter
            getter = LOCAL_GETTER.get();
        }


        //获取类型
        CacheTimeTypes type = timeTypeGetter == null ? null : timeTypeGetter.apply(time).getValue();
        Supplier<LocalDateTime> timeGetter = timeTypeGetter == null ? null : timeTypeGetter.apply(time).getKey();

        //先尝试查询本线程代理对象
        ProxyType proxyType = new ProxyType(time, type);

        //获取缓存Map
        MethodCacheMap cache = getCacheMap(proxyType);

        Map.Entry<ProxyType, SenderGetList> localCache = LOCAL_PROXY_GETTER.get();

        //如果为null，记录值且value值为null
        if((localCache == null) || (!localCache.getKey().equals(proxyType))){
            localCache = new AbstractMap.SimpleEntry<>(proxyType, null);
            LOCAL_PROXY_GETTER.set(localCache);
        }

        //查看是否有getList代理对象
        SenderGetList senderGetList = localCache.getValue();
        if (senderGetList != null) {
            return senderGetList;
        }

        System.out.println("无记录：" + proxyType);

        //构建GETTER的代理对象
        SenderGetList proxyCacheGetter = getProxy(getter, SenderGetList.class, (m, o) -> {
            //如果是Object的方法，不进行缓存
            for (Method objectMethod : OBJECT_METHODS) {
                if(objectMethod.equals(m)){
                    return m.invoke(getter, o);
                }
            }
            //执行
            //先查看是否有缓存值
            Object result = cache.get(m);
            if (result == null) {
                //没有值，执行方法并获取返回值
                result = m.invoke(getter, o);
                if (time != null) {
                    //有时间
                    if (timeGetter == null) {
                        //没有时间获取器，默认时间类型为秒
                        cache.putPlusSeconds(m, result, time);
                    } else {
                        cache.put(m, result, timeGetter.get());
                    }

                } else {
                    //如果什么也没有，则此代理默认缓存1小时
                    cache.putPlusHours(m, result, 1);
                }
            }

            //返回结果
            return result;
        });

        //记录本地线程数据
        LOCAL_PROXY_GETTER.get().setValue(proxyCacheGetter);
        return proxyCacheGetter;
    }

    /**
     * 构建缓存的代理Getter
     *  通过指定时间定义过期时间
     *  ※ 不是很推荐，但是如果过期时间很遥远的话也无所谓
     * @param toCacheGetter         getter对象
     * @param toTime           时间类型，不为null
     */
    public static SenderGetList toCacheableGetter(SenderGetList toCacheGetter, LocalDateTime toTime) {
        Objects.requireNonNull(toTime, "指定的过期日期不可为null");
        Objects.requireNonNull(toCacheGetter, "GETTER对象自身不可为null");

        SenderGetList getter;

        //如果原版getter为null，初始化值
        if(LOCAL_GETTER.get() == null){
            LOCAL_GETTER.set(toCacheGetter);
            getter = toCacheGetter;
        }else{
            //看看是否一致，如果不一致则使用本地保存的原本的getter
            //直接使用 == 来对内存地址进行对比判断
            getter = LOCAL_GETTER.get();
        }


        //先尝试查询本线程代理对象
        ProxyType proxyType = new ProxyType(toTime.toInstant(ZoneOffset.of("+8")).toEpochMilli(), CacheTimeTypes.DATE);

        //获取缓存Map
        MethodCacheMap cache = getCacheMap(proxyType);

        Map.Entry<ProxyType, SenderGetList> localCache = LOCAL_PROXY_GETTER.get();

        //如果为null，记录值且value值为null
        if((localCache == null) || (!localCache.getKey().equals(proxyType))){
            localCache = new AbstractMap.SimpleEntry<>(proxyType, null);
            LOCAL_PROXY_GETTER.set(localCache);
        }

        SenderGetList senderGetList = localCache.getValue();
        if (senderGetList != null) {
            return senderGetList;
        }

        //构建GETTER的代理对象
        SenderGetList proxyCacheGetter = ProxyUtils.proxy(SenderGetList.class, (m, o) -> {
            //如果是Object的方法，不进行缓存
            for (Method objectMethod : OBJECT_METHODS) {
                if(objectMethod.equals(m)){
                    return m.invoke(getter, o);
                }
            }
            System.out.println("代理~:" + m);
            //执行
            //先查看是否有缓存值
            Object result = cache.get(m);
            if (result == null) {
                //没有值，执行方法并获取返回值
                result = m.invoke(getter, o);
                //保存至指定时间
                cache.put(m, result, toTime);
            }

            //返回结果
            return result;
        });

        //记录本地线程数据
        LOCAL_PROXY_GETTER.get().setValue(proxyCacheGetter);
        return proxyCacheGetter;
    }

    /**
     * 获取代理对象
     */
    private static <B extends T, T> T getProxy(B b, Class<T> type, ProxyUtils.ExProxyHandler<Method, Object[], Object> handler){
        return (T) Proxy.newProxyInstance(b.getClass().getClassLoader(), new Class[]{type}, (p, m, o) -> handler.apply(m, o));
    }



    /**
     * 内部私有类，用于区别缓存类型
     */
    private static class ProxyType {
        private final Long time;
        private final CacheTimeTypes type;


        public Long getTime() {
            return time;
        }

        public CacheTimeTypes getType() {
            return type;
        }

        public ProxyType(Long time, CacheTimeTypes type) {
            this.time = time;
            this.type = type;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProxyType proxyType = (ProxyType) o;
            return Objects.equals(time, proxyType.time) &&
                    type == proxyType.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, type);
        }

        @Override
        public String toString() {
            return "ProxyType{" +
                    "time=" + time +
                    ", type=" + type +
                    '}';
        }
    }


}
