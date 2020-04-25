package com.forte.qqrobot;

/**
 * 优先级常量类，由于框架中大部分存在优先级策略的均为JDK升序排序，因此此类定义一些用于排序的常量。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class PriorityConstant {

    /* 优先级最低的倒数10个 */

    public static final int FIRST_LAST = Integer.MAX_VALUE;
    public static final int SECOND_LAST = Integer.MAX_VALUE - 1;
    public static final int THIRD_LAST = Integer.MAX_VALUE - 2;
    public static final int FOURTH_LAST = Integer.MAX_VALUE - 3;
    public static final int FIFTH_LAST = Integer.MAX_VALUE - 4;
    public static final int SIXTH_LAST = Integer.MAX_VALUE - 5;
    public static final int SEVENTH_LAST = Integer.MAX_VALUE - 6;
    public static final int EIGHTH_LAST = Integer.MAX_VALUE - 7;
    public static final int NINTH_LAST = Integer.MAX_VALUE - 8;
    public static final int TENTH_LAST = Integer.MAX_VALUE - 9;

    /* 优先级最高的前十个。从0开始计算。为啥从0开始呢？因为以后我有可能默认最大为0。虽然也说不准 */

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIFTH = 4;
    public static final int SIXTH = 5;
    public static final int SEVENTH = 6;
    public static final int EIGHTH = 7;
    public static final int NINTH = 8;
    public static final int TENTH = 9;


}
