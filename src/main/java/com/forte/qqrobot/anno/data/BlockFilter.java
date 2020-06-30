/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BlockFilter.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.anno.data;

import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.beans.types.MostType;

/**
 * 注解的参数类
 * @see com.forte.qqrobot.anno.BlockFilter 此注解的封装类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BlockFilter {

    private final String[] value;
    private final KeywordMatchType keywordMatchType;
    private final MostType mostType;
    private final boolean at;
    private final String[] code;

    //**************** 默认值 ****************//

    private static final String[] DEFAULT_VALUE = {};
    private static final KeywordMatchType DEFAULT_KEYWORD_MATCH_TYPE = KeywordMatchType.REGEX;
    private static final MostType DEFAULT_MOST_TYPE = MostType.ANY_MATCH;
    private static final boolean DEFAULT_AT = false;
    private static final String[] DEFAULT_CODE = {};

    private static final BlockFilter DEFAULT = new BlockFilter(
            DEFAULT_VALUE,
            DEFAULT_KEYWORD_MATCH_TYPE,
            DEFAULT_MOST_TYPE,
            DEFAULT_AT,
            DEFAULT_CODE
    );


    private BlockFilter(String[] value, KeywordMatchType keywordMatchType, MostType mostType, boolean at, String[] code) {
        this.value = value;
        this.keywordMatchType = keywordMatchType;
        this.mostType = mostType;
        this.at = at;
        this.code = code;
    }

    /** 工厂方法 */
    public static BlockFilter build(String[] value, KeywordMatchType keywordMatchType, MostType mostType, boolean at, String[] code){
        return new BlockFilter(value, keywordMatchType, mostType, at, code);
    }

    /** 工厂方法 */
    public static BlockFilter build(com.forte.qqrobot.anno.BlockFilter blockFilterAnnotation){
        return build(
                blockFilterAnnotation.value(),
                blockFilterAnnotation.keywordMatchType(),
                blockFilterAnnotation.mostType(),
                blockFilterAnnotation.at(),
                blockFilterAnnotation.code()
        );
    }

    public static BlockFilter build(){
        return DEFAULT;
    }


    public String[] value(){
        return value;
    }

    public KeywordMatchType keywordMatchType(){
        return keywordMatchType;
    }

    public MostType mostType(){
        return mostType;
    }

    public boolean at(){
        return at;
    }

    public String[] code(){
        return code;
    }




}
