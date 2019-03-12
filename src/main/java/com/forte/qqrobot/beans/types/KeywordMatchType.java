package com.forte.qqrobot.beans.types;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * 再{@link com.forte.qqrobot.anno.Filter}中,关键词匹配的时候使用的匹配方式
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 11:27
 * @since JDK1.8
 **/
public enum KeywordMatchType {

    /** 使用正则规则匹配 */
    REGEX(String::matches),

    /** 开头结尾去空后正则匹配 */
    TRIM_REGEX((msg, kw) -> msg.trim().matches(kw)),

    /** 使用完全相同匹配 */
    EQUALS(String::equals),

    /** 开头结尾去空后相同匹配  */
    TRIM_EQUALS((msg, kw) -> msg.trim().equals(kw)),

    /** 包含匹配 */
    CONTAINS(String::contains),

    /** 去空的包含匹配 */
    TRIM_CONTAINS((msg, kw) -> msg.trim().contains(kw)),

    ;


    /**
     * 进行比对
     * @param msg       消息
     * @param keyword   关键词
     * @return          比对结果
     */
    public Boolean test(String msg, String keyword){
        return msg != null && filter.test(msg, keyword);
    }

    /**
     * 根据规则而对字符串进行过滤
     */
    final BiPredicate<String, String> filter;

    /**
     * 构造
     * @param filter 匹配规则
     */
    KeywordMatchType(BiPredicate<String, String> filter){
        this.filter = filter;
    }
}
