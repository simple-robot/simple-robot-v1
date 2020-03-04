package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.exception.FilterException;
import com.forte.qqrobot.listener.Filterable;
import com.forte.qqrobot.log.QQLogLang;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听器过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 14:36
 * @since JDK1.8
 **/
public class ListenerFilter {

    private static final QQLogLang LOG_LANG = new QQLogLang("filter");
    private static QQLogLang getLog(){
        return LOG_LANG;
    }

    /**
     * 用户全部自定义的Filter
     */
    private static final Map<String, Filterable> DIY_FILTERS = new ConcurrentHashMap<>(4);

    /**
     * 注册一个自定义的filter
     *
     * @param name   filter的name
     * @param filter 过滤规则
     * @return put进去的filter
     */
    public static Filterable registerFilter(String name, Filterable filter) {
        Filterable exists = DIY_FILTERS.merge(name, filter, (old, val) -> {
            throw new FilterException("exists", name);
        });
        getLog().debug("register", name);
        return exists;
    }

    /**
     * 根据名称获取一个Filter
     *
     * @param name
     * @return
     */
    public static Filterable getFilter(String name) {
        return DIY_FILTERS.get(name);
    }

    /**
     * 根据名称列表获取结果
     * @param names 名称列表
     * @return 最终结果
     */
    public static Filterable[] getFilters(String... names){
        if(names.length == 0){
            return new Filterable[0];
        }

        Filterable[] filterables = new Filterable[names.length];
        for (int i = 0; i < names.length; i++) {
            Filterable filter = getFilter(names[i]);
            if(filter == null){
                throw new FilterException("notfound", names[i]);
            }
            filterables[i] = filter;
        }
        return filterables;
    }

    /**
     * 过滤
     *
     * @param listenerMethod 监听函数对象
     * @param at             是否被at了
     */
    public boolean filter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at) {
        //如果不存在filter注解，直接放过
        if (!listenerMethod.hasFilter()) {
            return true;
        }
        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        boolean shouldAt = filter.at();
        //根据是否需要被at判断
        if (shouldAt && !at.test()) {
            //如果需要被at，而at不为true，则返回false
            return false;
        }

        //**************** 正常判断 ****************//

        return allFilter(listenerMethod, msgGet, at);
    }


    /**
     * 根据BlockFilter注解过滤
     *
     * @param listenerMethod 监听函数
     * @param at             是否被at
     */
    public boolean blockFilter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at) {
        //如果不存在filter注解，则使用普通过滤器判断
        if (!listenerMethod.hasBlockFilter()) {
            return filter(listenerMethod, msgGet, at);
        }
        //获取过滤注解
        BlockFilter filter = listenerMethod.getBlockFilter();


        boolean shouldAt = filter.at();
        //根据是否需要被at判断
        if (shouldAt && !at.test()) {
            //如果需要被at，而at不为true，则返回false
            return false;
        }

        //**************** 正常判断 ****************//

        return allFilter(listenerMethod, msgGet, at);

//        //获取关键词组
//        String[] value = filter.value();
//        //如果关键词数量大于1，则进行关键词过滤
//        if (value.length > 0) {
//            if (value.length == 1) {
//                //如果只有一个参数，直接判断
//                String singleValue = value[0];
//                // 不再移除atCQ码
//                return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
//            } else {
//                //如果有多个参数，按照规则判断
//                //根据获取规则匹配
//                return filter.mostType().test(msgGet.getMsg(), value, filter.keywordMatchType());
//            }
//        } else {
//            return true;
//        }
    }

    /**
     * 全部的普配流程都走一遍，通过了才行
     */
    private boolean allFilter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at) {
        // 基础过滤
        return  wordsFilter(listenerMethod, msgGet)
                && groupFilter(listenerMethod, msgGet)
                && codeFilter(listenerMethod, msgGet)
                // 自定义过滤
                && diyFilter(listenerMethod, msgGet, at)
                ;





    }

    /**
     * 使用自定义过滤规则过滤
     * @param listenerMethod 监听函数对象
     * @param msgGet         接收到的消息
     * @param at             是否被at了的判断器
     * @return 过滤结果
     */
    private boolean diyFilter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at){
        Filter filter = listenerMethod.getFilter();
        String[] names = filter.diyFilter();
        if(names.length == 0){
            return true;
        }
        Filterable[] filters = getFilters(names);
        if(filters.length == 1){
            return filters[0].filter(filter, msgGet, at);
        }else{
            // 有多个
            return filter.mostDIYType().test(filter, msgGet, at, filters);
        }
    }

    /**
     * 关键词过滤与at过滤
     *
     * @param listenerMethod 监听函数
     * @param msgGet         消息封装
     * @return 是否通过
     */
    private boolean wordsFilter(ListenerMethod listenerMethod, MsgGet msgGet) {
        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        //获取关键词组
        String[] value = filter.value();
        //如果关键词数量大于1，则进行关键词过滤
        if (value.length > 0) {
            if (value.length == 1) {
                //如果只有一个参数，直接判断
                String singleValue = value[0];
                //如果需要被at，判断的时候移除at的CQ码
                //2019/10/25 不再移除CQ码
                return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
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
     * QQ号、群号的过滤规则，固定为去空并全匹配
     */
    private static final KeywordMatchType CODES_MATCH_TYPE = KeywordMatchType.TRIM_EQUALS;


    /**
     * QQ号过滤
     *
     * @param listenerMethod 监听函数
     * @param msgGet         消息封装
     * @return 是否通过
     */
    private boolean codeFilter(ListenerMethod listenerMethod, MsgGet msgGet) {
        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        // 匹配规则
        KeywordMatchType codeMatchType = filter.codeMatchType();

        QQCodeAble qqCodeAble;
        if (msgGet instanceof QQCodeAble) {
            qqCodeAble = (QQCodeAble) msgGet;
        } else {
            //如果并非QQ号携带者，直接放过
            return true;
        }

        //获取需要的QQ号
        String[] codes = filter.code();
        if (codes.length == 0) {
            //没有codes，直接放过
            return true;
        } else {
            String qqCode = qqCodeAble.getQQCode();
            //如果获取到的号码为null则不通过
            if (qqCode == null) {
                return false;
            }
            if (codes.length == 1) {
                String code = codes[0];
                return codeMatchType.test(qqCode, code);
            } else {
                //有多个
                return filter.mostCodeType().test(qqCode, codes, codeMatchType);
            }
        }
    }


    /**
     * 群号过滤
     *
     * @param listenerMethod 监听函数
     * @param msgGet         消息封装
     * @return 是否匹配
     */
    private boolean groupFilter(ListenerMethod listenerMethod, MsgGet msgGet) {
        GroupCodeAble groupCodeAble;
        if (msgGet instanceof GroupCodeAble) {
            groupCodeAble = (GroupCodeAble) msgGet;
        } else {
            //如果并非群号携带者，直接放过
            return true;
        }

        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        // 群号匹配规则
        KeywordMatchType groupMatchType = filter.groupMatchType();

        //群号列表
        String[] groups = filter.group();
        //如果获取到的号码为null则不通过
        if (groups.length == 0) {
            //没有要匹配的，直接放过
            return true;
        }

        String groupCode = groupCodeAble.getGroupCode();
        if (groupCode == null) {
            return false;
        }
        if (groups.length == 1) {
            //只有一条
            String group = groups[0];
            return groupMatchType.test(groupCode, group);
        } else {
            return filter.mostGroupType().test(groupCode, groups, groupMatchType);
        }
    }


}
