package com.forte.qqrobot.beans.cqcode;

import com.forte.qqrobot.beans.types.CQCodeTypes;
import com.forte.qqrobot.exception.CQParseException;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre> CQCode封装类
 * <pre> 从核心1.10.2开始，不再进行权限认证。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 11:42
 * @since JDK1.8
 **/
public class CQCode
        implements Map<String, String>,
        CharSequence,
        Comparable<CQCode>,
        java.io.Serializable

{

    /** CQ码类型 */
    private CQCodeTypes cqCodeType;

    /** CQ码类型字符串 */
    private String cqCodeTypeName;

    /** CQ码参数集 */
    private final Map<String, String> params;

    /** toString用的字符串 */
    private String toString;

    private CQCodeUtil util = CQCodeUtil.build();

    /**
     * 工厂方法
     * @param type      类型
     * @param params    参数数组，格式：[id=12, type=2]
     * @return CQCode实例对象
     */
    public static CQCode of(CQCodeTypes type, String[] params){
        return of(type.getFunction(), type, params);
    }

    /**
     * 工厂方法
     * @param type      类型
     * @param params    参数数组，格式：[id=12, type=2]
     * @return CQCode实例对象
     */
    public static CQCode of(String typeName, CQCodeTypes type, String[] params){
        //使用LinkedHashMap
        Map<String, String> paramsMap = Arrays.stream(params).map(s -> {
            String[] split = s.split("=", 2);
            return split.length > 1 ? split : null;
        }).filter(Objects::nonNull).collect(Collectors.toMap(a -> a[0], a -> a[1], throwingMerger(), LinkedHashMap::new));
        return of(typeName, type, paramsMap);
    }

    /**
     * 工厂方法
     * @param typeName   类型字符串
     * @param params    参数数组，格式：[id=12, type=2]
     * @return CQCode实例对象
     */
    public static CQCode of(String typeName, String[] params){
        final CQCodeTypes[] types = CQCodeTypes.getCQCodeTypesByFunction(typeName);
        if(types.length == 1){
            return of(typeName, types[0], params);
        }else if(types.length == 0){
            return of(typeName, CQCodeTypes.defaultType, params);
        }else{
            // 获取参数的key列表
            String[] keyArray = Arrays.stream(params).map(p -> p.split("=", 2)[0]).toArray(String[]::new);
            return of(typeName, CQCodeTypes.getTypeByFunctionAndParams(typeName, keyArray), params);
        }
    }

    /**
     * 工厂方法
     * @param typeStr   类型字符串
     * @param params    参数列表
     * @return CQCode实例对象
     */
    public static CQCode of(String typeStr, Map<String, String> params){
        String[] paramsArray = params.keySet().toArray(new String[0]);
        return of(typeStr, CQCodeTypes.getTypeByFunctionAndParams(typeStr, paramsArray), params);
    }

    /**
     * 工厂方法
     * @param type      类型枚举
     * @param params    参数列表
     * @return CQCode实例对象
     */
    public static CQCode of(CQCodeTypes type, Map<String, String> params){
        return of(type.getFunction(), type, params);
    }

    /**
     * 工厂方法
     * @param type      类型
     * @param params    参数列表
     * @return CQCode实例对象
     */
    public static CQCode of(String typeName, CQCodeTypes type, Map<String, String> params){
        return new CQCode(typeName, type, params);
    }

    /**
     * 工厂方法，直接转化CQ码字符串，如果格式并非CQ码字符串则会直接抛出异常
     * @param cqStr cq码字符串
     * @return CQ类
     */
    public static CQCode of(String cqStr){
        cqStr = cqStr.trim();
        if(checkCq(cqStr)){
            String[] arr = cqStr.substring(4, cqStr.length() - 1).split("\\,");
            //参数数组
            String[] paramArr = new String[arr.length - 1];
            System.arraycopy(arr, 1, paramArr, 0, paramArr.length);
            return CQCode.of(arr[0], paramArr);
        }else{
            throw new CQParseException("cannotParse", cqStr);
        }
    }

    /**
     * 判断是否为一个首尾是[CQ: ... ]的字符串
     */
    private static boolean checkCq(String cqStr){
        return cqStr.startsWith("[CQ:") && cqStr.endsWith("]");
    }


    /**
     * 获取CQCode的类型
     * @return CQCode的类型
     */
    public CQCodeTypes getCQCodeTypes() {
        return cqCodeType;
    }

    /**
     * 获取CQCode的类型
     * @return CQCode的类型
     */
    public String getCQCodeTypesName() {
        return cqCodeType.getFunction();
    }

    /**
     * 获取CQCode的类型。<br>
     * 这是一个名字比较短的方法。
     */
    public CQCodeTypes getType(){
        return getCQCodeTypes();
    }

    public void setCqCodeTypes(CQCodeTypes type){
        this.cqCodeType = type;
        this.cqCodeTypeName = type.getFunction();
        // 更新toString字符串
        updateToString();
    }

    /**
     * 设置cq码的参数
     */
    public void setCqCodeTypeName(String typeName){
        // 尝试获取类型枚举
        final CQCodeTypes typeFunction = CQCodeTypes.getTypeByFunctionAndParams(typeName, params.keySet().toArray(new String[0]));
        this.cqCodeType = typeFunction;
        this.cqCodeTypeName = typeName;
        updateToString();
    }

    /**
     * 重新设置CQCode的type
     * @param type
     */
    public void setType(CQCodeTypes type){
        setCqCodeTypes(type);
    }


    /**
     * 获取CQ码字符串
     * @return toString
     */
    @Override
    public String toString() {
        return toString;
    }

    /**
     * 更新toString的值。
     * 假如你变更了params内的值，updateToString会把额外的参数也更新至toString中。
     */
    public void updateToString(){
        //构建toString字符串
        StringJoiner joiner = getJoiner(this.cqCodeTypeName);
        this.params.forEach((k, v) -> joiner.add(k+"="+v));
        this.toString = joiner.toString();
    }


    /**
     * 是否获取根据当前真实参数获取ToString字符串
     * 如果为false，则获取此类构建时候确定的CQ码字符串,
     * 如果为true则会根据当前CQCode的真实参数来构建CQ码字符串.
     * @deprecated
     *  updateToString()方法存在之后此方法意义不大了。
     * @see #updateToString()
     */
    @Deprecated
    public String toString(boolean realParam){
        if(realParam){
            //构建toString字符串
            StringJoiner joiner = getJoiner(this.cqCodeTypeName);
            params.forEach((k, v) -> joiner.add(k+"="+v));
            return joiner.toString();
        }else {
            return toString();
        }
    }

    /**
     * @deprecated 直接获取参数将会无法进行编码转化
     * @see #getParam(String)
     * @see #addParam(String, Object)
     */
    @Deprecated
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * 获取某个参数
     * 同{@link #get(Object)} 方法
     * @param key key
     * @return  参数
     */
    public String getParam(String key){
        return get(key);
    }

    /**
     * 添加一个参数与值，同{@link #put(String, Object)} 方法
     */
    public String addParam(String key, Object value){
        return put(key, value);
    }

    /**
     * 添加一个参数与值，同{@link #put(String, String)} 方法
     */
    public String addParam(String key, String value){
        return put(key, value);
    }

    /**
     * 获取Stream<Entry>对象
     * 在v1.2.4-BETA之后的版本增加。（不包括v1.2.4-BETA）
     */
    public Stream<Entry<String, String>> stream(){
        return entrySet().stream();
    }

    //**************************************
    //*             以上为API
    //**************************************


    /**
     * 仅子类构造
     */
    protected CQCode(String typeName, CQCodeTypes cqCodeTypes, Map<String, String> params){
        this.cqCodeType = cqCodeTypes;
        this.cqCodeTypeName = typeName;
        this.params = params;
        updateToString();
    }

//    /**
//     * 仅子类构造
//     */
//    protected CQCode(CQCodeTypes cqCodeTypes, Map<String, String> params){
//        this.cqCodeType = cqCodeTypes;
//        this.cqCodeTypeName = cqCodeTypes.getFunction();
//        this.params = params;
//        updateToString();
//    }


    /**
     * 为构建toString字符串而获取的StringJoiner对象
     * @param function function字符串
     * @return StringJoiner对象
     */
    private static StringJoiner getJoiner(String function){
        return new StringJoiner(",", "[CQ:", "]").add(function);
    }

    /**
     * key 冲突的解决方案。
     */
    private static <T> BinaryOperator<T> throwingMerger(){
        return (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }


    /**
     * 拼接一个{@link CharSequence} 实现类，切割符为空格
     * @param append 一个{@link CharSequence} 实现类
     */
    public AppendList append(CharSequence append){
        return new CQAppendList().append(this).append(append);
    }

    /**
     * 拼接一个{@link CharSequence} 实现类, 并指定字符串输出的时候的切割符
     * @param append 拼接一个{@link CharSequence} 实现类
     */
    public AppendList append(CharSequence append, CharSequence split){
        return new CQAppendList(split).append(this).append(append);
    }

    public AppendList append(long append){
        return new CQAppendList().append(this).append(append);
    }
    public AppendList append(int append){
        return new CQAppendList().append(this).append(append);
    }
    public AppendList append(double append){
        return new CQAppendList().append(this).append(append);
    }
    public AppendList append(boolean append){
        return new CQAppendList().append(this).append(append);
    }
    public AppendList append(char append){
        return new CQAppendList().append(this).append(append);
    }
    public AppendList append(long append, CharSequence split){
        return new CQAppendList(split).append(this).append(append);
    }
    public AppendList append(int append, CharSequence split){
        return new CQAppendList(split).append(this).append(append);
    }
    public AppendList append(double append, CharSequence split){
        return new CQAppendList(split).append(this).append(append);
    }
    public AppendList append(boolean append, CharSequence split){
        return new CQAppendList(split).append(this).append(append);
    }
    public AppendList append(char append, CharSequence split){
        return new CQAppendList(split).append(this).append(append);
    }

    //**************************************
    //*         getter & setter
    //**************************************


    //**************************************
    //*          以下为Map接口的实现
    //**************************************


    /**
     * 此CQ码参数的数量
     */
    @Override
    public int size() {
        return params.size();
    }

    /**
     * 判断此CQ码对象的参数是否为空
     */
    @Override
    public boolean isEmpty() {
        return params.isEmpty();
    }

    /**
     * 是否存在某种key
     */
    @Override
    public boolean containsKey(Object key) {
        return params.containsKey(key);
    }

    /**
     * 是否存在某个参数值
     * 此参数值会自动进行转义
     */
    @Override
    public boolean containsValue(Object value) {
        return params.containsValue(util.escapeValue(String.valueOf(value)));
    }

    /**
     * 获取某个参数的值
     * 回进行解码
     */
    @Override
    public String get(Object key) {
        return util.escapeValueDecode(params.get(key));
    }

    /**
     * 放入某个参数
     * 如果是final参数则抛出异常
     * v1.4.1 从此之后不再干预finalParam一类的东西了，依靠用户自觉性。
     */
    @Override
    public String put(String key, String value) {
        return params.put(key, util.escapeValue(value));
    }

    /**
     * 放入某个参数
     */
    public String put(String key, Object value){
        return put(key, String.valueOf(value));
    }

    /**
     * 移除掉某个参数
     */
    @Override
    public String remove(Object key) {
        return params.remove(key);
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
     * 移除掉所有的参数
     */
    @Override
    public void clear() {
        params.clear();
    }

    /**
     * 获取键
     */
    @Override
    public Set<String> keySet() {
        return params.keySet();
    }

    /**
     * 获取值集合
     * 自动解码
     */
    @Override
    public Collection<String> values() {
        return params.values().stream().map(util::escapeValueDecode).collect(Collectors.toList());
    }

    /**
     * 获取EntrySet集合
     */
    @Override
    public Set<Entry<String, String>> entrySet() {
        return params.entrySet().stream().map(CQCodeEntry::new).collect(Collectors.toSet());
    }


    /**
     * CQ码对象中使用的Entry对象，覆盖原本的Entry对象并进行参数转义
     */
    public class CQCodeEntry implements Map.Entry<String, String>{

        /** 真正的entry对象 */
        private final Entry<String, String> realEntry;

        /** 构造 */
        CQCodeEntry(Entry<String, String> realEntry){
            this.realEntry = realEntry;
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
            return util.escapeValueDecode(realEntry.getValue());
        }

        /**
         * 设置值，不可操作final参数
         * 自动转义
         */
        @Override
        public String setValue(String value) {
            return realEntry.setValue(value);
        }

    }

    //**************** 其他接口实现类 ****************//


    @Override
    public int length() {
        return toString.length();
    }

    @Override
    public char charAt(int index) {
        return toString.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return toString.subSequence(start, end);
    }

    @Override
    public int compareTo(CQCode o) {
        return Integer.compare(this.cqCodeType.getSort(), o.cqCodeType.getSort());
    }
}
