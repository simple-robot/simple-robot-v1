package com.forte.qqrobot.depend;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;

import java.util.Arrays;

/**
 * beans携带部分的参数封装
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BeansData {

    /**
     * \@Beans注解的参数：value
     */
    private String value;

    /**
     * \@Beans注解的参数：single
     */
    private boolean single;

    /**
     * 是否全部标记为Depend
     */
    private boolean allDepend;

    /**
     * 如果标记，默认的@Depend注解对象
     */
    private Depend depend;

    /**
     * \@Beans注解的参数：constructor
     */
    private Class[] constructor;

    /**
     * 是否需要先初始化一次。
     */
    private boolean init;

    /**
     * 优先级，降序排序
     */
    private int priority;

    /**
     * 一个默认参数的@Beans参数
     * 默认对象中，当allDepend对象为false的时候，depend为null
     */
    private static final BeansData DEFAULT = new BeansData("", true, false, null, new Class[0], false, Integer.MAX_VALUE);

    /**
     * 私有构造
     */
    private BeansData(String value, boolean single, boolean allDepend, Depend depend, Class[] constructor, boolean init, int priority) {
        this.value = value;
        this.single = single;
        this.allDepend = allDepend;
        this.depend = depend;
        this.constructor = constructor;
        this.init = init;
        this.priority = priority;
    }

    /**
     * 根据注解获取实例
     */
    public static BeansData getInstance(Beans beans) {
        return getInstance(beans.value(), beans.single(), beans.allDepend(), beans.depend(), beans.constructor(), beans.init(), beans.priority());
    }

    /**
     * 获取默认值实例
     */
    public static BeansData getInstance() {
        return DEFAULT;
    }

    /**
     * 获取实例
     */
    private static BeansData getInstance(String value, boolean single, boolean allDepend, Depend depend, Class[] constructor, boolean init, int priority) {
        return new BeansData(value, single, allDepend, depend, constructor, init, priority);
    }


    public String value() {
        return value;
    }

    public boolean single() {
        return single;
    }

    public boolean allDepend() {
        return allDepend;
    }

    public Depend depend() {
        return depend;
    }

    public Class[] constructor() {
        return constructor;
    }

    //**************** 开放字段的setter方法 ****************//

    public void setValue(String value) {
        this.value = value;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public void setAllDepend(boolean allDepend) {
        this.allDepend = allDepend;
    }

    public void setDepend(Depend depend) {
        this.depend = depend;
    }

    public void setConstructor(Class[] constructor) {
        this.constructor = constructor;
    }

    public int priority() {
        return priority;
    }

    public boolean init() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "BeansData{" +
                "value='" + value + '\'' +
                ", single=" + single +
                ", allDepend=" + allDepend +
                ", depend=" + depend +
                ", constructor=" + Arrays.toString(constructor) +
                ", init=" + init +
                ", priority=" + priority +
                '}';
    }
}
