package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.exception.FilterException;
import com.forte.qqrobot.listener.Filterable;
import com.forte.qqrobot.listener.ListenContext;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * 监听器过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 14:36
 * @since JDK1.8
 **/
public class ListenerFilter implements Closeable {

    private static final QQLogLang LOG_LANG = new QQLogLang("filter");
    private static QQLogLang getLog(){
        return LOG_LANG;
    }

    /**
     * 用户全部自定义的Filter
     */
    private final Map<String, Filterable> diyFilters = new ConcurrentHashMap<>(4);

    /**
     * at判断器, 默认情况下即使用CQ码作为判断
     */
    private static volatile AtomicReference<Function<MsgGet, AtDetection>> AT_DETECTION_FUNCTION = new AtomicReference<>(msg -> () -> CQCodeUtil.build().isAt(msg));


    /**
     * 获取当前的at判断函数
     * @return 当前的at判断函数
     */
    public static Function<MsgGet, AtDetection> getAtDetectionFunction(){
        return AT_DETECTION_FUNCTION.get();
    }

    /**
     * 注册一个新的at判断函数，替换当前函数
     * @param atDetectionFunction at判断函数
     */
    public static void registerAtDetectionFunction(Function<MsgGet, AtDetection> atDetectionFunction){
        AT_DETECTION_FUNCTION.updateAndGet(old -> atDetectionFunction);
    }

    /**
     * 根据当前的at判断函数来更新一个at判断函数
     * @param updateFunction at判断函数的更新函数
     */
    public static void updateAtDetectionFunction(UnaryOperator<Function<MsgGet, AtDetection>> updateFunction){
        AT_DETECTION_FUNCTION.updateAndGet(updateFunction);
    }

    /**
     * 注册一个自定义的filter
     *
     * @param name   filter的name
     * @param filter 过滤规则
     * @return put进去的filter
     */
    public Filterable registerFilter(String name, Filterable filter) {
        Filterable exists = diyFilters.merge(name, filter, (old, val) -> {
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
    public Filterable getFilter(String name) {
        return diyFilters.get(name);
    }

    /**
     * 根据名称列表获取结果
     * @param names 名称列表
     * @return 最终结果
     */
    public Filterable[] getFilters(String... names){
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
    public boolean filter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at, ListenContext context) {
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

        return allFilter(listenerMethod, msgGet, at, context);
    }


    /**
     * 根据BlockFilter注解过滤
     *
     * @param listenerMethod 监听函数
     * @param at             是否被at
     */
    public boolean blockFilter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at, ListenContext context) {
        //如果不存在filter注解，则使用普通过滤器判断
        if (!listenerMethod.hasBlockFilter()) {
            return filter(listenerMethod, msgGet, at, context);
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

        return allFilter(listenerMethod, msgGet, at, context);
    }

    /**
     * 全部的普配流程都走一遍，通过了才行
     */
    private boolean allFilter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at, ListenContext context) {
        // 基础过滤
        return     botFilter(listenerMethod, msgGet)
                && groupFilter(listenerMethod, msgGet)
                && codeFilter(listenerMethod, msgGet)
                && msgFilter(listenerMethod, msgGet)
                // 自定义过滤
                && diyFilter(listenerMethod, msgGet, at, context)
                ;





    }

    /**
     * 使用自定义过滤规则过滤
     * @param listenerMethod 监听函数对象
     * @param msgGet         接收到的消息
     * @param at             是否被at了的判断器
     * @return 过滤结果
     */
    private boolean diyFilter(ListenerMethod listenerMethod, MsgGet msgGet, AtDetection at, ListenContext context){
        Filter filter = listenerMethod.getFilter();
        String[] names = filter.diyFilter();
        if(names.length == 0){
            return true;
        }
        // 封装对象
        com.forte.qqrobot.anno.data.Filter filterData = listenerMethod.getFilterData();

        Filterable[] filters = getFilters(names);
        if(filters.length == 1){
            return filters[0].filter(filterData, msgGet, at, context);
        }else{
            // 有多个
            return filter.mostDIYType().test(filterData, msgGet, at, context, filters);
        }
    }

    /**
     * 关键词过滤与at过滤
     *
     * @param listenerMethod 监听函数
     * @param msgGet         消息封装
     * @return 是否通过
     */
    private boolean msgFilter(ListenerMethod listenerMethod, MsgGet msgGet) {
        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        //获取关键词组
        Pattern[] patternValue = listenerMethod.getPatternValue();
        boolean test = true;
        //如果关键词数量大于1，则进行关键词过滤
        if (patternValue.length > 0) {
            if (patternValue.length == 1) {
                //如果只有一个参数，直接判断
                Pattern singleValue = patternValue[0];
                //如果需要被at，判断的时候移除at的CQ码
                //2019/10/25 不再移除CQ码
                test = filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
            } else {
                //如果有多个参数，按照规则判断
                //根据获取规则匹配
                test = filter.mostType().test(msgGet.getMsg(), patternValue, filter.keywordMatchType());
            }
            return test;
        } else {
            return true;
        }
    }


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
        Pattern[] codes = listenerMethod.getPatternCodeValue();


        if (codes.length == 0) {
            //没有codes，直接放过
            return true;
        } else {
            String qqCode = qqCodeAble.getQQCode();
            //如果获取到的号码为null则通过
            if (qqCode == null) {
                return true;
            }
            if (codes.length == 1) {
                Pattern code = codes[0];
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
        Pattern[] groups = listenerMethod.getPatternGroupValue();
        if (groups.length == 0) {
            //没有要匹配的，直接放过
            return true;
        }

        String groupCode = groupCodeAble.getGroupCode();
        //如果获取到的号码为null则通过
        if (groupCode == null) {
            return true;
        }
        if (groups.length == 1) {
            //只有一条
            Pattern group = groups[0];
            return groupMatchType.test(groupCode, group);
        } else {
            return filter.mostGroupType().test(groupCode, groups, groupMatchType);
        }
    }


    /**
     * bot账号过滤
     *
     * @param listenerMethod 监听函数
     * @param msgGet         消息封装
     * @return 是否匹配
     */
    private boolean botFilter(ListenerMethod listenerMethod, MsgGet msgGet) {
        // 如果获取到的thisCode为null，直接放行
        String thisCode = msgGet.getThisCode();
        if(thisCode == null){
            return true;
        }

        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        // 群号匹配规则
        KeywordMatchType botMatchType = filter.botMatchType();

        //群号列表
        Pattern[] bots = listenerMethod.getPatternBotValue();
        //如果获取到的号码为null则不通过
        if (bots.length == 0) {
            //没有要匹配的，直接放过
            return true;
        }

        if (bots.length == 1) {
            //只有一条
            Pattern bot = bots[0];
            return botMatchType.test(thisCode, bot);
        } else {
            return filter.mostBotType().test(thisCode, bots, botMatchType);
        }
    }

    /**
     * close
     */
    @Override
    public void close() {
        this.diyFilters.clear();
    }
}
