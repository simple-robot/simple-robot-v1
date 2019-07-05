package com.forte.qqrobot.beans.cqcode;

import com.forte.qqrobot.beans.types.CQCodeTypes;
import com.forte.qqrobot.exception.CQParamsException;
import com.forte.qqrobot.exception.CQParseException;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * CQCode参数
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 11:42
 * @since JDK1.8
 **/
public class CQCode implements Map<String, String> {

    /** CQ码类型 */
    private final CQCodeTypes CQ_CODE_TYPE;

    /** CQ码参数集 */
    private final Map<String, String> PARAMS;

    /** toString用的字符串 */
    private final String TO_STRING;

    /** 不可以被改变的参数 */
    private final String[] FINAL_PARAMS;

    /**
     * 工厂方法
     * @param type      类型
     * @param params    参数数组，格式：[id=12, type=2]
     * @return CQCode实例对象
     */
    public static CQCode of(CQCodeTypes type, String[] params){
        //使用LinkedHashMap
        Map<String, String> paramsMap = Arrays.stream(params).map(s -> s.split("=")).collect(Collectors.toMap(a -> a[0], a -> a[1], throwingMerger(), LinkedHashMap::new));
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
     * 工厂方法，直接转化CQ码字符串，如果格式并非CQ码字符串则会直接抛出异常
     * @param cqStr cq码字符串
     * @return CQ类
     */
    public static CQCode of(String cqStr){
        if(cqStr.matches(CQCodeTypes.getCqcodeExtractRegex())){
            String[] arr = cqStr.substring(4, cqStr.length() - 1).split("\\,");
            //参数数组
            String[] paramArr = new String[arr.length - 1];
            System.arraycopy(arr, 1, paramArr, 0, paramArr.length);
            return CQCode.of(arr[0], paramArr);
        }else{
            throw new CQParseException("["+ cqStr +"] 无法解析为CQ码对象。");
        }
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
     * 是否获取根据当前真实参数获取ToString字符串
     * 如果为false，则获取此类构建时候确定的CQ码字符串,
     * 如果为true则会根据当前CQCode的真实参数来构建CQ码字符串
     */
    public String toString(boolean realParam){
        if(realParam){
            //构建toString字符串
            StringJoiner joiner = getJoiner(this.CQ_CODE_TYPE.getFunction());
            PARAMS.forEach((k,v) -> joiner.add(k+"="+v));
            return joiner.toString();
        }else
            return toString();
    }

    /**
     * @deprecated 直接获取参数将会无法进行编码转化
     * @see #getParam(String)
     * @see #addParam(String, Object)
     */
    @Deprecated
    public Map<String, String> getParams() {
        return PARAMS;
    }

    /**
     * 获取某个参数
     * @param key key
     * @return  参数
     */
    public String getParam(String key){
        return get(key);
    }


    public String addParam(String key, Object value){
        return put(key, value);
    }

    public String addParam(String key, String value){
        return put(key, value);
    }

    /**
     * 仅子类构造
     */
    protected CQCode(CQCodeTypes cqCodeTypes, Map<String, String> params){
        this.CQ_CODE_TYPE = cqCodeTypes;
        this.PARAMS = params;
        //初始化的时候填入的参数列表不可以被移除
        this.FINAL_PARAMS = params.keySet().toArray(new String[0]);

        //构建toString字符串
        StringJoiner joiner = getJoiner(this.CQ_CODE_TYPE.getFunction());
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

    private static <T> BinaryOperator<T> throwingMerger(){
        return (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }

    //**************************************
    //*          以下为Map接口的实现
    //**************************************


    /**
     * 此CQ码参数的数量
     */
    @Override
    public int size() {
        return PARAMS.size();
    }

    /**
     * 判断此CQ码对象的参数是否为空
     */
    @Override
    public boolean isEmpty() {
        return PARAMS.isEmpty();
    }

    /**
     * 是否存在某种参数
     */
    @Override
    public boolean containsKey(Object key) {
        return PARAMS.containsKey(key);
    }

    /**
     * 是否存在某个参数值
     * 此参数值会自动进行转义
     */
    @Override
    public boolean containsValue(Object value) {
        return PARAMS.containsValue(CQCodeUtil.build().escapeValue(String.valueOf(value)));
    }

    /**
     * 获取某个参数的值
     * 回进行解码
     */
    @Override
    public String get(Object key) {
        return CQCodeUtil.build().escapeValueDecode(PARAMS.get(key));
    }

    /**
     * 放入某个参数
     * 如果是final参数则抛出异常
     */
    @Override
    public String put(String key, String value) {
        for (String final_param : FINAL_PARAMS) {
            if(final_param.equals(key)){
                throw new CQParamsException("参数["+ key +"]值不可被替换。");
            }
        }

        return PARAMS.put(key, CQCodeUtil.build().escapeValue(value));
    }

    /**
     * 放入某个参数
     */
    public String put(String key, Object value){
        return put(key, String.valueOf(value));
    }

    /**
     * 移除掉某个参数
     * 此类被构造的时候所填入的参数不可被移除
     * @throws com.forte.qqrobot.exception.CQParamsException 当移除了不可移除的参数后将会抛出此异常
     */
    @Override
    public String remove(Object key) {
        for (String notKey : FINAL_PARAMS) {
            if(notKey.equals(key)){
                throw new CQParamsException("参数["+ key +"]在CQ码类型["+ CQ_CODE_TYPE +"]中不可被移除。");
            }
        }

        return PARAMS.remove(key);
    }

    /**
     * 添加所有
     * 通过put添加
     */
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach(this::put);
    }

    /**
     * 移除掉所有的参数-除了FINAL参数
     */
    @Override
    public void clear() {
        for (String key : PARAMS.keySet()) {
            for (String finalParam : FINAL_PARAMS) {
                if(!key.equals(finalParam)){
                    PARAMS.remove(key);
                }
            }
        }
    }

    /**
     * 获取键
     */
    @Override
    public Set<String> keySet() {
        return PARAMS.keySet();
    }

    /**
     * 获取值集合
     * 自动解码
     */
    @Override
    public Collection<String> values() {
        return PARAMS.values().stream().map(CQCodeUtil.build()::escapeValueDecode).collect(Collectors.toList());
    }

    /**
     * 获取EntrySet集合
     */
    @Override
    public Set<Entry<String, String>> entrySet() {
        return PARAMS.entrySet().stream().map(e -> new CQCodeEntry(e, FINAL_PARAMS)).collect(Collectors.toSet());
    }


    /**
     * CQ码对象中使用的Entry对象，覆盖原本的Entry对象并进行参数转化
     */
    public static class CQCodeEntry implements Map.Entry<String, String>{

        /** 真正的entry对象 */
        private final Entry<String, String> realEntry;

        /** 不可变参数 */
        private final String[] FINAL_KEYS;

        /** 构造 */
        CQCodeEntry(Entry<String, String> realEntry, String[] finalKeys){
            this.realEntry = realEntry;
            this.FINAL_KEYS = finalKeys;
        }

        /**
         * 获取Key
         */
        @Override
        public String getKey() {
            return realEntry.getKey();
        }

        /**
         * 获取value
         * 自动解码
         */
        @Override
        public String getValue() {
            return CQCodeUtil.build().escapeValueDecode(realEntry.getValue());
        }

        /**
         * 设置值，不可操作final参数
         * 自动转义
         */
        @Override
        public String setValue(String value) {
            for (String finalKey : FINAL_KEYS) {
                if(finalKey.equals(value)){
                    throw new CQParamsException("参数["+ finalKey +"]不可变。");
                }
            }
            return realEntry.setValue(value);
        }

    }


}
