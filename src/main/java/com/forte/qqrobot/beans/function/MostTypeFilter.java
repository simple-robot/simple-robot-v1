package com.forte.qqrobot.beans.function;

import java.util.function.BiPredicate;

/**
 * 为枚举{@link com.forte.qqrobot.beans.types.MostType}服务, 作为函数接口使用。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 15:58
 * @since JDK1.8
 **/
@FunctionalInterface
public interface MostTypeFilter {

    /**
     * 传入一个字符串数组为关键词数组，和一个匹配规则，返回一个匹配结果。
     * 一般来讲，msg就是接收到的消息，keywords就是指注解Filter中写的匹配字符串，
     * 而filter就是指单个Filter的匹配规则，例如全匹配啊、正则匹配等等。
     * 当keywords出现多个的时候，通过此函数来规定多个匹配词的时候的整体规则。
     * 例如：只有一个匹配成功就可以，或者需要全部匹配等等。
     * @param msg       文本消息
     * @param keywords  我想要进行过滤的词或者想要匹配的正则的数组
     * @param filter    单个规则匹配的匹配规则
     * @return 是否匹配
     */
    boolean test(String msg, String[] keywords, BiPredicate<String, String> filter);

}
