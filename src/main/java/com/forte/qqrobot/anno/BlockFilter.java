package com.forte.qqrobot.anno;

import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.beans.types.MostType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 阻塞过滤器，在阻塞的情况下使用的过滤器
 * 功能与普通过滤器一致
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:49
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
public @interface BlockFilter {

    /** 关键词过滤，如果有msg */
    String[] value() default {};

    /** 如果上面的关键词过滤有内容，那么此处为匹配规则，默认为正则匹配 */
    KeywordMatchType keywordMatchType() default KeywordMatchType.REGEX;

    /** 如果上面的关键词过滤有多数内容，那么此处为多数匹配规则，是一个匹配的就好还是全部匹配才行之类的 */
    MostType mostType() default MostType.ANY_MATCH;

    /** 是否需要被at才接收此消息，默认为false <br>
     * ※ 请注意！如果为私聊的话将会永远接收不到消息 */
    boolean at() default false;

}
