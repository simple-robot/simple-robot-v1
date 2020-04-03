package com.forte.qqrobot.utils;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机值获取工具类
 */
public class RandomUtil {

    /* ——————————————————————— getNumber : 获取随机长度字母(仅数字，尽量不要超过int的最大数上限长度) ——————————————————————————— */

    /** 唯一一个random */
    public static Random RANDOM = ThreadLocalRandom.current();

    public static Random getRandom(){
        return RANDOM;
    }

    /**
     * 获取长度为4的随机数
     *
     * @return
     */
    public static int getNumber() {
        return getNumber(4);
    }

    /**
     * 获取指定长度的随机数,如果超过最大值则返回最大值
     * 最大值：2147483647
     *
     * @param length
     * @return
     */
    public static int getNumber(int length) {
        length--;
        double pow = Math.pow(10, length);
        if (length >= 1) {
            //参照算法：random.nextInt(9000)+1000;
            return (int) (getRandom().nextInt((int) (9 * pow)) + pow);
        } else {
            return getRandom().nextInt(10);
        }
    }

    /**
     * 获取一个随机整数
     *
     * @return
     */
    public static int getInteger() {
        return getNumber(1);
    }

    /**
     * 获取某个区间中的随机数[a,b)
     *
     * @return
     */
    public static int getNumber(int a, int b) {
        return getRandom().nextInt(b - a) + a;
    }


    /**
     * 获取某个区间中的随机数[a,b]
     *
     * @param a
     * @param b
     * @return
     */
    public static int getNumber$right(int a, int b) {
        //计算差值
        int bound = a > b ? a - b : b - a;
        return getRandom().nextInt(bound + 1) + a;
    }


    /* ——————————————————————— getCode : 获取随机code(字母和数字) ——————————————————————————— */


    /**
     * 获取随机code，包含数字和字母
     *
     * @param length
     * @return
     */
    public static String getCode(int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 1; i <= length; i++) {
            if (getRandom().nextBoolean()) {
                //0.5的概率为0-9的数字
                s.append(getRandom().nextInt());
            } else {
                //0.5的概率为字母，其中大写0.25，小写0.25
                if (getRandom().nextBoolean()) {
                    //小写
                    s.append(getRandomChar());
                } else {
                    //大写
                    s.append((getRandomChar() + "").toUpperCase());
                }
            }
        }
        return s.toString();
    }

    /**
     * 获取一个4位数的随机code，字母小写
     *
     * @return
     */
    public static String getCode() {
        return getCode(4);
    }


    /* ——————————————————————— getUUID : 获取随机UUID,java.util自带的UUID方法 ——————————————————————————— */


    /**
     * 获取长度为32的UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }


    /* ——————————————————————— getRandomChar : 获取随机字符（单个字母） ——————————————————————————— */

    /**
     * 获取一个随机英文字符，小写
     *
     * @return
     */
    public static char getRandomChar() {
        return (char) (getRandom().nextInt(26) + 97);
    }


    /* ———————————————————— getRandomString : 获取随机字符串 ———————————————————————— */

    /**
     * 获取一串指定长度的随机字符串
     *
     * @param length     字符串长度
     * @param randomCase 是否开启随机大小写
     * @return
     */
    public static String getRandomString(int length, boolean randomCase) {
        char[] crr = new char[length];
        for (int i = 0; i < length; i++) {
            Character randomChar = getRandomChar();
            //如果开启了随机大写，则有概率将字符转为大写 1/2

            if (randomCase) {
                crr[i] = getRandom().nextBoolean() ? randomChar : Character.toUpperCase(randomChar);
            } else {
                crr[i] = randomChar;
            }
        }
        return new String(crr);
    }

    /**
     * 获取一串指定长度的随机字符串，默认大小写随机
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomString(int length) {
        return getRandomString(length, true);
    }


    /**
     * 获取一串长度为32的字符串，默认大小写随机
     *
     * @return
     */
    public static String getRandomString() {
        return getRandomString(32, true);
    }

    /**
     * 数字小数保留
     *
     * @param dnum   需要保留的小数
     * @param length 小数保留位数
     * @return
     */
    public static String toFixed(Number dnum, int length) {
        StringBuilder sb = new StringBuilder("#.");
        //遍历并设置位数
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }

        //返回结果
        String douStr = numFormat(dnum, sb.toString());
        if(douStr.startsWith(".")){
            //如果开头是点，说明首位是0，补位
            douStr = "0" + douStr;
        }
        return douStr;
    }


    /**
     * 自定义数字格式化
     *
     * @param dnum
     * @param formatStr
     * @return
     */
    public static String numFormat(Number dnum, String formatStr) {
        return new DecimalFormat(formatStr).format(dnum);
    }

    /* ———————————————————— getColor : 获取随机颜色 ———————————————————————— */

    /**
     * 返回一个随机颜色
     *
     * @return
     */
    public static Color randomColor() {
        int[] arr = randomColor$intArr();
        return new Color(arr[0], arr[1], arr[2]);
    }

    /**
     * 返回一个长度为三的数组，三位分别代表了颜色的R、G、B
     *
     * @return
     */
    public static int[] randomColor$intArr() {
        final int[] arr = new int[3];
        arr[0] = getRandom().nextInt(256);
        arr[1] = getRandom().nextInt(256);
        arr[2] = getRandom().nextInt(256);
        return arr;
    }

    /**
     * 返回16进制颜色代码
     *
     * @return
     */
    public static String randomColor$hexString() {
        int[] arr = randomColor$intArr();
        StringBuilder sb = new StringBuilder();
        String r = Integer.toHexString(arr[0]);
        r = r.length() == 1 ? '0' + r : r;

        String g = Integer.toHexString(arr[1]);
        g = g.length() == 1 ? '0' + g : g;

        String b = Integer.toHexString(arr[2]);
        b = b.length() == 1 ? '0' + b : b;
        sb.append("#")
                .append(r)
                .append(g)
                .append(b);
        return sb.toString();
    }

    /* ———————————————————— getProbability : 根据概率获取boolean ———————————————————————— */

    /**
     * 根据概率获取boolean，区间：[probL , probR]
     *
     * @param probL 概率百分比区间的左参数，取值范围为0-1之间，对应了0%和100%
     * @param probR 概率百分比区间的右参数，取值范围为0-1之间，对应了0%和100%
     * @return
     */
    public static Boolean getProbability(double probL, double probR) {
        double v = getRandom().nextDouble();
        if (v >= probL && v <= probR) {
            return true;
        }
        return false;
    }

    /**
     * 根据概率获取boolean，区间：[0 , prob]
     * 填入的参数即为概率的百分比
     *
     * @param prob 概率百分比的小数形式，参数范围0-1
     * @return
     */
    public static Boolean getProbability(double prob) {
        return getProbability(0, prob);
    }

    /* ———————————————————— getRandomElement : 从数组或者集合中获取一个随机元素 ———————————————————————— */

    /**
     * 从数组中返回一个随机元素
     *
     * @param trr
     * @param <T>
     * @return
     */
    public static <T> T getRandomElement(T[] trr) {
        return trr[getRandom().nextInt(trr.length)];
    }

    /**
     * 从集合中返回一个随机元素
     *
     * @param trr
     * @param <T>
     * @return
     */
    public static <T> T getRandomElement(List<T> trr) {
        return trr.get(getRandom().nextInt(trr.size()));
    }


}
