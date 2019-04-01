package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * 再{@link com.forte.qqrobot.anno.Filter}中,关键词匹配的时候使用的匹配方式
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 11:27
 * @since JDK1.8
 **/
public enum KeywordMatchType {

    //**************** 正则匹配相关 ****************//

    /** 使用正则规则匹配 */
    REGEX(String::matches),

    /** 开头结尾去空后正则匹配 */
    TRIM_REGEX((msg, regex) -> msg.trim().matches(regex)),

    /** 移除掉所有CQ码后正则匹配 */
    RE_CQCODE_REGEX((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).matches(regex)),

    /** 移除掉所有CQ码并开头结尾去空后正则匹配 */
    RE_CQCODE_TRIM_REGEX((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().matches(regex)),

    //**************** 相同匹配相关 ****************//

    /** 使用完全相同匹配 */
    EQUALS(String::equals),

    /** 开头结尾去空后相同匹配  */
    TRIM_EQUALS((msg, regex) -> msg.trim().equals(regex)),

    /** 移除掉所有CQ码后相同匹配 */
    RE_CQCODE_EQUALS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).equals(regex)),

    /** 移除掉所有CQ码并开头结尾去空后相同匹配 */
    RE_CQCODE_TRIM_EQUALS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().equals(regex)),

    //**************** 包含匹配相关 ****************//

    /** 包含匹配 */
    CONTAINS(String::contains),

    /** 去空的包含匹配 */
    TRIM_CONTAINS((msg, regex) -> msg.trim().contains(regex)),

    /** 移除掉所有CQ码后包含匹配 */
    RE_CQCODE_CONTAINS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).contains(regex)),

    /** 移除掉所有CQ码并开头结尾去空后包含匹配 */
    RE_CQCODE_TRIM_CONTAINS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().contains(regex)),

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
