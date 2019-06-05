package com.forte.qqrobot.depend;

import com.forte.qqrobot.utils.ObjectsPlus;

import java.util.function.Function;

/**
 * 标注了Beans注解的类、方法<br>
 * 封装了他们所携带的各种条件、参数以及成为实例所需要的各类参数、条件<br>
 * 名称绝不可以相同，当名称不同的时候，类名可以相同
 * 但是假如已经有一个此类型存在于单例工厂，将无法添加其他异名单例
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Beans<T> {

    /** 名称 */
    private final String NAME;

    /** 类型 */
    private final Class<T> TYPE;

    /** 是否为单例 */
    private final boolean single;

    /** 实例化所需要的参数列表及其对应的name */
    private final NameTypeEntry[] instanceNeed;

    /** 获取实例对象的方法 */
    private final Function<Object[], T> getInstanceFunction;

    /** 假如此Beans是类上的，那么children代表了其类中存在的其他Beans */
    private final Beans[] children;

    /** Beans注解对象 */
    private final com.forte.qqrobot.anno.depend.Beans beans;

    /**
     * 构造
     */
    public Beans(String NAME, Class<T> TYPE, boolean single, NameTypeEntry[] instanceNeed, Function<Object[], T> getInstanceFunction, Beans[] children,
                 com.forte.qqrobot.anno.depend.Beans beans) {
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.single = single;
        this.instanceNeed = instanceNeed == null ? new NameTypeEntry[0] : instanceNeed;
        this.getInstanceFunction = getInstanceFunction;
        this.children = children == null ? new Beans[0] : children;
        this.beans = beans;

        //判断除了数组之外的类型是否存在null值
        ObjectsPlus.allNonNull("com.forte.qqrobot.depend.Beans对象字段不可出现null值", this.NAME, this.TYPE, this.single, getInstanceFunction, this.beans);
    }

    public String getName() {
        return NAME;
    }

    public Class<T> getType() {
        return TYPE;
    }

    public boolean isSingle() {
        return single;
    }

    public NameTypeEntry[] getInstanceNeed() {
        return instanceNeed;
    }

    public Beans[] getChildren() {
        return children;
    }

    public Function<Object[], T> getGetInstanceFunction() {
        return getInstanceFunction;
    }

    public com.forte.qqrobot.anno.depend.Beans getBeans() {
        return beans;
    }

    @Override
    public String toString() {
        return "Beans{" +
                "name='" + NAME + '\'' +
                ", type=" + TYPE +
                ", single=" + single +
                '}';
    }
}
