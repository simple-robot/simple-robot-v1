/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AppendList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.cqcode;

import com.forte.qqrobot.beans.types.CQCodeTypes;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * 多个CQCode或者字符串信息的链接体
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface AppendList {

    /**
     *  拼接一个CharSequence类型对象
     *  如果字符串可以转化为CQCode则会进行转化。
     *      *  一个append中如果包含而不是整个都是的话将不会进行转化。
     *      *  例如：
     *      *  <code>
     *      *      append("hello! [CQ:at,qq=123]");
     *      *      append("       [CQ:at,qq=123]      ");
     *      *      以上情况不会进行转化。
     *      *  </code>
     * */
    AppendList append(CharSequence append);
    /** 对象默认转化为字符串 */
    default AppendList append(Object o){
        return append(String.valueOf(o));
    }
    AppendList append(long append);
    AppendList append(int append);
    AppendList append(double append);
    AppendList append(boolean append);
    AppendList append(char append);


    /** 转化为字符串后会前后去空处理 */
    AppendList appendTrim(CharSequence append);

    /** 批量加载，且都去空处理 */
    AppendList appendTrim(AppendList append);

    /** 拼接另一个AppendList */
    AppendList append(AppendList list);

    /** 获取拼接列表中的CQCode对象 */
    CQCode[] getCQCodes();

    /** 获取某个索引上的值 */
    CharSequence get(int index);

    /** 获取所有的值 */
    CharSequence[] getAll();

    /** 获取长度 */
    int size();

    /** 是否包含 */
    boolean contains(Object b);

    /** 是否包含某个CQ码类型 */
    boolean containsType(CQCodeTypes types);

    /** foreach方法 */
    void forEach(Consumer<CharSequence> each);

    /**
     * 只遍历CQCode的foreach，携带索引参数
     */
    void forEachCQCode(BiConsumer<CQCode, Integer> each);

    @Override
    String toString();

    /** 转化为流对象 */
    Stream<CharSequence> stream();

    /** 转化为仅保留CQCode的流对象 */
    Stream<CQCode> streamCQCode();

    /**
     * 1.3.10 add, 当上一个消息与下一个消息为同类型的时候，合并。
     * 以CQ码实现类来讲，一般用于合并字符串，CQ码不合并。
     * */
    AppendList merge();

}
