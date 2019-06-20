package com.forte.qqrobot.depend;


/**
 * 基础数据类型的Depend对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BasicDepend<V> extends Depend<V> {

    /**
     * 构造
     * @param name  基础数据类型名称
     * @param type  基础数据类型
     * @param value 值
     */
    public BasicDepend(String name, Class<V> type, V value) {
        super(name, type, true, () -> value, v -> {}, (v, add) -> {});
    }
}
