package com.forte.qqrobot.anno;

import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.beans.types.MostType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 监听消息过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 11:25
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
@ByNameType(Filter.ByName.class)
public @interface Filter {

    /**
     * 关键词过滤，如果有msg
     */
    String[] value() default {};

    /**
     * 如果上面的关键词过滤有内容，那么此处为匹配规则，默认为正则匹配
     */
    KeywordMatchType keywordMatchType() default KeywordMatchType.REGEX;

    /**
     * 如果上面的关键词过滤有多数内容，那么此处为多数匹配规则，是一个匹配的就好还是全部匹配才行之类的
     */
    MostType mostType() default MostType.ANY_MATCH;

    /**
     * 是否需要被at才接收此消息，默认为false<br>
     * ※ 请注意！如果为私聊且at参数为true的话的话将会永远接收不到消息
     */
    boolean at() default false;

    //**************** 以下为 v1.2.4-BETA 后更新 ****************//

    /**
     * 如果监听类型可以过滤QQ号，则对qq号进行过滤
     * 如果是不存在QQ号的消息类型，则此参数将会失效。
     * 如果为空数组则为全部匹配
     */
    String[] code() default {};

    /**
     * 与{@link #mostType()} 功能类似，当{@link #code()}存在多个code的时候，判断其匹配规则。
     * 默认为任意存在即可
     */
    MostType mostCodeType() default MostType.ANY_MATCH;

    /**
     * 如果监听类型可以过滤群号，则对群号进行过滤
     * 如果是不存在群号的消息类型，则此参数将会失效
     * 如果为空数组则为全部匹配
     */
    String[] group() default {};

    /**
     * 与{@link #mostType()} 功能类似，当{@link #group()}存在多个group的时候，判断其匹配规则。
     * 默认为任意匹配即可。
     */
    MostType mostGroupType() default MostType.ANY_MATCH;

    //**************** 以下为1.3.5-BETA之后的更新，且尚未实装 ****************//

    /**
     * 当存在群消息的时候，可以对群消息发信人进行权限过滤
     * 尚未实装
     */
    @Deprecated
    PowerType[] permission() default {};

    /**
     * 与{@link #mostType()} 功能类似，当{@link #permission()} 存在多个权限类型的时候，判断其匹配规则。
     * 尚未实装
     */
    @Deprecated
    MostType mostPermissionType() default MostType.ANY_MATCH;


    /**
     * 结构与{@link Filter}一致, 但是枚举类均替换为字符串类型
     */
    @Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
    @Target({ElementType.METHOD}) //接口、类、枚举、注解、方法
    @ByNameFrom(Filter.class)
    @interface ByName {
        /**
         * 关键词过滤，如果有msg
         */
        String[] value() default {};

        /**
         * 如果上面的关键词过滤有内容，那么此处为匹配规则，默认为正则匹配
         */
        @ByNameField(KeywordMatchType.class)
        String keywordMatchType() default "REGEX";

        /**
         * 如果上面的关键词过滤有多数内容，那么此处为多数匹配规则，是一个匹配的就好还是全部匹配才行之类的
         */
        @ByNameField(MostType.class)
        String mostType() default "ANY_MATCH";

        /**
         * 是否需要被at才接收此消息，默认为false<br>
         * ※ 请注意！如果为私聊且at参数为true的话的话将会永远接收不到消息
         */
        boolean at() default false;

        //**************** 以下为 v1.2.4-BETA 后更新 ****************//

        /**
         * 如果监听类型可以过滤QQ号，则对qq号进行过滤
         * 如果是不存在QQ号的消息类型，则此参数将会失效。
         * 如果为空数组则为全部匹配
         */
        String[] code() default {};

        /**
         * 与{@link #mostType()} 功能类似，当{@link #code()}存在多个code的时候，判断其匹配规则。
         * 默认为任意存在即可
         */
        @ByNameField(MostType.class)
        String mostCodeType() default "ANY_MATCH";

        /**
         * 如果监听类型可以过滤群号，则对群号进行过滤
         * 如果是不存在群号的消息类型，则此参数将会失效
         * 如果为空数组则为全部匹配
         */
        String[] group() default {};

        /**
         * 与{@link #mostType()} 功能类似，当{@link #group()}存在多个group的时候，判断其匹配规则。
         * 默认为任意匹配即可。
         */
        @ByNameField(MostType.class)
        String mostGroupType() default "ANY_MATCH";

        //**************** 以下为1.3.5-BETA之后的更新，且尚未实装 ****************//

        /**
         * 当存在群消息的时候，可以对群消息发信人进行权限过滤
         * 尚未实装
         */
        @Deprecated
        @ByNameField(PowerType.class)
        String[] permission() default {};

        /**
         * 与{@link #mostType()} 功能类似，当{@link #permission()} 存在多个权限类型的时候，判断其匹配规则。
         * 尚未实装
         */
        @Deprecated
        @ByNameField(MostType.class)
        String mostPermissionType() default "ANY_MATCH";


    }

}
