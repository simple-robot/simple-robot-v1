package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.beans.function.MostTypeFilter;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * 此枚举规定了如果关键词匹配的时候有多个关键词，那么如何匹配
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 15:03
 * @since JDK1.8
 **/
public enum MostType {

    /** 需要全部匹配 */
    EVERY_MATCH((msg, kw, filter) -> Arrays.stream(kw).allMatch(k -> filter.test(msg, k))),
    /** 任意一个匹配 */
    ANY_MATCH((msg, kw, filter) -> Arrays.stream(kw).anyMatch(k -> filter.test(msg, k))),
    /** 没有匹配 */
    NONE_MATCH((msg, kw, filter) -> Arrays.stream(kw).noneMatch(k -> filter.test(msg, k)))
    ;

    /**
     * 传入信息、关键词数组、匹配规则进行匹配
     * @param msg       消息
     * @param keywords  关键词数组
     * @param filter    关键词匹配方法
     * @return  是否匹配
     */
    public boolean test(String msg, String[] keywords,  BiPredicate<String, String> filter){
        return moreFilter.test(msg, keywords, filter);
    }

    /**
     * 传入信息、关键词数组
     * @param msg       消息
     * @param keywords  关键词数据
     * @param keywordMatchType 关键词匹配类型
     * @return  是否匹配
     */
    public boolean test(String msg, String[] keywords, KeywordMatchType keywordMatchType){
        return moreFilter.test(msg, keywords, keywordMatchType.filter);
    }


    /** 传入一个字符串数组为关键词数组，和一个匹配规则，返回一个匹配结果 */
    private final MostTypeFilter moreFilter;

    MostType(MostTypeFilter moreFilter){
        this.moreFilter = moreFilter;
    }


}
