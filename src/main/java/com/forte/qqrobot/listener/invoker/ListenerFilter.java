package com.forte.qqrobot.listener.invoker;

import com.forte.qqrobot.anno.BlockFilter;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.types.KeywordMatchType;

/**
 * 监听器过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 14:36
 * @since JDK1.8
 **/
public class ListenerFilter {

    /**
     * 过滤
     *
     * @param listenerMethod 监听函数对象
     * @param at             是否被at了
     */
    public boolean filter(ListenerMethod listenerMethod, MsgGet msgGet, boolean at) {
        //如果不存在filter注解，直接放过
        if (!listenerMethod.hasFilter()) {
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

        return allFilter(listenerMethod, msgGet);
    }


    /**
     * 根据BlockFilter注解过滤
     *
     * @param listenerMethod
     * @param at
     */
    public boolean blockFilter(ListenerMethod listenerMethod, MsgGet msgGet, boolean at) {
        //如果不存在filter注解，则使用普通过滤器判断
        if (!listenerMethod.hasBlockFilter()) {
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
//                if (shouldAt) {
//                    String qqCode = BaseConfiguration.getLocalQQCode();
//                    String regex = CQCodeUtil.build().getCQCode_at(qqCode); //"\\[CQ:at,qq="+ qqCode +"\\]";
//                    return filter.keywordMatchType().test(msgGet.getMsg().replaceAll(regex, ""), singleValue);
//                } else {
//                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
//                }
                // 不再移除atCQ码
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
     * 全部的普配流程都走一遍，通过了才行
     */
    private boolean allFilter(ListenerMethod listenerMethod, MsgGet msgGet) {
        return
                wordsFilter(listenerMethod, msgGet)
                        && codeFilter(listenerMethod, msgGet)
                        && groupFilter(listenerMethod, msgGet);
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
//                if (at) {
////                    String qqCode = BaseConfiguration.getLocalQQCode();
////                    String regex = CQCodeUtil.build().getCQCode_at(qqCode); //"\\[CQ:at,qq="+ qqCode +"\\]";
//                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
//                } else {
//                    return filter.keywordMatchType().test(msgGet.getMsg(), singleValue);
//                }
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
                return CODES_MATCH_TYPE.test(qqCode, code);
            } else {
                //有多个
                return filter.mostCodeType().test(qqCode, codes, CODES_MATCH_TYPE);
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
        //获取过滤注解
        Filter filter = listenerMethod.getFilter();

        GroupCodeAble groupCodeAble;
        if (msgGet instanceof GroupCodeAble) {
            groupCodeAble = (GroupCodeAble) msgGet;
        } else {
            //如果并非群号携带者，直接放过
            return true;
        }

        //群号列表
        String[] groups = filter.group();
        //如果获取到的号码为null则不通过
        if (groups.length == 0) {
            //没有要匹配的，直接放过
            return true;
        } else {
            String groupCode = groupCodeAble.getGroupCode();
            if (groupCode == null) {
                return false;
            }
            if (groups.length == 1) {
                //只有一条
                String group = groups[0];
                return CODES_MATCH_TYPE.test(groupCode, group);
            } else {
                return filter.mostGroupType().test(groupCode, groups, CODES_MATCH_TYPE);
            }
        }


    }


}
