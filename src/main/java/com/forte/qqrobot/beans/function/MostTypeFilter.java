package com.forte.qqrobot.beans.function;

import java.util.function.BiPredicate;

/**
 * 为枚举{@link com.forte.qqrobot.beans.types.MostType}服务
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 15:58
 * @since JDK1.8
 **/
public interface MostTypeFilter {

    boolean test(String msg, String[] keywords, BiPredicate<String, String> filter);

}
