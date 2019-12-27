package com.forte.qqrobot.listener.result;

import com.alibaba.fastjson.JSON;

/**
 *
 * 基础转化类，存在部分返回值存在特殊含义。
 * <ul>
 *     <li>如果返回值本身为ListenResult对象则其他参数均无效，以其本身为主。</li>
 *     <li>布尔类型代表函数是否执行成功。</li>
 *     <li>返回值为null的时候代表执行未成功。</li>
 *     <li>数值类型小于0代表执行未成功。</li>
 * </ul>
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BasicResultParser implements ListenResultParser {
    // 使用单例，转化器没有多个实例的必要
    private static final BasicResultParser INSTANCE = new BasicResultParser();
    public static BasicResultParser getInstance(){
        return INSTANCE;
    }
    private BasicResultParser(){}


    /**
     * 转化器
     * @param t 监听函数的执行结果
     * @param sort          排序参数
     * @param isBreak       是否跳过后续函数
     * @param isBreakPlugin 是否跳过后续插件
     * @param e 如果存在异常，此为异常
     * @return listenResult对象
     */
    @Override
    public ListenResult parse(Object t, int sort, boolean isBreak, boolean isBreakPlugin, Throwable e) {
        // 如果存在异常，则必定执行异常
        // 如果结果为null，则同样认为执行失败
        if(e != null || t == null){
            return new ListenResultImpl<>(sort, t, false, isBreak, isBreakPlugin, e);
        }else{
            // 无异常，判断t的类型
            if(t instanceof ListenResult){
                return (ListenResult) t;
            }else{
                // 结果不是ListenResult，判断结果的具体类型
                // 布尔类型代表函数是否执行成功。
                // 返回值为null的时候代表执行未成功。
                // 数值类型小于0代表执行未成功。
                if(t instanceof Boolean){
                    return new ListenResultImpl<>(sort, null, (boolean) t, isBreak, isBreakPlugin, null);
                }else if(t instanceof Number){
                    // 数字类型，判断是否大于0，大于等于0则成功。
                    return new ListenResultImpl<>(sort, null, ((Number) t).intValue() >= 0, isBreak, isBreakPlugin, null);
                }else{
                    // 是一个未知的类型，直接赋予result-data
                    // 并认定为执行成功
                    return new ListenResultImpl<>(sort, t, true, isBreak, isBreakPlugin, null);
                }
            }
        }


    }

}
