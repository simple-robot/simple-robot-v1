package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGet;

import java.lang.reflect.Method;

/**
 * 监听器过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 14:36
 * @since JDK1.8
 **/
public class ListenerFilter {

    /**
     * 根据Filter注解过滤
     *
     * @param method 方法
     * @param msgGet msgGet参数
     * @param cqCode cqCode参数
     * @return 是否保留
     */
    public Boolean filter(Method method, MsgGet msgGet, CQCode[] cqCode, Boolean at) {
        //过滤，留下没有注解的或注解合格的
        Filter filter = method.getAnnotation(Filter.class);
        if (filter == null) {
            //如果没有此注解，直接放过
            return true;
        } else {
            //否则at，进行判断
            boolean shouldAt = filter.at();
            if (shouldAt && !at) {
                //如果需要被at，而at不为true，则返回false
                return false;
            }
            String[] value = filter.value();
            //如果关键词数量大于1，则进行关键词过滤
            if (value.length > 0) {
                if (value.length == 1) {
                    //如果只有一个参数，直接判断
                    String singleValue = value[0];
                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
                } else {
                    //如果有多个参数，按照规则判断
                    //根据获取规则匹配
                    return filter.mostType().test(msgGet.getMsg(), value, filter.keywordMatchType());
                }
            } else {
                //如果没有关键词，则直接通过
                return true;
            }
        }
    }


    /**
     * 根据BlockFilter注解过滤
     *
     * @param method 方法
     * @param msgGet msgGet参数
     * @param cqCode cqCode参数
     * @return 是否保留
     */
    public Boolean blockFilter(Method method, MsgGet msgGet, CQCode[] cqCode, Boolean at) {
        //过滤，留下没有注解的或注解合格的
        BlockFilter filter = method.getAnnotation(BlockFilter.class);
        if (filter == null) {
            //如果没有此注解，直接放过
            return true;
        } else {
            //否则at，进行判断
            boolean shouldAt = filter.at();
            if (shouldAt && !at) {
                //如果需要被at，而at不为true，则返回false
                return false;
            }
            String[] value = filter.value();
            //如果关键词数量大于1，则进行关键词过滤
            if (value.length > 0) {
                if (value.length == 1) {
                    //如果只有一个参数，直接判断
                    String singleValue = value[0];
                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
                } else {
                    //如果有多个参数，按照规则判断
                    //根据获取规则匹配
                    return filter.mostType().test(msgGet.getMsg(), value, filter.keywordMatchType());
                }
            } else {
                //如果没有关键词，则直接通过
                return true;
            }
        }
    }

}
