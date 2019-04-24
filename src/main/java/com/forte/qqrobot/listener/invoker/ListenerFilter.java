package com.forte.qqrobot.listener.invoker;

import com.forte.forlemoc.SocketResourceDispatchCenter;
import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;

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
     * @param method 方法
     * @param msgGet msgGet参数
     * @param cqCode cqCode参数
     * @return 是否保留
     */
    @Deprecated
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
                    //如果需要被at，判断的时候移除at的CQ码
                    if(shouldAt){
                        String qqCode = SocketResourceDispatchCenter.getLinkConfiguration().getLocalQQCode();
                        String regex = "\\[CQ:at,qq="+ qqCode +"\\]";
                        return filter.keywordMatchType().test(msgGet.getMsg().replaceAll(regex, ""), singleValue);
                    }else{
                        return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
                    }
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
     * 过滤
     * @param listenerMethod
     * @param at
     * @return
     */
    public boolean filter(ListenerMethod listenerMethod, MsgGet msgGet, boolean at){
        //如果不存在filter注解，直接放过
        if(!listenerMethod.hasFilter()){
            return true;
        }
        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        boolean shouldAt = filter.at();
        //根据是否需要被at判断
        if (shouldAt && !at) {
            //如果需要被at，而at不为true，则返回false
            return false;
        }

        //**************** 正常判断 ****************//

        //获取关键词组
        String[] value = filter.value();
        //如果关键词数量大于1，则进行关键词过滤
        if (value.length > 0) {
            if (value.length == 1) {
                //如果只有一个参数，直接判断
                String singleValue = value[0];
                //如果需要被at，判断的时候移除at的CQ码
                if(shouldAt){
                    String qqCode = SocketResourceDispatchCenter.getLinkConfiguration().getLocalQQCode();
                    String regex = "\\[CQ:at,qq="+ qqCode +"\\]";
                    return filter.keywordMatchType().test(msgGet.getMsg().replaceAll(regex, ""), singleValue);
                }else{
                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
                }
            } else {
                //如果有多个参数，按照规则判断
                //根据获取规则匹配
                return filter.mostType().test(msgGet.getMsg(), value, filter.keywordMatchType());
            }
        } else {
            return true;
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
    @Deprecated
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

    /**
     * 根据BlockFilter注解过滤
     * @param listenerMethod
     * @param at
     */
    public boolean blockFilter(ListenerMethod listenerMethod, MsgGet msgGet, boolean at){
        //如果不存在filter注解，则使用普通过滤器判断
        if(!listenerMethod.hasBlockFilter()){
            return filter(listenerMethod, msgGet, at);
        }
        //获取过滤注解
        BlockFilter filter = listenerMethod.getBlockFilter();



        boolean shouldAt = filter.at();
        //根据是否需要被at判断
        if (shouldAt && !at) {
            //如果需要被at，而at不为true，则返回false
            return false;
        }

        //**************** 正常判断 ****************//

        //获取关键词组
        String[] value = filter.value();
        //如果关键词数量大于1，则进行关键词过滤
        if (value.length > 0) {
            if (value.length == 1) {
                //如果只有一个参数，直接判断
                String singleValue = value[0];
                //如果需要被at，判断的时候移除at的CQ码
                if(shouldAt){
                    String qqCode = SocketResourceDispatchCenter.getLinkConfiguration().getLocalQQCode();
                    String regex = "\\[CQ:at,qq="+ qqCode +"\\]";
                    return filter.keywordMatchType().test(msgGet.getMsg().replaceAll(regex, ""), singleValue);
                }else{
                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
                }
            } else {
                //如果有多个参数，按照规则判断
                //根据获取规则匹配
                return filter.mostType().test(msgGet.getMsg(), value, filter.keywordMatchType());
            }
        } else {
            return true;
        }
    }


}
