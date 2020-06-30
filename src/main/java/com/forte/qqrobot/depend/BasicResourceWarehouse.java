/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BasicResourceWarehouse.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend;

import com.forte.lang.Language;
import com.forte.qqrobot.exception.DependResourceException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础数据资源仓库
 * 此仓库只接收String类型的数据和8大基本数据类型及其封装类
 * 资源保存的时候不可覆盖，如果出现键值相同则抛出异常{@link com.forte.qqrobot.exception.DependResourceException}
 * 可以保存的数据类型：(省略基础数据类型的封装类)
 * <ul>
 * <li>{@link String}</li>
 * <li>byte</li>
 * <li>short</li>
 * <li>int</li>
 * <li>long</li>
 * <li>float</li>
 * <li>double</li>
 * <li>char</li>
 * <li>boolean</li>
 * </ul>
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BasicResourceWarehouse implements Map<String, Object> {

    /**
     * 保存基础数据类型的map
     */
    private final Map<String, Object> warehouse = new ConcurrentHashMap<>();

    /**
     * 可以储存的数据类型数组
     */
    private static final Class[] BasicDataTypes = new Class[]{
            //String类型
            String.class,

            //8大基础数据类型
            byte.class, short.class, int.class, long.class,
            float.class, double.class,
            char.class, boolean.class,

            //8大基础数据类型封装类
            Byte.class, Short.class, Integer.class, Long.class,
            Float.class, Double.class,
            Character.class, Boolean.class,
    };

    //**************** 直接保存基础数据类型 ****************//


    public void put(String key, String value) {
        warehouse.put(key, value);
    }

    public void put(String key, char... charArray) {
        warehouse.put(key, String.valueOf(charArray));
    }

    public void put(String key, byte value) {
        warehouse.put(key, value);
    }

    public void put(String key, short value) {
        warehouse.put(key, value);
    }

    public void put(String key, int value) {
        warehouse.put(key, value);
    }

    public void put(String key, long value) {
        warehouse.put(key, value);
    }

    public void put(String key, float value) {
        warehouse.put(key, value);
    }

    public void put(String key, double value) {
        warehouse.put(key, value);
    }

    public void put(String key, char value) {
        warehouse.put(key, value);
    }

    public void put(String key, boolean value) {
        warehouse.put(key, value);
    }

    public void put(String key, Byte value) {
        warehouse.put(key, value);
    }

    public void put(String key, Short value) {
        warehouse.put(key, value);
    }

    public void put(String key, Integer value) {
        warehouse.put(key, value);
    }

    public void put(String key, Long value) {
        warehouse.put(key, value);
    }

    public void put(String key, Float value) {
        warehouse.put(key, value);
    }

    public void put(String key, Double value) {
        warehouse.put(key, value);
    }

    public void put(String key, Character value) {
        warehouse.put(key, value);
    }

    public void put(String key, Boolean value) {
        warehouse.put(key, value);
    }

    public void putToString(String key, Object objToString) {
        put(key, String.valueOf(objToString));
    }

    //**************** 获取方法 ****************//


    public String getString(String key) {
        return getParse(key, String.class);
    }

    public byte getByteBasic(String key) {
        return getParse(key, byte.class);
    }

    public short getShortBasic(String key) {
        return getParse(key, short.class);
    }

    public int getIntBasic(String key) {
        return getParse(key, int.class);
    }

    public long getLongBasic(String key) {
        return getParse(key, long.class);
    }

    public float getFloatBasic(String key) {
        return getParse(key, float.class);
    }

    public double getDoubleBasic(String key) {
        return getParse(key, double.class);
    }

    public char getCharBasic(String key) {
        return getParse(key, char.class);
    }

    public boolean getBooleanBasic(String key) {
        return getParse(key, boolean.class);
    }

    public Byte getByte(String key) {
        return getParse(key, Byte.class);
    }

    public Short getShort(String key) {
        return getParse(key, Short.class);
    }

    public Integer getInt(String key) {
        return getParse(key, Integer.class);
    }

    public Long getLong(String key) {
        return getParse(key, Long.class);
    }

    public Float getFloat(String key) {
        return getParse(key, Float.class);
    }

    public Double getDouble(String key) {
        return getParse(key, Double.class);
    }

    public Character getChar(String key) {
        return getParse(key, Character.class);
    }

    public Boolean getBoolean(String key) {
        return getParse(key, Boolean.class);
    }

    public String getToString(String key) {
        Object result = warehouse.get(key);
        return result == null ? null : String.valueOf(result);
    }

    //**************** 通用保存方法/获取方法 ****************//

    /**
     * 保存一个基础数据, 返回上一个旧数据
     * 如果返回值为null则说明数据没有保存过
     */
    public Object putBasic(String key, Object value) {
        //判断是否为基础数据，如果不是，直接抛出异常
        if (isBasicType(value)) {
            return warehouse.put(key, value);
        } else {
            // 不是8大基础数据类型
            // [key({0}):value({1})]的值类型[{2}]并非为String或8大基本数据类型(或其封装类)
            throw new DependResourceException("notBasic", key, value, value.getClass());
        }
    }

    /**
     * 获取的同时尝试转化数据类型
     */
    public <T> T getParse(String key, Class<T> parseType) {
        //类型不可为null
        Objects.requireNonNull(parseType);
        Object value = warehouse.get(key);
        if (!isBasicType(parseType)) {
            throw new DependResourceException("notParseType", parseType, Arrays.toString(BasicDataTypes));
        }
        if (value == null) {
            return null;
        }
        //判断是否为字符串
        if (value instanceof String) {
            String strValue = (String) value;
            //如果不是字符串类型，判断并尝试转化
            T result;
            try {
                result = basicToBasic(strValue, parseType);
            } catch (Exception e) {
                throw new DependResourceException("cannotParse", e, key, value.getClass(), parseType);
            }
            return result;

        } else {
            T result;
            //返回值不是字符串，判断并尝试转化
            try {
                result = basicToBasic(value, parseType);
            } catch (Exception e) {
                throw new DependResourceException("cannotParse", e, key, value.getClass(), parseType);
            }
            return result;
        }


    }

    /**
     * 尝试将字符串转化为对应类型，此方法中，异常不做处理
     * 需要保证type为8大基本数据类型及其封装类
     */
    private <T> T StringParseTo(String str, Class<T> type) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(str);
        if (type.equals(short.class) || type.equals(Short.class)) {
            return (T) (Short) Short.parseShort(str);
        } else if (int.class.equals(type) || Integer.class.equals(type)) {
            return (T) (Integer) Integer.parseInt(str);
        } else if (long.class.equals(type) || Long.class.equals(type)) {
            return (T) (Long) Long.parseLong(str);
        } else if (byte.class.equals(type) || Byte.class.equals(type)) {
            return (T) (Byte) Byte.parseByte(str);
        } else if (float.class.equals(type) || Float.class.equals(type)) {
            return (T) (Float) Float.parseFloat(str);
        } else if (double.class.equals(type) || Double.class.equals(type)) {
            return (T) (Double) Double.parseDouble(str);
        } else if (char.class.equals(type) || Character.class.equals(type)) {
            if (str.length() == 1) {
                return (T) (Character) str.charAt(0);
            } else {
                throw new NumberFormatException(Language.format("exception.numberFormat.cannotParse", new Object[]{str, type}));
            }
        } else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
            return (T) (Boolean) Boolean.parseBoolean(str);
        } else if (String.class.equals(type)) {
            return (T) str;
        } else {
            throw new NumberFormatException(Language.format("exception.numberFormat.noCorrespondingType", new Object[]{str, type}));
        }
    }


    /**
     * 将一个未知类型的值转化为某基础类型
     */
    private <T> T basicToBasic(Object basic, Class<T> type) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(basic);
        //如果返回值类型为字符串，直接返回，否则判断
        if (type.equals(String.class)) {
            return (T) String.valueOf(basic);
        } else if (basic instanceof String) {
            return StringParseTo((String) basic, type);
        } else {
            if (basic instanceof Number) {
                Number number = (Number) basic;
                if (short.class.equals(type) || Short.class.equals(type)) {
                    return (T) (Short) number.shortValue();
                } else if (int.class.equals(type) || Integer.class.equals(type)) {
                    return (T) (Integer) number.intValue();
                } else if (long.class.equals(type) || Long.class.equals(type)) {
                    return (T) (Long) number.longValue();
                } else if (byte.class.equals(type) || Byte.class.equals(type)) {
                    return (T) (Byte) number.byteValue();
                } else if (float.class.equals(type) || Float.class.equals(type)) {
                    return (T) (Float) number.floatValue();
                } else if (double.class.equals(type) || Double.class.equals(type)) {
                    return (T) (Double) number.doubleValue();
                } else if (char.class.equals(type) || Character.class.equals(type)) {
                    String str = String.valueOf(number);
                    if (str.length() == 1) {
                        return (T) (Character) str.charAt(0);
                    } else {
                        throw new NumberFormatException(Language.format("exception.numberFormat.cannotParse", number, type));
                    }
                } else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
                    return (T) (Boolean) (number.intValue() == 0);
                } else {
                    throw new NumberFormatException(Language.format("exception.numberFormat.cannotParse", number, type));
                }
            } else {
                //值不是number类型
                //可能是char或者boolean
                if (basic instanceof Character || basic.getClass().equals(char.class)) {
                    char charValue = (char) basic;
                    if (type.equals(short.class) || type.equals(Short.class)) {
                        return (T) (Short) Short.parseShort(String.valueOf(charValue));
                    } else if (type.equals(int.class) || type.equals(Integer.class)) {
                        return (T) (Integer) Integer.parseInt(String.valueOf(charValue));
                    } else if (type.equals(long.class) || type.equals(Long.class)) {
                        return (T) (Long) Long.parseLong(String.valueOf(charValue));
                    } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                        return (T) (Byte) Byte.parseByte(String.valueOf(charValue));
                    } else if (type.equals(float.class) || type.equals(Float.class)) {
                        return (T) (Float) Float.parseFloat(String.valueOf(charValue));
                    } else if (type.equals(double.class) || type.equals(Double.class)) {
                        return (T) (Double) Double.parseDouble(String.valueOf(charValue));
                    } else if (type.equals(char.class) || type.equals(Character.class)) {
                        return (T) (Character) charValue;
                    } else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
                        return (T) (Boolean) (Integer.parseInt(String.valueOf(charValue)) == 0);
                    } else {
                        throw new NumberFormatException(Language.format("exception.numberFormat.cannotParse", charValue, type));
                    }
                } else if (basic instanceof Boolean || basic.getClass().equals(boolean.class)) {
                    boolean booValue = (boolean) basic;
                    //是boolean类型
                    if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        return (T) (Boolean) booValue;
                    } else {
                        throw new NumberFormatException(Language.format("exception.numberFormat.cannotParse", basic, type));
                    }
                } else {
                    throw new RuntimeException(Language.format("exception.numberFormat.cannotParse", basic, type));
                }

            }
        }
    }


    //**************** 判断相关方法 ****************//


    /**
     * 判断类型是不是可用的基础数据类型
     */
    public static boolean isBasicType(Object obj) {
        if (obj == null) {
            return false;
        }
        return isBasicType(obj.getClass());
    }

    /**
     * 判断类型是不是可用的基础数据类型
     */
    public static boolean isBasicType(Class clz) {
        if (clz == null) {
            return false;
        }
        for (Class type : BasicDataTypes) {
            if (type.equals(clz))
                return true;
        }
        return false;
    }

    /**
     * 如果可以添加，则添加
     */
    public boolean putIfCan(String key, Object value) {
        try {
            put(key, value);
            return true;
        } catch (DependResourceException e) {
            return false;
        }

    }

    //**************** 接口实现方法 ****************//


    @Override
    public int size() {
        return warehouse.size();
    }

    @Override
    public boolean isEmpty() {
        return warehouse.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return warehouse.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return warehouse.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return warehouse.get(key);
    }

    /**
     * 保存数据，返回值为上一个旧值
     */
    @Override
    public Object put(String key, Object value) {
        return putBasic(key, value);
    }

    @Override
    public Object remove(Object key) {
        return warehouse.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        warehouse.clear();
    }

    @Override
    public Set<String> keySet() {
        return warehouse.keySet();
    }

    @Override
    public Collection<Object> values() {
        return warehouse.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return warehouse.entrySet();
    }
}
