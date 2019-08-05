package com.forte.qqrobot.beans.types;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * GETTER的缓存类型枚举
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum CacheTypes {

    /** 当前时间后的xxnanos */
    PLUS_NANOS(l -> getEntry(() -> LocalDateTime.now().plusNanos(l == null ? 1 : l), CacheTimeTypes.NANOS)),

    /** 当前时间后推xx秒 */
    PLUS_SECONDS(l -> getEntry(() -> LocalDateTime.now().plusSeconds(l == null ? 1 : l), CacheTimeTypes.SECONDS)),

    /** 当前时间后推xx分钟 */
    PLUS_MINUTES(l -> getEntry(() -> LocalDateTime.now().plusMinutes(l == null ? 1 : l), CacheTimeTypes.MINUTES)),

    /** 当前时间向后推xx小时 */
    PLUS_HOURS(l -> getEntry(() -> LocalDateTime.now().plusHours(l == null ? 1 : l), CacheTimeTypes.HOURS)),

    /** 当前时间向后推xx天 */
    PLUS_DAYS(l -> getEntry(() -> LocalDateTime.now().plusDays(l == null ? 1 : l), CacheTimeTypes.DAYS)),

    /** 当前时间向后推xx月 */
    PLUS_MONTH(l -> getEntry(() -> LocalDateTime.now().plusMonths(l == null ? 1 : l), CacheTimeTypes.MONTH)),

    /** 当前时间向后推xx年 */
    PLUS_YEAR(l -> getEntry(() -> LocalDateTime.now().plusYears(l == null ? 1 : l), CacheTimeTypes.YEAR))


    ;

    private static <K, V> Map.Entry<K, V> getEntry(K k, V v){
        return new AbstractMap.SimpleEntry<>(k, v);
    }

    //返回值中，key为获取到的时间对象，value值为其对应的时间类型
    private final Function<Long, Map.Entry<Supplier<LocalDateTime>, CacheTimeTypes>> timeCreator;

    CacheTypes(Function<Long, Map.Entry<Supplier<LocalDateTime>, CacheTimeTypes>> timeCreator){
        this.timeCreator = timeCreator;
    }

    public Function<Long, Map.Entry<Supplier<LocalDateTime>, CacheTimeTypes>> getTimeCreator(){
        return timeCreator;
    }

    public Map.Entry<Supplier<LocalDateTime>, CacheTimeTypes> get(long time){
        return timeCreator.apply(time);
    }

    public LocalDateTime getTime(long time){
        return get(time).getKey().get();
    }

    public CacheTimeTypes getType(long time){
        return get(time).getValue();
    }
}
