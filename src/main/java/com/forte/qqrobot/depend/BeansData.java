package com.forte.qqrobot.depend;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;

import java.util.Arrays;

/**
 * beans携带部分的参数封装
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BeansData {

    /** \@Beans注解的参数：value */
    private final String value;

    /** \@Beans注解的参数：single */
    private final boolean single;

    /** 是否全部标记为Depend */
    private final boolean allDepend;

    /** 如果标记，默认的@Depend注解对象 */
    private final Depend depend;

    /** \@Beans注解的参数：constructor */
    private final Class[] constructor;

    /** 一个默认参数的@Beans参数
     *  默认对象中，当allDepend对象为false的时候，depend为null
     * */
    private static final BeansData DEFAULT = new BeansData("", true, false, null ,new Class[0]);

    /** 私有构造 */
    private BeansData(String value, boolean single, boolean allDepend, Depend depend, Class[] constructor) {
        this.value = value;
        this.single = single;
        this.allDepend = allDepend;
        this.depend = depend;
        this.constructor = constructor;
    }

    /** 根据注解获取实例 */
    public static BeansData getInstance(Beans beans){
        return getInstance(beans.value(), beans.single(), beans.allDepend(), beans.depend(), beans.constructor());
    }

    /** 获取默认值实例 */
    public static BeansData getInstance(){
        return DEFAULT;
    }

    /** 获取实例 */
    private static BeansData getInstance(String value, boolean single, boolean allDepend, Depend depend, Class[] constructor){
        if((DEFAULT.value.equals(value)) && (DEFAULT.single() == single) && (DEFAULT.allDepend() == allDepend) && (Arrays.equals(DEFAULT.constructor(), constructor))){
            return DEFAULT;
        }else{
            return new BeansData(value, single, allDepend, depend, constructor);
        }
    }


    public String value(){
        return value;
    }

    public boolean single(){
        return single;
    }

    public boolean allDepend() {
        return allDepend;
    }

    public Depend depend(){
        return depend;
    }

    public Class[] constructor(){
        return constructor;
    }



}
