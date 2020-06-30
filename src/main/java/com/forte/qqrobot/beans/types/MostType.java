/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MostType.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.beans.function.MostTypeFilter;

import java.util.regex.Pattern;

/**
 * 此枚举规定了如果关键词匹配的时候有多个关键词，那么如何匹配
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 15:03
 * @since JDK1.8
 **/
public enum MostType {

    /** 需要全部匹配 */
    EVERY_MATCH((msg, kw, filter) -> {
        boolean b = true;
        for (int i = 0; i < kw.length; i++) {
            if(!filter.test(msg, kw[i])){
                return -1;
            }
        }
        return kw.length-1;
    }),
    /** 任意一个匹配 */
    ANY_MATCH((msg, kw, filter) -> {
        for (int i = 0; i < kw.length; i++) {
            if(filter.test(msg, kw[i])){
                return i;
            }
        }
        return -1;
    }),
    /** 没有匹配 */
    NONE_MATCH((msg, kw, filter) -> {
        for (int i = 0; i < kw.length; i++) {
            if (filter.test(msg, kw[i])) {
                return -1;
            }
        }
        return kw.length-1;
    })
    ;

    /**
     * 传入信息、关键词数组
     * @param msg       消息
     * @param keywords  关键词数据
     * @param keywordMatchType 关键词匹配类型
     * @return  是否匹配
     */
    public boolean test(String msg, Pattern[] keywords, KeywordMatchType keywordMatchType){
        return moreFilter.test(msg, keywords, keywordMatchType.filter) >= 0;
    }

    /**
     * 传入信息、关键词数组
     * @param msg       消息
     * @param keywords  关键词数据
     * @param keywordMatchType 关键词匹配类型
     * @return 所匹配的是哪一个索引。如果mostType类型为{@link MostType#ANY_MATCH}，则返回值为对应匹配索引，
     * 其余，返回值要么是数组最后一位（成功），要么是-1（失败）
     */
    public int testMatch(String msg, Pattern[] keywords, KeywordMatchType keywordMatchType){
        return moreFilter.test(msg, keywords, keywordMatchType.filter);
    }


    /** 传入一个字符串数组为关键词数组，和一个匹配规则，返回一个匹配结果 */
    private final MostTypeFilter moreFilter;

    MostType(MostTypeFilter moreFilter){
        this.moreFilter = moreFilter;
    }


}
