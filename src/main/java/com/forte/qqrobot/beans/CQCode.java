package com.forte.qqrobot.beans;

import com.forte.qqrobot.beans.types.CQCodeTypes;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * CQCode参数
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 11:42
 * @since JDK1.8
 **/
public class CQCode {

    /** CQ码类型 */
    private final CQCodeTypes CQ_CODE_TYPE;
    /** CQ码参数集 */
    private final Map<String, String> PARAMS;

    /** toString用的字符串 */
    private final String TO_STRING;

    /**
     * 工厂方法
     * @param type      类型
     * @param params    参数数组，格式：[id=12, type=2]
     * @return CQCode实例对象
     */
    public static CQCode of(CQCodeTypes type, String[] params){
        Map<String, String> paramsMap = Arrays.stream(params).map(s -> s.split("=")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
        return of(type, paramsMap);
    }

    /**
     * 工厂方法
     * @param typeStr   类型字符串
     * @param params    参数数组，格式：[id=12, type=2]
     * @return CQCode实例对象
     */
    public static CQCode of(String typeStr, String[] params){
        return of(CQCodeTypes.getTypeByFunction(typeStr), params);
    }

    /**
     * 工厂方法
     * @param typeStr   类型字符串
     * @param params    参数列表
     * @return CQCode实例对象
     */
    public static CQCode of(String typeStr, Map<String, String> params){
        return of(CQCodeTypes.getTypeByFunction(typeStr), params);
    }

    /**
     * 工厂方法
     * @param type      类型
     * @param params    参数列表
     * @return CQCode实例对象
     */
    public static CQCode of(CQCodeTypes type, Map<String, String> params){
        return new CQCode(type, params);
    }

    /**
     * 获取CQCode的类型
     * @return CQCode的类型
     */
    public CQCodeTypes getCQCodeTypes() {
        return CQ_CODE_TYPE;
    }


    /**
     * 重写toString
     * @return toString
     */
    @Override
    public String toString() {
        return TO_STRING;
    }

    /**
     * 获取此CQCode的参数
     * @return CQCode的参数
     */
    public Map<String, String> getParams() {
        return PARAMS;
    }

    /**
     * 获取某个参数
     * @param key key
     * @return  参数
     */
    public String getParam(String key){
        return PARAMS.get(key);
    }

    /**
     * 私有构造
     */
    private CQCode(CQCodeTypes cqCodeTypes, Map<String, String> params){
        this.CQ_CODE_TYPE = cqCodeTypes;
        this.PARAMS = params;
        //构建toString字符串
        System.out.println(CQ_CODE_TYPE);
        StringJoiner joiner = getJoiner(CQ_CODE_TYPE.getFunction());
        PARAMS.forEach((k,v) -> joiner.add(k+"="+v));
        this.TO_STRING = joiner.toString();
    }

    /**
     * 为构建toString字符串而获取的StringJoiner对象
     * @param function function字符串
     * @return StringJoiner对象
     */
    private static StringJoiner getJoiner(String function){
        return new StringJoiner(",", "[CQ:", "]").add(function);
    }
}
