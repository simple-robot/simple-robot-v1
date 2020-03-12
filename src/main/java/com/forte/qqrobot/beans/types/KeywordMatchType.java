package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 再{@link com.forte.qqrobot.anno.Filter}中,关键词匹配的时候使用的匹配方式
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 11:27
 * @since JDK1.8
 **/
public enum KeywordMatchType {

    //**************** 正则匹配相关 ****************//

    /** 使用正则规则匹配 */
    REGEX((msg, regex) -> regex.matcher(msg).matches()),

    /** 首尾去空后正则匹配 */
    TRIM_REGEX((msg, regex) -> regex.matcher(msg.trim()).matches()),

    /** 移除掉所有CQ码后正则匹配 */
    RE_CQCODE_REGEX((msg, regex) -> regex.matcher(CQCodeUtil.build().removeCQCodeFromMsg(msg)).matches()),

    /** 移除掉所有CQ码并首尾去空后正则匹配 */
    RE_CQCODE_TRIM_REGEX((msg, regex) -> regex.matcher(CQCodeUtil.build().removeCQCodeFromMsg(msg).trim()).matches()),

    //**************** 相同匹配相关 ****************//

    /** 使用完全相同匹配 */
    EQUALS((msg, regex) -> msg.equals(regex.toString())),

    /** 首尾去空后相同匹配  */
    TRIM_EQUALS((msg, regex) -> msg.trim().equals(regex.toString())),

    /** 移除掉所有CQ码后相同匹配 */
    RE_CQCODE_EQUALS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).equals(regex.toString())),

    /** 移除掉所有CQ码并首尾去空后相同匹配 */
    RE_CQCODE_TRIM_EQUALS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().equals(regex.toString())),

    //**************** 包含匹配相关 ****************//

    /** 包含匹配 */
    CONTAINS((msg, regex) -> msg.contains(regex.toString())),

    /** 去空的包含匹配 */
    TRIM_CONTAINS((msg, regex) -> msg.trim().contains(regex.toString())),

    /** 移除掉所有CQ码后包含匹配 */
    RE_CQCODE_CONTAINS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).contains(regex.toString())),

    /** 移除掉所有CQ码并首尾去空后包含匹配 */
    RE_CQCODE_TRIM_CONTAINS((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().contains(regex.toString())),

    //**************** 开头匹配 ****************//

    /** 首部匹配 */
    STARTS_WITH((msg, regex) -> msg.startsWith(regex.toString())),
    /** 去空的首部匹配 */
    TRIM_STARTS_WITH((msg, regex) -> msg.trim().startsWith(regex.toString())),
    /** 移除掉所有CQ码后首部匹配 */
    RE_CQCODE_STARTS_WITH((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).startsWith(regex.toString())),
    /** 移除掉所有CQ码并首尾去空后首部匹配 */
    RE_CQCODE_TRIM_STARTS_WITH((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().startsWith(regex.toString())),

    //**************** 结尾匹配 ****************//

    /** 尾部匹配 */
    ENDS_WITH((msg, regex) -> msg.endsWith(regex.toString())),
    /** 去空的尾部匹配 */
    TRIM_ENDS_WITH((msg, regex) -> msg.trim().endsWith(regex.toString())),
    /** 移除掉所有CQ码后尾部匹配 */
    RE_CQCODE_ENDS_WITH((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).endsWith(regex.toString())),
    /** 移除掉所有CQ码并首尾去空后尾部匹配 */
    RE_CQCODE_TRIM_ENDS_WITH((msg, regex) -> CQCodeUtil.build().removeCQCodeFromMsg(msg).trim().endsWith(regex.toString())),


    ;

    /**
     * 进行比对
     * @param msg       消息
     * @param keyword   关键词的正则对象
     * @return          比对结果
     */
    public Boolean test(String msg, Pattern keyword){
        return msg != null && filter.test(msg, keyword);
    }

    /**
     * 进行比对
     * @param msg       消息
     * @param keyword   关键词
     * @return          比对结果
     */
    public Boolean test(String msg, String keyword){
        return msg != null && filter.test(msg, Pattern.compile(keyword));
    }

    /**
     * 根据规则而对字符串进行过滤
     */
    final BiPredicate<String, Pattern> filter;

    /**
     * 构造
     * @param filter 匹配规则
     */
    KeywordMatchType(BiPredicate<String, Pattern> filter){
        this.filter = filter;
    }
}
