package com.forte.qqrobot.anno.data;

import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.beans.types.MostType;

/**
 * 注解的参数类
 * @see com.forte.qqrobot.anno.Filter 此类为此注解的参数封装类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Filter {

    private final String[] value;
    private final KeywordMatchType keywordMatchType;
    private final MostType mostType;
    private final boolean at;
    private final String[] code;
    private final MostType mostCodeType;
    private final String[] group;
    private final MostType mostGroupType;

    //**************** 默认值类型 ****************//

    private static final String[] DEFAULT_VALUE = {};
    private static final KeywordMatchType DEFAULT_KEYWORD_MATCH_TYPE = KeywordMatchType.REGEX;
    private static final MostType DEFAULT_MOST_TYPE = MostType.ANY_MATCH;
    private static final boolean DEFAULT_AT = false;
    private static final String[] DEFAULT_CODE = {};
    private static final MostType DEFAULT_MOST_CODE_TYPE = MostType.ANY_MATCH;
    private static final String[] DEFAULT_GROUP = {};
    private static final MostType DEFAULT_MOST_GROUP_TYPE = MostType.ANY_MATCH;

    /** 默认值 */
    private static final Filter DEFAULT = new Filter(
            DEFAULT_VALUE,
            DEFAULT_KEYWORD_MATCH_TYPE,
            DEFAULT_MOST_TYPE,
            DEFAULT_AT,
            DEFAULT_CODE,
            DEFAULT_MOST_CODE_TYPE,
            DEFAULT_GROUP,
            DEFAULT_MOST_GROUP_TYPE
    );

    /**
     * 构造
     */
    private Filter(String[] value, KeywordMatchType keywordMatchType, MostType mostType, boolean at, String[] code, MostType mostCodeType, String[] group, MostType mostGroupType) {
        this.value = value;
        this.keywordMatchType = keywordMatchType;
        this.mostType = mostType;
        this.at = at;
        this.code = code;
        this.mostCodeType = mostCodeType;
        this.group = group;
        this.mostGroupType = mostGroupType;
    }

    /** 工厂方法 */
    public static Filter build(String[] value, KeywordMatchType keywordMatchType, MostType mostType, boolean at, String[] code, MostType mostCodeType, String[] group, MostType mostGroupType){
        return new Filter(value, keywordMatchType, mostType, at, code, mostCodeType, group, mostGroupType);
    }

    /** 工厂方法 */
    public static Filter build(com.forte.qqrobot.anno.Filter filterAnnotation){
        return build(
                filterAnnotation.value(),
                filterAnnotation.keywordMatchType(),
                filterAnnotation.mostType(),
                filterAnnotation.at(),
                filterAnnotation.code(),
                filterAnnotation.mostCodeType(),
                filterAnnotation.group(),
                filterAnnotation.mostGroupType()
        );
    }

    /** 工厂方法，仅默认值 */
    public static Filter build(){
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

    public MostType mostCodeType(){
        return mostCodeType;
    }

    public String[] group(){
        return group;
    }

    public MostType mostGroupType(){
        return mostGroupType;
    }

}
