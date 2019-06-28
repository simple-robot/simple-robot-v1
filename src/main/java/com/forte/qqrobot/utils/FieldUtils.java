package com.forte.qqrobot.utils;


import java.lang.reflect.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 字段操作工具，提供丰富的方法，以反射的方式从对象中获取值或赋值。<br>
 *  说是字段操作工具，但是不止操作字段
 * 其中:<br>
 * - objectGetter方法可以允许使用多级字段，例如"user.child.name"<br>
 * - getExcelNum方法可以获取Excel中列的数字坐标，例如:"AA" => 27<br>
 * </p>
 * <br>
 * <p>
 * 实现了缓存优化，使得效率大幅上升
 * 由于希望将其作为一个简单的工具类使用，因此全部使用内部类实现
 * <blockquote>
 * <p>2018-12-15 多层级缓存优化-getter测试结果：</p>
 * <footer>
 * 次数：500w<br>
 * 深度：100 <br>
 * -没有缓存的:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >总用时：1635s ; 1635950ms<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >平均用时：.33ms<br>
 * -有缓存的:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >总用时：18s ; 18216ms<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >平均用时：.00ms<br>
 * </footer>
 * </blockquote>
 * <br>
 * <blockquote>
 * <p>2018-12-15至2018-12-16 多层级缓存优化-getter测试结果：</p>
 * <footer>
 * 次数：5亿<br>
 * 深度：200<br>
 * -没有缓存的：<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >总用时：至终止程序为止，用时11 小时 50 分 13 秒
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >控制台可见进度：6.89%左右
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >未执行完成，中途终止
 * -有缓存的：<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * >总用时：3433s ; 3433008ms
 *
 * </footer>
 * </blockquote>
 * </p>
 *
 * @author ForteScarlet
 */
public class FieldUtils {


    //静态代码块加载
    static {

        //加载字母顺序
        Map<String, Integer> wordNum = new HashMap<>(26);

        for (int i = 1; i <= 26; i++) {
            char c = (char) (97 + (i - 1));
            wordNum.put(c + "", i);
        }

        //获取字母顺序表
        //保存
        WORD_NUMBER = wordNum;


        Map<Class, Class> basisTypes = new HashMap<>(8);

        //1、整型
        basisTypes.put(byte.class, Byte.class);
        basisTypes.put(short.class, Short.class);
        basisTypes.put(int.class, Integer.class);
        basisTypes.put(long.class, Long.class);
        //2、浮点型
        basisTypes.put(float.class, Float.class);
        basisTypes.put(double.class, Double.class);
        //3、字符型
        basisTypes.put(char.class, Character.class);
        //4、布尔型
        basisTypes.put(boolean.class, Boolean.class);

        //赋值保存
        BASIS_TYPES_MAP = basisTypes;
    }

    /**
     * 字母顺序表
     */
    private static final Map<String, Integer> WORD_NUMBER;

    /**
     * 单层字段缓存记录，使用线程安全map
     */
    private static final Map<Class, HashMap<String, SingleCacheField>> SINGLE_FIELD_CACHE_MAP = Collections.synchronizedMap(new HashMap<>());

    /**
     * 多层字段缓存记录，使用线程安全map
     */
    private static final Map<Class, HashMap<String, LevelCacheField>> LEVEL_FIELD_CACHE_MAP = Collections.synchronizedMap(new HashMap<>());

    /**
     * 8大基础数据类型的封装类和其对应的基础数据类型
     */
    private static final Map<Class, Class> BASIS_TYPES_MAP;

    /**
     * 8大基础数据类型的class对象数组
     */
    private static final Class[] BASIS_TYPE_ARRAY = new Class[]{
            //1、整型
            byte.class, short.class, int.class, long.class,
            //2、浮点型
            float.class, double.class,
            //3、字符型
            char.class,
            //4、布尔型
            boolean.class,
    };


    /**
     * 8大基础数据类型的包装类的class对象数组
     */
    private static final Class[] BASIS_PACKAGE_TYPE_ARRAY = new Class[]{
            //1、整型
            Byte.class, Short.class, Integer.class, Long.class,
            //2、浮点型
            Float.class, Double.class,
            //3、字符型
            Character.class,
            //4、布尔型
            Boolean.class,
    };

    /**
     * 获取Excel中列的数字坐标<br>
     * 例如:"AA" => 27
     *
     * @param colStr Excel中的列坐标
     * @return 此列坐标对应的数字
     * @author ForteScarlet
     */
    public static long getExcelNum(String colStr) {
        //获取数组
        char[] array = colStr.toCharArray();
        //长度
        int length = array.length;
        //初始数
        long end = 1;

        //倒序遍历,从小位开始遍历
        for (int i = array.length - 1; i >= 0; i--) {
            //字母序号
            int num = WORD_NUMBER.get((array[i] + "").toLowerCase());
            //加成
            int addBuffer = length - i - 1;
            //结果加成
            end += num * (int) (Math.pow(26, addBuffer));
        }

        //返回结果
        return end;
    }

    /**
     * 获取对象的所有字段(包括没有get方法的字段)
     *
     * @param tClass 对象
     * @return
     * @author ForteScarlet
     */
    public static List<Field> getFieldsWithoutGetter(Class<?> tClass) {
        Field[] fs = tClass.getDeclaredFields();
        return Arrays.asList(fs);
    }


    /**
     * 获取对象的所有字段(包括没有get方法的字段)
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<Field> getFieldsWithoutGetter(T t) {
        return getFieldsWithoutGetter(t.getClass());
    }


    /**
     * 获取对象的所有字段名(包括没有get方法的字段)
     *
     * @param t 对象
     * @return
     * @author ForteScarlet
     */
    public static <T> List<String> getFieldsNameWithoutGetter(T t) {
        return getFieldsWithoutGetter(t).stream().map(Field::getName).collect(Collectors.toList());
    }

    /**
     * 获取对象的所有字段
     *
     * @param tClass 对象
     * @return
     * @author ForteScarlet
     */
    public static List<Field> getFieldsWithGetter(Class<?> tClass) {
        Field[] fs = tClass.getDeclaredFields();
        //返回时过滤掉没有get方法的字段
        return Arrays.stream(fs).filter(f -> Arrays.stream(tClass.getMethods()).anyMatch(m -> m.getName().equals("get" + headUpper(f.getName())))).collect(Collectors.toList());
    }

    /**
     * 获取对象的所有字段
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<Field> getFieldsWithGetter(T t) {
        return getFieldsWithGetter(t.getClass());
    }

    /**
     * 获取对象的所有字段名
     *
     * @param t 对象
     * @return
     * @author ForteScarlet
     */
    public static <T> List<String> getFieldsNameWithGetter(T t) {
        return getFieldsWithGetter(t).stream().map(Field::getName).collect(Collectors.toList());
    }


    /**
     * 获取类指定的字段对象，支持多层级获取
     *
     * @param c
     * @param fieldName
     * @return
     */
    public static Field fieldGetter(Class c, String fieldName) {
        //先进行缓存判断,如果缓存中有记录，直接返回
        CacheField cacheField = getCacheField(c, fieldName);
        if (cacheField != null) {
            return cacheField.getField();
        }

        //可以获取多级字段的字段对象
        //使用'.'切割字段名
        String[] split = fieldName.split("\\.");
        if (split.length == 1) {
            //如果长度为1，则说明只有一级字段，直接获取字段对象
            return getField(c, fieldName);
        } else {
            //如果不是1，则说明不止1层字段值
            //当前字段名
            String thisFieldName = split[0];
            //剩下的字段名称拼接
            //移除第一个字段
            List<String> list = Arrays.stream(split).collect(Collectors.toList());
            list.remove(0);
            //拼接剩余
            String otherFieldName = String.join(".", list);
            //递归获取
            //多层级也计入缓存
            Field field = fieldGetter(getFieldClass(c, thisFieldName), otherFieldName);
            //计入缓存
            saveSingleCacheField(c, field, null, null);
            return field;
        }
    }

    /**
     * 获取字段的class类型，支持多层级获取
     *
     * @param c
     * @param fieldName
     * @return
     */
    public static Class fieldClassGetter(Class c, String fieldName) {
        return fieldGetter(c, fieldName).getType();
    }


    /**
     * 获取字段的getter方法,单层级
     *
     * @param whereIn
     * @param fieldName
     * @return
     */
    public static Method getFieldGetter(Class<?> whereIn, String fieldName) {
        //先查询缓存
        Method cacheGetter = getCacheFieldGetter(whereIn, fieldName);
        if (cacheGetter != null) {
            return cacheGetter;
        }

        //获取getter方法
        try {
            Method getter = whereIn.getMethod("get" + headUpper(fieldName));
            //计入缓存
            saveSingleCacheFieldGetter(whereIn, fieldName, getter);
            //返回结果
            return getter;
        } catch (NoSuchMethodException e) {
            //如果出现异常，返回null
            return null;
        }
    }

    /**
     * 获取字段的getter方法
     *
     * @param whereIn 字段所在类
     * @param field   字段
     * @return
     */
    public static Method getFieldGetter(Class<?> whereIn, Field field) {
        //获取getter方法
        return getFieldGetter(whereIn, field.getName());
    }

    /**
     * 获取字段的getter方法
     *
     * @param obj
     * @param field
     * @return
     */
    public static Method getFieldGetter(Object obj, Field field) {
        return getFieldGetter(obj.getClass(), field);
    }


    /**
     * 获取字段的getter方法
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Method getFieldGetter(Object obj, String fieldName) {
        return getFieldGetter(obj.getClass(), fieldName);
    }


    /**
     * 获取字段的setter方法
     *
     * @param whereIn
     * @param fieldName
     * @return
     */
    public static Method getFieldSetter(Class<?> whereIn, String fieldName) {
        //先查询缓存中是否存在
        Method cacheSetter = getCacheFieldSetter(whereIn, fieldName);
        if (cacheSetter != null) {
            return cacheSetter;
        }


        try {
            //获取这个字段的Field对象
            Field field = whereIn.getDeclaredField(fieldName);
            Method setter = whereIn.getMethod("set" + headUpper(fieldName), field.getType());
            //计入缓存
            saveSingleCacheFieldSetter(whereIn, fieldName, setter);
            //返回
            return setter;
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            //如果出现异常，返回null
            return null;
        }
    }

    /**
     * 获取字段的setter方法
     *
     * @param whereIn
     * @param field
     * @return
     */
    public static Method getFieldSetter(Class<?> whereIn, Field field) {
        //先查询缓存
        Method cacheFieldSetter = getCacheFieldSetter(whereIn, field.getName());
        if (cacheFieldSetter != null) {
            return cacheFieldSetter;
        }

        try {
            Method setter = whereIn.getMethod("set" + headUpper(field.getName()), field.getType());
            //计入缓存
            saveSingleCacheFieldSetter(whereIn, field, setter);
            //返回结果
            return setter;
        } catch (NoSuchMethodException e) {
            //如果出现异常，返回null
            return null;
        }
    }

    /**
     * 获取字段的setter方法
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Method getFieldSetter(Object obj, String fieldName) {
        return getFieldSetter(obj.getClass(), fieldName);
    }

    /**
     * 获取字段的setter方法
     *
     * @param obj
     * @param field
     * @return
     */
    public static Method getFieldSetter(Object obj, Field field) {
        return getFieldSetter(obj.getClass(), field);
    }


    /**
     * 获取字段的getter方法，支持多层级获取
     *
     * @param objClass
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     */
    public static Method fieldGetterGetter(Class objClass, String fieldName) throws NoSuchMethodException {
        //判断是否有用“.”分割
        String[] split = fieldName.split("\\.");
        //如果分割后只有一个字段值，说明是单层
        if (split.length == 1) {
            //获取其get方法,返回执行结果
            Method getter = getFieldGetter(objClass, fieldName);
            if (getter == null) {
                //抛出没有此方法异常
                throw new NoSuchMethodException("没有找到类[" + objClass + "]字段[" + fieldName + "]的getter方法");
            }
            //返回获取结果
            return getter;
        } else {
            //否则为多层级，深入获取
            //获取第一个字段名，拼接其余字段名并进行递归处理
            String field = split[0];
            //移除第一个字段
            List<String> list = Arrays.stream(split).collect(Collectors.toList());
            list.remove(0);
            //拼接剩余
            String innerFieldName = String.join(".", list);

            //递归
            return fieldGetterGetter(fieldGetterGetter(objClass, field).getReturnType(), innerFieldName);
        }
    }

    /**
     * 通过对象的getter获取字段数值
     * 支持类似“user.child”这种多层级的获取方式
     * 获取的字段必须有其对应的公共get方法
     *
     * @param t
     * @param fieldName
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Object objectGetter(Object t, String fieldName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return objectGetter(t, t, fieldName, fieldName, 1);
    }

    /**
     * 通过对象的getter获取字段数值
     * 支持类似“user.child”这种多层级的获取方式
     * 获取的字段必须有其对应的公共get方法
     *
     * @param t         被获取的对象
     * @param fieldName 字段名
     * @return
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @author ForteScarlet
     */
    private static Object objectGetter(Object t, Object root, String fieldName, String realFieldName, int level) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        //先查询缓存
        CacheField cacheField = getCacheField(t.getClass(), fieldName);
        if (cacheField != null) {
            //如果有缓存，获取执行结果
            InvokeResult invokeResult = cacheField.fieldValue(t);
            boolean success = invokeResult.isSuccess();
            if (success) {
                return invokeResult.getInvoke();
            }
        }

        //判断是否有用“.”分割
        String[] split = fieldName.split("\\.");
        //如果分割后只有一个字段值，直接返回
        if (split.length == 1) {

            //获取其get方法,返回执行结果
            Method getter = getFieldGetter(t, fieldName);
            if (getter == null) {
                //抛出没有此方法异常
                throw new NoSuchMethodException("没有找到类[" + t.getClass() + "]字段[" + fieldName + "]的getter方法");
            }
            //计入单缓存-当前
            SingleCacheField<?> singleCacheField = saveSingleCacheFieldGetter(t.getClass(), fieldName, getter);

            //如果层数等级与真实字段相同且等级不为1,说明这是多层级字段的最终字段，保存
            if (level != 1 && level == realFieldName.split("\\.").length) {
                saveLevelCacheField(root.getClass(), realFieldName, singleCacheField);
            }


            //返回执行结果
            return getter.invoke(t);
        } else {
            //否则为多层级字段,获取第一个字段名，拼接其余字段名并进行递归处理
            String field = split[0];
            //移除第一个字段
            List<String> list = Arrays.stream(split).collect(Collectors.toList());
            list.remove(0);
            //拼接剩余
            String innerFieldName = String.join(".", list);


            /*
                以下的getter获取方法均为当前字段的，即field字段
             */

            //获取其get方法,返回执行结果
            Method getter = getFieldGetter(t, field);
            if (getter == null) {
                //抛出没有此方法异常
                throw new NoSuchMethodException("没有找到类[" + t.getClass() + "]字段[" + field + "]的getter方法");
            }

            //计入单缓存-当前-同时将getter方法提前计入缓存
            SingleCacheField<?> singleCacheField = saveSingleCacheFieldGetter(t.getClass(), field, getter);


            //此字段的实例对象-直接使用与上面相同的方式获取执行结果而不是使用自我调用
            Object innerObject = getter.invoke(t);

          /*
            保存此多层级字段的getter并保存至多层级缓存
            假如目标是 bean.bean.bean.a
            则当前第一轮为
            realField:  bean.bean.bean.a
            field:      bean
            innerField: bean.bean.a
            则计入多层缓存的应当是bean

            第二轮为
            realField:  bean.bean.bean.a
            field:      bean
            innerField: bean.a
            则计入多层缓存的应当是bean.bean

            可见应记录层数，并截取与层数相同数量的字段层级并进行记录
            层数默认开始为1
           */
//         //计入多层级缓存-当前
            //获取字段名-切割真实字段名
            String levelFieldName = Arrays.stream(realFieldName.split("\\.")).limit(level).collect(Collectors.joining("."));
            saveLevelCacheField(root.getClass(), levelFieldName, singleCacheField);


            //field必定为单层字段，获取field对应的对象，然后使用此对象进行递归
            return objectGetter(innerObject, root, innerFieldName, realFieldName, level + 1);
        }
    }


    /**
     * 通过对象的setter为字段赋值
     * 支持类似“user.child”这种多层级的赋值方式
     * 赋值的字段必须有其对应的公共set方法
     * 如果多层级对象中有非底层级字段为null，将会尝试为其创建一个新的实例
     * <p>
     * TODO 旧
     *
     * @param t         对象
     * @param fieldName 需要赋值的字段
     * @param value     需要赋的值
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void objectSetter(Object t, String fieldName, Object value) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        //判断是否有用“.”分割
        String[] split = fieldName.split("\\.");
        //如果分割后只有一个字段值，直接进行赋值
        if (split.length == 1) {

//            //先查询缓存
//            Method cacheFieldSetter = getCacheFieldSetter(t.getClass() , fieldName);
//            if(cacheFieldSetter != null){
//                //如果缓存中有这个setter，直接使用
//                //赋值
//                MethodUtil.invoke(t , new Object[]{value} , cacheFieldSetter);
//            }

            //获取其set方法,返回执行结果
            String setterName = "set" + FieldUtils.headUpper(fieldName);
            //获取字段的setter方法
            Method setter = getFieldSetter(t, fieldName);

            //如果没有setter,展示异常提醒
            // 直接抛出异常
            if (setter == null) {
                String error = "没有找到[" + t.getClass() + "]中的字段[" + fieldName + "]的setter方法，无法进行赋值";
                throw new NoSuchFieldError(error);
            } else {
                //计入缓存-当前
                saveSingleCacheFieldSetter(t.getClass(), fieldName, setter);
                //赋值
                MethodUtil.invoke(t, new Object[]{value}, setter);
            }

        } else {
            //否则为多层级字段,获取第一个字段名，拼接其余字段名并进行递归处理
            String field = split[0];
            //移除第一个字段
            List<String> list = Arrays.stream(split).collect(Collectors.toList());
            list.remove(0);
            //拼接剩余
            fieldName = String.join(".", list);

            //获取下一层的对象
            Object fieldObject = objectGetter(t, field);
            if (fieldObject == null) {
                //如果为null，创建一个此类型的实例
                fieldObject = getFieldClass(t, field).newInstance();
                //并为此对象赋值
                objectSetter(t, field, fieldObject);
            }
            //寻找下一层字段
            objectSetter(fieldObject, fieldName, value);
        }
    }

    /**
     * 通过对象的setter为字段赋值
     * 支持类似“user.child”这种多层级的赋值方式
     * 赋值的字段必须有其对应的公共set方法
     * 如果多层级对象中有非底层级字段为null，将会尝试为其创建一个新的实例
     *
     * @param t
     * @param fieldName
     * @param param
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Deprecated
    public static void objectSetter2(Object t, String fieldName, Object param) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // TODO 发现bug，此方法会导致缓存无法储存
        objectSetter(t, t, fieldName, fieldName, 1, param);
    }

    /**
     * 通过对象的setter为字段赋值
     * 支持类似“user.child”这种多层级的赋值方式
     * 赋值的字段必须有其对应的公共set方法
     * 如果多层级对象中有非底层级字段为null，将会尝试为其创建一个新的实例
     *
     * @param t
     * @param root
     * @param fieldName
     * @param realFieldName
     * @param level
     * @param param
     */
    @Deprecated
    private static void objectSetter(Object t, Object root, String fieldName, String realFieldName, int level, Object param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        // TODO 实现缓存的setter方法
        // TODO 存在严重bug，此方法会导致缓存无法储存和获取，导致getter效率大幅度下降
        //先查询缓存
        CacheField cacheField = getCacheField(t.getClass(), fieldName);
        if (cacheField != null) {
            //如果有缓存，获取执行结果
            InvokeResult invokeResult = cacheField.fieldValueSet(t, param);
            if (invokeResult.isSuccess()) {
                //如果赋值成功，结束方法
                return;
            }
        }
        //判断是否有用“.”分割
        String[] split = fieldName.split("\\.");
        //如果分割后只有一个字段值，直接进行赋值
        if (split.length == 1) {
            //单层字段，获取setter方法
            Method setter = getFieldSetter(t, fieldName);
            if (setter == null) {
                //如果没有setter，抛出异常
                throw new NoSuchMethodException("没有找到类[" + t.getClass() + "]字段[" + fieldName + "]的setter方法");
            }


            //如果有此方法，计入缓存-当前
            SingleCacheField<?> singleCacheField = saveSingleCacheFieldSetter(t.getClass(), fieldName, setter);
            //如果层数等级与真实字段相同且等级不为1,说明这是多层级字段的最终字段，保存
            if (level != 1 && level == realFieldName.split("\\.").length) {
                saveLevelCacheField(root.getClass(), realFieldName, singleCacheField);
            }

            //执行赋值
            MethodUtil.invoke(t, new Object[]{param}, setter);
        } else {
            //否则为多层字段，获取第一个字段名
            String field = split[0];
            //移除第一个字段
            List<String> list = Arrays.stream(split).collect(Collectors.toList());
            list.remove(0);
            //拼接剩余
            String newFieldName = String.join(".", list);


             /*
                以下的setter获取方法均为当前字段的，即field字段
             */

            //获取其get方法和set方法,返回执行结果
            //获取setter方法
            Method setter = getFieldSetter(t, field);
            if (setter == null) {
                //抛出没有此方法异常
                throw new NoSuchMethodException("没有找到类[" + t.getClass() + "]字段[" + field + "]的setter方法");
            }

            //计入单缓存-当前-同时将setter方法提前计入缓存
            SingleCacheField<?> singleCacheField = saveSingleCacheFieldSetter(t.getClass(), field, setter);

            //获取此字段的实例对象，使用objectGetter方法，可以省去一部计入缓存的步骤
            Object fieldInstance = objectGetter(t, field);

            //如果当前字段的值为null，赋值
            if (fieldInstance == null) {
                fieldInstance = getFieldClass(t, field).newInstance();
                //为当前字段对象赋一个新的值
                objectSetter(t, field, fieldInstance);

            }

            //将setter计入缓存
            //计入多层级缓存-当前
            //获取字段名-切割真实字段名
            String levelFieldName = Arrays.stream(realFieldName.split("\\.")).limit(level).collect(Collectors.joining("."));
            saveLevelCacheField(root.getClass(), levelFieldName, singleCacheField);


            //寻找下一层字段
            objectSetter(fieldInstance, root, newFieldName, realFieldName, level + 1, param);

        }

    }


    /**
     * 获取对象指定字段对象
     *
     * @param object    对象的class对象
     * @param fieldName 字段名称
     */
    public static Field getField(Object object, String fieldName) {
        return getField(object.getClass(), fieldName);
    }


    /**
     * 获取类指定字段对象
     *
     * @param objectClass 类的class对象
     * @param fieldName   字段名称
     */
    public static Field getField(Class objectClass, String fieldName) {
        //反射获取全部字段
        Field[] declaredFields = objectClass.getDeclaredFields();
        //遍历寻找此字段
        for (Field f : declaredFields) {
            //如果找到了, 返回结果
            if (f.getName().equals(fieldName)) {
                return f;
            }
        }

        //没有找到，进入父类寻找
        Class superclass = objectClass.getSuperclass();
        //没有父类，是Object类，直接返回null
        if (superclass == null) {
            return null;
        } else {
            return getField(superclass, fieldName);
        }

    }


    /**
     * 获取指定类字段的类型class对象
     *
     * @param objectClass 类class对象
     * @param fieldName   字段名称
     */
    public static Class getFieldClass(Class objectClass, String fieldName) {
        Field field = getField(objectClass, fieldName);
        return Optional.ofNullable(field).map(Field::getType).orElseThrow(() -> new RuntimeException(new NoSuchFieldException()));
    }

    /**
     * 获取类指定字段的class对象
     *
     * @param object    类实例
     * @param fieldName 字段名称
     * @return
     */
    public static Class getFieldClass(Object object, String fieldName) {
        return getFieldClass(object.getClass(), fieldName);
    }


    /**
     * 获取类中全部的字段
     * @param type      Class对象
     * @param withSuper 是否获取父类中的全部
     * @return
     */
    public static Field[] getFields(Class type, boolean withSuper){
        return getFieldsStream(type, withSuper).toArray(Field[]::new);
    }

    /**
     * 获取类中全部的字段，转化为list
     * @param type      Class对象
     * @param withSuper 是否获取父类中的全部
     */
    public static List<Field> getFieldsList(Class type, boolean withSuper){
        return getFields(type, withSuper, Collectors.toList());
    };

    /**
     * 获取类中全部的字段，转化为set
     * @param type      Class对象
     * @param withSuper 是否获取父类中的全部
     */
    public static Set<Field> getFieldsSet(Class type, boolean withSuper){
        return getFields(type, withSuper, Collectors.toSet());
    }

    /**
     * 获取类中的全部字段，并根据字段名分组
     */
    public static Map<String, List<Field>> getFieldsGroupByName(Class type, boolean withSuper){
        return getFields(type, withSuper, Collectors.groupingBy(Field::getName, Collectors.toList()));
    }

    /**
     * 自定义流转化
     * @param type      Class对象
     * @param withSuper 是否获取父类中的全部
     * @param collector Stream流转化函数
     */
    public static <A, R> R getFields(Class type, boolean withSuper, Collector<? super Field, A, R> collector){
        return getFieldsStream(type, withSuper).collect(collector);
    }

    /**
     * 自定义流转化
     * @param type          Class对象
     * @param withSuper     是否获取父类中的全部
     * @param supplier      Stream流转化函数
     * @param accumulator   Stream流转化函数
     * @param combiner      Stream流转化函数
     */
    public static <A, R> R getFields(Class type, boolean withSuper,
                                     Supplier<R> supplier,
                                     BiConsumer<R, ? super Field> accumulator,
                                     BiConsumer<R, R> combiner){
        return getFieldsStream(type, withSuper).collect(supplier, accumulator, combiner);
    }

    /**
     * 获取类中全部的字段
     * @param type      Class对象
     * @param withSuper 是否获取父类中的全部
     * @return 获取到的全部字段
     */
    public static Stream<Field> getFieldsStream(Class type, boolean withSuper) {
        if (withSuper) {
            //如果还要获取父类的，获取父类
            Class superClass = type.getSuperclass();
            if (superClass == null) {
                //没有父类，是Object类型
                //Object 类型没有字段，直接返回
                return Stream.empty();
            } else {
                //有父类，获取父类
                Stream<Field> fields = getFieldsStream(superClass, true);
                return Stream.concat(fields, Arrays.stream(type.getDeclaredFields()));
            }
        } else {
            //获取全部字段
            return Arrays.stream(type.getDeclaredFields());
        }
    }

    /**
     * 通过Class对象判断是否存在此字段
     *
     * @param tClass
     * @param field
     * @return
     */
    public static boolean isFieldExist(Class<?> tClass, String field) {
        //判断是否为多层级字段
        String[] split = field.split("\\.");
        if (split.length == 1) {
            //如果只有一个，直接获取
            Field getField = null;
            try {
                getField = getField(tClass, field);
            } catch (Exception ignored) {
            }
            //如果存在，返回true，否则返回false
            return getField != null;
        } else {
            //否则，存在多层级字段，先获取第一个字段并获得其类型，然后通过此类型的Class对象进一步判断
            String firstField = split[0];
            //获取字段对象
            Field getField = null;
            try {
                getField = getField(tClass, firstField);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //如果获取失败，直接返回false
            if (getField == null) {
                return false;
            }

            Class<?> firstFieldType = getField.getType();
            //拼接剩余
            //移除第一个字段
            List<String> list = Arrays.stream(split).collect(Collectors.toList());
            list.remove(0);
            //拼接剩余
            String fieldName = String.join(".", list);
            //获取当前字段的Class对象
            Class fieldClass = getFieldClass(tClass, firstField);
            //递归
            return isFieldExist(fieldClass, fieldName);
        }
    }

    /**
     * 通过对象实例判断字段是否存在
     *
     * @param obj   实例对象
     * @param field 查询字段
     * @return
     * @throws NoSuchFieldException
     */
    public static boolean isFieldExist(Object obj, String field) {
        return isFieldExist(obj.getClass(), field);
    }


    /**
     * 获取一个list字段的泛型类型<br>
     * 这个字段必须是一个list类型的字段！
     *
     * @param listField 字段
     * @throws ClassNotFoundException 如果泛型类型未找到或多于1个
     */
    public static Class getListFieldGeneric(Field listField) throws ClassNotFoundException {

        ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
        Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
        if (listActualTypeArguments.length == 0) {
            //如果没有数据
            return null;
        } else if (listActualTypeArguments.length == 1) {
            //如果只有一种类型
            String typeName = listActualTypeArguments[0].getTypeName();
            //如果此类型存在泛型，移除泛型
            typeName = typeName.replaceAll("<[\\w\\.\\, ]+>", "");
            return Class.forName(typeName);
        } else {
            //如果多个类型，直接返回Object类型
            //不返回了，抛出异常
//            return Object.class;
            throw new ClassNotFoundException("more than one types.");
        }
    }

    /**
     * 获取数组的组件类型
     *
     * @param trr
     * @param <T>
     * @return
     */
    public static <T> Class getArrayGeneric(T[] trr) {
        //获取组件类型
        return trr.getClass().getComponentType();
    }

    /**
     * 获取数组的组件类型
     *
     * @param <T>
     * @return
     */
    public static <T> Class getArrayGeneric(Class<T> tClass) {
        return tClass.getComponentType();
    }


    /**
     * 获取一个list字段的泛型类型<br>
     * 这个字段必须是一个list类型的字段！
     *
     * @param c
     * @param fieldName
     * @return
     * @throws ClassNotFoundException
     */
    public static Class getListFieldGeneric(Class c, String fieldName) throws ClassNotFoundException {
        return getListFieldGeneric(fieldGetter(c, fieldName));
    }

    /**
     * 获取一个list字段的泛型类型<br>
     * 这个字段必须是一个list类型的字段！
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws ClassNotFoundException
     */
    public static Class getListFieldGeneric(Object obj, String fieldName) throws ClassNotFoundException {
        return getListFieldGeneric(obj.getClass(), fieldName);
    }


    /**
     * 判断一个Class对象是否为另一个对象的实现类
     *
     * @param child      进行寻找的子类
     * @param findFather 被寻找的父类
     * @return
     */
    public static boolean isChild(Class child, Class findFather) {
        Objects.requireNonNull(child, "the first parameter [child] can not be null");
        Objects.requireNonNull(findFather, "the second parameter [findFather] can not be null");
        //如果自身就是这个类，直接返回true
        if (child.equals(findFather)) {
            return true;
        }

        //如果是基础数据类型或基础数据类型对应的封装类型，返回true
        if (isBasis(child, findFather)) {
            return true;
        }

        /*
            两个方向，一个是向父继承类递归，一个是向接口递归
         */
        //子类继承的父类
        Class superClass = child.getSuperclass();
        //子类实现的接口
        Class[] interfaces = child.getInterfaces();
        //如果全部为null，直接返回false
        if (superClass == null && interfaces.length == 0) {
            return false;
        }
        //进行判断-先对当前存在的两类型进行判断
        if (superClass != null && superClass.equals(findFather)) {
            //如果发现了，返回true
            return true;
        }

        //遍历接口并判断
        for (Class interClass : interfaces) {
            if (interClass.equals(findFather)) {
                return true;
            }
        }

        //如果当前的没有发现，递归查询
        //如果没有发现，递归父类寻找
        if (superClass != null && isChild(superClass, findFather)) {
            return true;
        }

        //如果父类递归没有找到，进行接口递归查询
        //遍历
        for (Class interClass : interfaces) {
            if (isChild(interClass, findFather)) {
                return true;
            }
        }

        //未查询到
        return false;
    }


    /**
     * 判断一个Class对象是否为另一个对象的实现类
     *
     * @param child      进行寻找的子类的实现类
     * @param findFather 被寻找的父类
     * @return
     */
    public static boolean isChild(Object child, Class findFather) {
        return isChild(child.getClass(), findFather);
    }


    /**
     * 判断两个类是否为基础数据类型
     *
     * @param child
     * @param findFather
     * @return
     */
    private static boolean isBasis(Class child, Class findFather) {
        //如果双方有任意类不在基础数据类型或其封装类型中，直接false
        if (
                (
                        Arrays.stream(BASIS_TYPE_ARRAY).noneMatch(bc -> bc.equals(child))
                                &&
                                Arrays.stream(BASIS_PACKAGE_TYPE_ARRAY).noneMatch(bpc -> bpc.equals(child))
                )
                        ||
                        (
                                Arrays.stream(BASIS_TYPE_ARRAY).noneMatch(bc -> bc.equals(findFather))
                                        &&
                                        Arrays.stream(BASIS_PACKAGE_TYPE_ARRAY).noneMatch(bpc -> bpc.equals(findFather))
                        )

        ) {
            return false;
        }

        //**************** 已经保证双方至少都是基础数据类型或者其封装类了 ****************//

        //如果是封装类型，则一定能拿到，获取不到说明是基础类型，不变
        Class childBasis = BASIS_TYPES_MAP.getOrDefault(child, child);

        //同上
        Class findFatherBasis = BASIS_TYPES_MAP.getOrDefault(findFather, findFather);


        return childBasis.equals(findFatherBasis);
    }

    /**
     * 判断类型是否为基础数据类型或封装类
     * @param type
     * @return
     */
    public static boolean isBasic(Class type){
        if(type == null){
            return false;
        }
        for (Class basic : BASIS_TYPE_ARRAY) {
            if(type.equals(basic)){
                return true;
            }
        }

        for (Class basic : BASIS_PACKAGE_TYPE_ARRAY) {
            if(type.equals(basic)){
                return true;
            }
        }

        return false;
    }

    /**
     * 将基础数据类型封装为其对应的封装类
     */
    public static  Class basicToBox(Class type){
        return BASIS_TYPES_MAP.getOrDefault(type, type);
    }

    /**
     * 单词开头大写
     *
     * @param str
     * @return
     * @author ForteScarlet
     */
    public static String headUpper(String str) {
        //拿到第一个字母
        String head = (str.charAt(0) + "").toUpperCase();
        //拿到其他
        String body = str.substring(1);
        return head + body;
    }

    /**
     * 单词开头小写
     *
     * @author ForteScarlet
     */
    public static String headLower(String str) {
        //拿到第一个字母
        String head = (str.charAt(0) + "").toLowerCase();
        //拿到其他
        String body = str.substring(1);
        return head + body;
    }


    /**
     * 获取类名
     *
     * @param c 类
     * @return 类名
     * @author ForteScarlet
     * @deprecated 有方法直接获取的: {@link Class#getSimpleName()}
     */
    @Deprecated
    public static String getClassName(Class<?> c) {
        String name = c.getName();
        String[] split = name.split("\\.");
        return split[split.length - 1];
    }

    /**
     * 通过对象获取类名
     *
     * @param o
     * @return
     * @author ForteScarlet
     */
    public static String getClassName(Object o) {
        return getClassName(o.getClass());
    }


    /**
     * 只要传入的参数中任意一个出现了null则会抛出空指针异常
     *
     * @param all
     */
    public static void allNonNull(Object... all) {
        for (Object o : all) {
            if (o == null) {
                throw new NullPointerException();
            }
        }
    }


    /**
     * 获取方法名，如果是个getter规则方法名则移除get并开头小写, 与{@link #getMethodNameWithoutSetter(Method)}类似
     *
     * @param method 方法
     * @return 方法名
     */
    public static String getMethodNameWithoutGetter(Method method) {
        //获取方法名
        String name = method.getName();
        //判断是否为get开头且去掉get之后首字母大写
        if (name.startsWith("get") && Character.isUpperCase(name.substring(3).charAt(0))) {
            //如果是，去掉get并开头小写
            return headLower(name.substring(3));
        } else {
            //否则原样返回
            return name;
        }
    }

    /**
     * 获取方法名，如果是个getter规则方法名则移除get并开头小写, 与{@link #getMethodNameWithoutGetter(Method)}类似
     *
     * @param method 方法
     * @return 方法名
     */
    public static String getMethodNameWithoutSetter(Method method) {
        //获取方法名
        String name = method.getName();
        //判断是否为get开头且去掉set之后首字母大写
        if (name.startsWith("set") && Character.isUpperCase(name.substring(3).charAt(0))) {
            //如果是，去掉get并开头小写
            return headLower(name.substring(3));
        } else {
            //否则原样返回
            return name;
        }
    }

    /**
     * 获取方法名，如果是个getter或setter规则方法名则移除get或set并开头小写
     *
     * @param method 方法
     * @return 方法名
     * @see #getMethodNameWithoutGetter(Method)
     * @see #getMethodNameWithoutSetter(Method)
     */
    public static String getMethodNameWithoutGetterAndSetter(Method method) {
        //获取方法名
        String name = method.getName();
        //判断是否为get开头且去掉get之后首字母大写
        if ((name.startsWith("get") || name.startsWith("set")) && Character.isUpperCase(name.substring(3).charAt(0))) {
            //如果是，去掉get并开头小写
            return headLower(name.substring(3));
        } else {
            //否则原样返回
            return name;
        }
    }




    /* —————————————————————————————————————— 缓存字段接口 ———————————————————————————————————— */

    /**
     * 缓存字段的接口，定义获取一个缓存字段的值的方法
     *
     * @param <T> 字段所属的类型
     */
    private static interface CacheField<T> {
        /**
         * 获取一个缓存字段的值
         *
         * @param object 用于获取字段值的实例
         * @return
         */
        InvokeResult fieldValue(T object);

        /**
         * 为缓存字段的值赋值
         *
         * @param object
         * @param param
         * @return
         */
        InvokeResult fieldValueSet(T object, Object param);

        /**
         * 获取Getter方法
         *
         * @return
         */
        Method getGetter();

        /**
         * 获取Setter方法
         *
         * @return
         */
        Method getSetter();

        /**
         * 获取字段对象
         *
         * @return
         */
        Field getField();

    }

    /* —————————————————————————————————————— 单缓存字段相关方法 *基本全部为本类内部使用* ———————————————————————————————————— */


    /**
     * 向缓存中增加一个字段记录并返回这个新的缓存字段
     *
     * @param field
     * @param fieldWhereClass
     * @param getter
     * @param setter
     */
    private static <T> SingleCacheField<T> saveSingleCacheField(Class<T> fieldWhereClass, Field field, Method getter, Method setter) {
        if (field.getName().split("\\.").length > 1) {
            throw new RuntimeException("字段[" + field + "]并非单层字段");
        }
        //先查看是否有这个字段
        SingleCacheField<T> singleCacheField = (SingleCacheField<T>) getCacheField(fieldWhereClass, field.getName());
        if (singleCacheField != null) {
            //如果已经有这个缓存了，查看getter和setter是否存在
            //准备好Cache，有可能会进行更新操作
            SingleCacheField<T> cache;
            Method cGetter = singleCacheField.getGetter();
            Method cSetter = singleCacheField.getSetter();
            //判断是否更新
            boolean isUpdate = false;

            //判断getter
            if (cGetter == null && getter != null) {
                //getter为空，且传入的getter不为空
                isUpdate = true;
                cGetter = getter;
            }

            //判断setter
            if (cSetter == null && setter != null) {
                //setter为空，且传入的setter不为空
                //变更状态，设置getter
                isUpdate = true;
                cSetter = setter;
            }

            //判断是否更新
            if (isUpdate) {
                cache = new SingleCacheField<T>(fieldWhereClass, field, cGetter, cSetter);
                updateSingleCacheField(cache);
                return cache;
            }
            //如果不需要更新，直接返回获取值
            return singleCacheField;

        } else {
            //不存在，直接返回
            SingleCacheField<T> newSingleCacheField = new SingleCacheField<>(fieldWhereClass, field, getter, setter);
            updateSingleCacheField(newSingleCacheField);
            return newSingleCacheField;
        }
    }

    /**
     * 向缓存中增加一个字段记录并返回这个新的缓存字段
     * getter、setter均为null
     *
     * @param fieldWhereClass
     * @param field
     * @param <T>
     * @return
     */
    private static <T> SingleCacheField<T> saveSingleCacheField(Class<T> fieldWhereClass, Field field) {
        return saveSingleCacheField(fieldWhereClass, field, null, null);
    }

    /**
     * 向缓存中增加一个字段记录
     * 不可使用多层级字段获取
     *
     * @param fieldWhereClass
     * @param fieldName
     * @param getter
     * @param setter
     */
    private static <T> SingleCacheField<T> saveSingleCacheField(Class<T> fieldWhereClass, String fieldName, Method getter, Method setter) {
        return saveSingleCacheField(fieldWhereClass, getField(fieldWhereClass, fieldName), getter, setter);
    }

    /**
     * 向缓存中增加一个字段记录
     * 不可使用多层级字段获取
     *
     * @param fieldWhereClass
     * @param fieldName
     * @param <T>
     * @return
     */
    private static <T> SingleCacheField<T> saveSingleCacheField(Class<T> fieldWhereClass, String fieldName) {
        return saveSingleCacheField(fieldWhereClass, getField(fieldWhereClass, fieldName));
    }


    /**
     * 储存一个getter方法
     *
     * @param fieldWhereClass
     * @param fieldName
     * @param getter
     * @param <T>
     */
    private static <T> SingleCacheField<T> saveSingleCacheFieldGetter(Class<T> fieldWhereClass, String fieldName, Method getter) {
        return saveSingleCacheField(fieldWhereClass, fieldName, getter, null);
    }

    /**
     * 储存一个getter方法
     *
     * @param fieldWhereClass
     * @param field
     * @param getter
     * @param <T>
     */
    private static <T> SingleCacheField<T> saveSingleCacheFieldGetter(Class<T> fieldWhereClass, Field field, Method getter) {
        return saveSingleCacheField(fieldWhereClass, field, getter, null);
    }

    /**
     * 储存一个setter方法
     *
     * @param fieldWhereClass
     * @param fieldName
     * @param setter
     * @param <T>
     */
    private static <T> SingleCacheField<T> saveSingleCacheFieldSetter(Class<T> fieldWhereClass, String fieldName, Method setter) {
        return saveSingleCacheField(fieldWhereClass, fieldName, null, setter);
    }

    /**
     * 储存一个setter方法
     *
     * @param fieldWhereClass
     * @param field
     * @param setter
     * @param <T>
     */
    private static <T> SingleCacheField<T> saveSingleCacheFieldSetter(Class<T> fieldWhereClass, Field field, Method setter) {
        return saveSingleCacheField(fieldWhereClass, field, null, setter);
    }

    /**
     * 更新一个或新增加一个字段缓存
     *
     * @param newSingleCacheField
     */
    private static void updateSingleCacheField(SingleCacheField newSingleCacheField) {
        //字段所在类的Class对象
        Class fieldWhereClassIn = newSingleCacheField.getFieldWhereClassIn();
        //从缓存中获取整个Map集合
        HashMap<String, SingleCacheField> cacheFields = SINGLE_FIELD_CACHE_MAP.get(fieldWhereClassIn);
        if (cacheFields == null) {
            //如果没有此类的相关记录
            //获取这个类的字段总数量
            int length = fieldWhereClassIn.getDeclaredFields().length;
            HashMap<String, SingleCacheField> hashMap = new HashMap<>(length);
            //保存这个增加字段并添加至缓存
            hashMap.put(newSingleCacheField.getFieldName(), newSingleCacheField);
            SINGLE_FIELD_CACHE_MAP.put(fieldWhereClassIn, hashMap);
        } else {
            //有此类相关记录，保存或覆盖此字段信息
            cacheFields.put(newSingleCacheField.getFieldName(), newSingleCacheField);
        }
    }


    /**
     * 获取缓存中的字段的getter方法
     *
     * @param fieldWhereClass
     * @param fieldName
     * @return
     */
    private static Method getCacheFieldGetter(Class<?> fieldWhereClass, String fieldName) {
        //如果缓存中存在此字段，返回getter，否则返回null
        return Optional.ofNullable(getCacheField(fieldWhereClass, fieldName)).map(CacheField::getGetter).orElse(null);
    }

    /**
     * 获取缓存中字段的setter方法
     *
     * @param fieldWhereClass
     * @param fieldName
     * @return
     */
    private static Method getCacheFieldSetter(Class<?> fieldWhereClass, String fieldName) {
        //如果缓存中存在此字段，返回getter，否则返回null
        return Optional.ofNullable(getCacheField(fieldWhereClass, fieldName)).map(CacheField::getSetter).orElse(null);
    }

    /* ———————————————————————————————————————— 单缓存字段内部类 ———————————————————————————————————— */

    /**
     * 单层字段缓存对象
     * 内部类，实现字段缓存，优化此工具类的效率
     * 字段的缓存，其中储存所在类的Clss对象、字段名称、字段对象、字段类型、getter、setter
     * *虽然为公共权限，但仅仅为了使其内部类可被外部访问而设*
     */
    private static class SingleCacheField<T> implements CacheField<T> {
        // 储存所在类的Class对象、字段名称、字段对象、字段类型、getter、setter

        /**
         * 字段所在Class，不可变更
         */
        private final Class<T> FIELD_WHERE_CLASS;

        /**
         * 字段名称
         */
        private final String FIELD_NAME;

        /**
         * 字段对象
         */
        private final Field FIELD;

        /**
         * 字段类型
         */
        private final Class<?> FIELD_TYPE;

        /**
         * getter方法
         */
        private final Method GETTER;

        /**
         * setter方法
         */
        private final Method SETTER;

        /* —————— 各种api —————— */

        /**
         * 通过实例对象获取字段值,返回一个封装类
         * success为是否成功的执行了
         * invoke代表执行的值
         * 如果执行失败，则invoke的值必然为null
         *
         * @param obj
         * @return
         */
        private InvokeResult objectGetter(Object obj) {
            if (GETTER != null) {
                try {
                    //执行getter方法
                    Object invoke = GETTER.invoke(obj);
                    return InvokeResult.success(invoke);
                } catch (Exception e) {
                    return InvokeResult.fail();
                }
            } else {
                return InvokeResult.fail();
            }
        }

        /**
         * 通过一个实例对象设置字段值，返回一个封装类
         * setter没有返回值，则invoke必然为null
         * 如果success为false，则说明方法执行出现错误或setter不存在
         *
         * @param obj
         * @param value
         * @return
         */
        private InvokeResult objectSetter(Object obj, Object value) {
            if (SETTER != null) {
                try {
                    MethodUtil.invoke(obj, new Object[]{value}, SETTER);
                    return InvokeResult.emptySuccess();
                } catch (InvocationTargetException | IllegalAccessException e) {
                    return InvokeResult.fail();
                }
            } else {
                return InvokeResult.fail();
            }
        }

        /**
         * 获取字段所在类的Class对象
         *
         * @return
         */
        private Class<T> getFieldWhereClassIn() {
            return this.FIELD_WHERE_CLASS;
        }

        /**
         * 获取字段名
         *
         * @return
         */
        private String getFieldName() {
            return FIELD_NAME;
        }

        /**
         * 获取字段
         *
         * @return
         */
        @Override
        public Field getField() {
            return FIELD;
        }

        /**
         * 获取字段类型
         *
         * @return
         */
        private Class<?> getFieldType() {
            return FIELD_TYPE;
        }

        /**
         * 获取getter方法
         *
         * @return
         */
        @Override
        public Method getGetter() {
            return GETTER;
        }

        /**
         * 获取setter方法
         *
         * @return
         */
        @Override
        public Method getSetter() {
            return SETTER;
        }


        /*
            重写equals方法和hashCode方法，使得字段名和所在Class类为区别本类的字段
         */

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SingleCacheField<?> that = (SingleCacheField<?>) o;
            return FIELD_WHERE_CLASS.equals(that.FIELD_WHERE_CLASS) &&
                    FIELD_NAME.equals(that.FIELD_NAME);
        }

        @Override
        public int hashCode() {
            return Objects.hash(FIELD_WHERE_CLASS, FIELD_NAME);
        }

        /**
         * 构造-通过字段为字段名赋值
         */
        private SingleCacheField(Class<T> fieldWhereClass, Field field, Method getter, Method setter) {
            //设置所属类
            this.FIELD_WHERE_CLASS = fieldWhereClass;
            //设置字段相关
            this.FIELD = field;
            this.FIELD_NAME = field.getName();
            this.FIELD_TYPE = field.getType();
            //获取此字段的getter方法
            this.GETTER = getter;
            this.SETTER = setter;
        }

        /**
         * 获取字段的实例值
         *
         * @param object
         * @return
         */
        @Override
        public InvokeResult fieldValue(Object object) {
            return objectGetter(object);
        }

        /**
         * 为字段赋值
         *
         * @param object
         * @param param
         * @return
         */
        @Override
        public InvokeResult fieldValueSet(T object, Object param) {
            //赋值
            try {
                MethodUtil.invoke(object, new Object[]{param}, SETTER);
                //如果没有出现异常，则说明方法执行成功，返回成功信息
                return InvokeResult.emptySuccess();
            } catch (InvocationTargetException | IllegalAccessException e) {
                //如果出现异常，则说明方法执行错误，返回错误信息
                return InvokeResult.fail();
            }
        }

    }




    /* —————————————————————————————————————— 多层缓存对象相关方法 —————————————————————————————————————————— */

    /**
     * 尝试查询上层层级字段
     *
     * @param thisLevelCacheField
     * @param <R>
     * @param <T>
     * @return
     */
    private static <R, T> LevelCacheField<R, T> findUpperLevelCacheField(LevelCacheField thisLevelCacheField) {
        HashMap<String, LevelCacheField> levelCacheFieldHashMap = LEVEL_FIELD_CACHE_MAP.get(thisLevelCacheField.getRootClass());
        try {
            LevelCacheField tryFoundUpper = levelCacheFieldHashMap.entrySet().parallelStream().filter(e -> (e.getValue().getLevel() + 1) == thisLevelCacheField.getLevel()).findFirst().map(Map.Entry::getValue).orElse(null);
            return Optional.ofNullable(tryFoundUpper).orElse(null);
        } catch (Exception ignore) {
            //如果出现异常则说明没有查询到，不做处理,直接返回null
            return null;
        }
    }

    /**
     * 储存或覆盖一个多层级字段对象
     * 可选：上层字段、下层字段
     * 必要：根类、字段名、本类字段
     *
     * @param rootClass      必要 根类
     * @param fieldName      必要 字段名
     * @param upper          可选 上层
     * @param lower          可选 下层
     * @param thisCacheField 必要 本类字段
     * @param <R>            根类类型
     * @param <T>            本类类型
     * @return 保存或更新的多层级对象
     */
    private static <R, T> LevelCacheField<R, T> saveLevelCacheField(Class<R> rootClass,
                                                                    String fieldName,
                                                                    LevelCacheField<R, ?> upper,
                                                                    LevelCacheField<R, ?> lower,
                                                                    SingleCacheField<T> thisCacheField) {


        //通过根类获取
        HashMap<String, LevelCacheField> levelCacheFieldHashMap = LEVEL_FIELD_CACHE_MAP.get(rootClass);
        //判断有没有此根类的多级字段集
        if (levelCacheFieldHashMap != null) {
            //有多层级字段集，根据字段名获取对象
            LevelCacheField<R, T> levelCacheField = levelCacheFieldHashMap.get(fieldName);

            if (levelCacheField == null) {
                //没有此多层级对象，创建并添加
                levelCacheField = new LevelCacheField<>(rootClass, fieldName, upper, lower, thisCacheField);
                levelCacheFieldHashMap.put(fieldName, levelCacheField);
            }

            //判断upper
            if (levelCacheField.getUpperLevelField() == null) {
                //如果原本没有上层
                //如果upper不为null，赋值
                if (upper != null) {
                    levelCacheField.setUpperLevelField(upper);
                } else {
                    //如果为null，则尝试从内存中寻找上层字段 - 根据level查找即可
                    //如果有上层字段，则肯定同属一个根类，且上层level+1为此层level，此层level使用字段名切割判断
                    LevelCacheField<R, ?> upperLevelCacheField = findUpperLevelCacheField(levelCacheField);
                    LevelCacheField tryFoundUpper = levelCacheFieldHashMap.entrySet().parallelStream().filter(e -> (e.getValue().getLevel() + 1) == fieldName.split("\\.").length).findFirst().map(Map.Entry::getValue).orElse(null);
                    //如果查询到了则赋值
                    if (tryFoundUpper != null) {
                        levelCacheField.setUpperLevelField(tryFoundUpper);
                    }
                }
            }

            //判断lower
            if (levelCacheField.getLowerLevelField() == null) {

                //如果lower不为null，赋值
                if (lower != null) {
                    levelCacheField.setLowerLevelField(lower);
                } else {
                    //如果lower为null，尝试从缓存中查找下层字段 - 根据level查找
                    //如果有下层字段，则肯定同属一个根类，且下层level-1为此层level，此层level使用字段名切割判断
                    try {
                        LevelCacheField tryFoundLower = levelCacheFieldHashMap.entrySet().parallelStream().filter(e -> (e.getValue().getLevel() - 1) == fieldName.split("\\.").length).findFirst().map(Map.Entry::getValue).orElse(null);
                        levelCacheField.setLowerLevelField(tryFoundLower);
                    } catch (Exception ignore) {
                        //如果出现异常则说明没有查询到，不做处理
                    }
                }
            }


            //返回
            return levelCacheField;

        } else {
            //没有字段集，创建一个新的并保存
            HashMap<String, LevelCacheField> newLevelCacheFieldHashMap = new HashMap<>(5);
            //新的多层级缓存字段
            LevelCacheField<R, T> newLevelCacheField = new LevelCacheField<>(rootClass, fieldName, null, null, thisCacheField);
            //保存
            newLevelCacheFieldHashMap.put(fieldName, newLevelCacheField);
            //根据根类记入缓存
            LEVEL_FIELD_CACHE_MAP.put(rootClass, newLevelCacheFieldHashMap);

            //返回
            return newLevelCacheField;
        }
    }

    /**
     * 储存或覆盖一个多层级字段对象
     * 可选的上层字段、下层字段为null
     * 必要：根类、字段名、本类字段
     *
     * @param rootClass
     * @param fieldName
     * @param thisCacheField
     * @param <R>
     * @param <T>
     * @return
     */
    private static <R, T> LevelCacheField<R, T> saveLevelCacheField(Class<R> rootClass,
                                                                    String fieldName,
                                                                    SingleCacheField<T> thisCacheField) {
        return saveLevelCacheField(rootClass, fieldName, null, null, thisCacheField);
    }

    /**
     * 储存或覆盖一个多层级字段对象的upper
     *
     * @param rootClass
     * @param fieldName
     * @param upper
     * @param thisCacheField
     * @param <R>
     * @param <T>
     * @return
     */
    private static <R, T> LevelCacheField<R, T> saveLevelCacheFieldUpper(Class<R> rootClass,
                                                                         String fieldName,
                                                                         LevelCacheField<R, ?> upper,
                                                                         SingleCacheField<T> thisCacheField) {
        return saveLevelCacheField(rootClass, fieldName, upper, null, thisCacheField);
    }

    /**
     * 储存或覆盖一个多层级字段对象的lower
     *
     * @param rootClass
     * @param fieldName
     * @param lower
     * @param thisCacheField
     * @param <R>
     * @param <T>
     * @return
     */
    private static <R, T> LevelCacheField<R, T> saveLevelCacheFieldLower(Class<R> rootClass,
                                                                         String fieldName,
                                                                         LevelCacheField<R, ?> lower,
                                                                         SingleCacheField<T> thisCacheField) {
        return saveLevelCacheField(rootClass, fieldName, null, lower, thisCacheField);
    }


    /* —————————————————————————————————————— 多层缓存对象内部类 —————————————————————————————————————————— */

    /**
     * 多层级字段缓存对象
     * 内部类，实现字段缓存，优化此工具类的效率
     * **由于多层级字段的缓存与获取很容易发生错误和异常，所以尽可能的将异常处理，提高容错性
     *
     * @param <R> 多层级缓存字段的根类类型
     * @param <T> 多层级缓存字段的当前类类型
     */
    private static class LevelCacheField<R extends Object, T> implements CacheField<R> {
        /*
            多层级，就要一环套一环
            每层概念上都会有：上层、下层、当前层
            当前层即为一个SingleCacheField对象

         */

        /**
         * 多层级的根层所在类
         */
        private final Class<R> ROOT_CLASS;

        /**
         * 字段名，唯一且不可变
         */
        private final String FIELD_NAME;

        /**
         * 上层对象
         */
        private LevelCacheField upperLevelField;

        /**
         * 下层
         */
        private LevelCacheField lowerLevelField;

        /**
         * 当前层
         */
        private SingleCacheField<T> thisLevelField;

        private final int LEVEL;

        /* —————————— 相关api —————————— */

        /**
         * 当前层所在类
         *
         * @return
         */
        public Class<T> thisLevelClass() {
            return thisLevelField.getFieldWhereClassIn();
        }

        @Override
        public Method getGetter() {
            return thisLevelField.getGetter();
        }

        @Override
        public Method getSetter() {
            return thisLevelField.getSetter();
        }

        @Override
        public Field getField() {
            return thisLevelField.getField();
        }


        /* —————————— getter setter —————————— */

        /**
         * 当前字段在根类中的层级数，1级为根字段，根据字段名的切割计算
         *
         * @return
         */
        public int getLevel() {
            return this.LEVEL;
        }

        public Class getRootClass() {
            return ROOT_CLASS;
        }

        public String getFieldName() {
            return FIELD_NAME;
        }

        public LevelCacheField getUpperLevelField() {
            return upperLevelField;
        }

        /**
         * 设置upper的同时设置对方的lower
         *
         * @param upperLevelField
         */
        public void setUpperLevelField(LevelCacheField upperLevelField) {
            if (this.upperLevelField != null) {
                this.upperLevelField = upperLevelField;
                //直接赋值，防止无限循环
                upperLevelField.lowerLevelField = this;
            }
        }

        public LevelCacheField getLowerLevelField() {
            return lowerLevelField;
        }

        /**
         * 设置lower的同时设置对方的upper
         *
         * @param lowerLevelField
         */
        public void setLowerLevelField(LevelCacheField lowerLevelField) {
            if (lowerLevelField != null) {
                this.lowerLevelField = lowerLevelField;
                //直接赋值，防止无限循环
                lowerLevelField.upperLevelField = this;
            }
        }

        public SingleCacheField<T> getThisLevelField() {
            return thisLevelField;
        }

        public void setThisLevelField(SingleCacheField<T> thisLevelField) {
            this.thisLevelField = thisLevelField;
        }

        /* —————— toString、equals、hashcode —————— */

        @Override
        public String toString() {
            return "LevelCacheField{" +
                    "rootClass=" + ROOT_CLASS +
                    ", fieldName='" + FIELD_NAME + '\'' +
                    ", thisLevelFieldClass=" + thisLevelField.getFieldWhereClassIn() +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            LevelCacheField<?, ?> that = (LevelCacheField<?, ?>) o;
            return ROOT_CLASS.equals(that.ROOT_CLASS) &&
                    FIELD_NAME.equals(that.FIELD_NAME);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ROOT_CLASS, FIELD_NAME);
        }

        /* —————— 根类匹配方法 —————— */

        /**
         * 如果根类不匹配则抛出异常
         *
         * @param myRoot
         * @param inRoot
         */
        private static void isSameRoot(Class myRoot, Class inRoot) {
            if (!myRoot.equals(inRoot)) {
                throw new NotSameRootException(myRoot, inRoot);
            }

        }

        /**
         * 构造
         */
        public LevelCacheField(Class rootClass, String fieldName, LevelCacheField upperLevelField, LevelCacheField lowerLevelField, SingleCacheField<T> thisLevelField) {
            //验证必要参数
            allNonNull(rootClass, fieldName, thisLevelField);

            //根层Class
            this.ROOT_CLASS = rootClass;

            //为字段名赋值，不可为null
            this.FIELD_NAME = fieldName;

            //为层级赋值 - 根据字段名切割‘.’计算层级数
            this.LEVEL = fieldName.split("\\.").length;

            //上层对象，可为null
            //如果不为null，则根层类必须相同
            if (upperLevelField != null) {
                isSameRoot(rootClass, upperLevelField.getRootClass());
                this.upperLevelField = upperLevelField;
            } else {
                this.upperLevelField = null;
            }

            //下层对象，可为null
            //如果不为null，则根层类必须相同
            if (lowerLevelField != null) {
                isSameRoot(rootClass, lowerLevelField.getRootClass());
                this.lowerLevelField = lowerLevelField;
            } else {
                this.lowerLevelField = null;
            }

            //当前层字段对象，不可为null
            this.thisLevelField = thisLevelField;

        }

        /**
         * 使用根类具体对象返回当前层级的当前字段的值
         * 从上层对象遍历至此，由远到近
         *
         * @return
         */
        private Object thisLevelFieldGetter(R r) {
            //如果有上层对象
            if (upperLevelField != null) {
                //如果有上层对象，则先获取上层对象的字段值，再根据上层对象的字段值返回对象获取当前字段值
                //上层字段值的获取对象的类型为根类类型
                //得到上层对象的值，使用上层对象获取本类对象
                Object upperInvoke = upperLevelField.thisLevelFieldGetter(r);
                if (upperInvoke != null) {
                    try {
                        //使用上层字段的值执行getter
                        return getThisLevelField().getGetter().invoke(upperInvoke);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        // 出现异常，展示异常并返回一个null
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    //如果上层返回值为null，则直接返回null
                    return null;
                }
            } else {
                //如果没有上层对象
                //如果level不为1，则尝试查询上层，如果查询不到则返回null
                if (LEVEL != 1) {
                    // 查询上层字段
                    LevelCacheField<R, ?> upperLevelCacheField = findUpperLevelCacheField(this);
                    if (upperLevelCacheField != null) {
                        //如果查询到了，赋值并重新获取
                        this.upperLevelField = upperLevelCacheField;
                        return thisLevelFieldGetter(r);
                    } else {
                        //如果没有找到上层对象，返回null
                        return null;
                    }
                } else {
                    //如果level为1没有上层对象，认为当前即为根类，获取当前层字段值
                    InvokeResult invokeResult = thisLevelField.objectGetter(r);
                    //抛出异常并不合适，这里选择不做处理，直接返回null
                    return invokeResult.getInvoke();
                }
            }
        }


        /**
         * 为字段赋值
         *
         * @param object
         * @param param
         * @return
         */
        private Boolean thisLevelFieldSetter(R object, Object param) {
            //如果有上层字段，
            if (upperLevelField != null) {
                //如果有上层字段值，则先获取上层字段的字段值，再根据上层字段的值为当前字段赋值
                Object upperInvoke = upperLevelField.thisLevelFieldGetter(object);
                //如果上层对象的获取值为null，尝试为上层字段赋一个新的实例对象
                if (upperInvoke == null) {
                    try {
                        //获取上层对象的SETTER方法和字段类型
                        Method upperSetter = upperLevelField.getSetter();
                        Object upperInstance = upperLevelField.getThisLevelField().getFieldType().newInstance();
                        //为上层字段的上层字段赋值
                        //获取上上层对象的实例
                        InvokeResult upperUpperInvokeResult = upperLevelField.upperLevelField.fieldValue(object);
                        //如果获取失败或没有值，直接返回null
                        if (upperUpperInvokeResult.isSuccess() && upperUpperInvokeResult.getInvoke() != null) {
                            MethodUtil.invoke(upperUpperInvokeResult, new Object[]{upperInstance}, upperSetter);
                            //赋值之后重新获取
                            return thisLevelFieldSetter(object, param);
                        } else {
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //若是出现异常，则说明上层对象无法赋值
                        //没有上层对象则无法为本层对象赋值，直接返回false
                        return false;
                    }
                } else {
                    try {
                        //上层对象的获取值不为null，使用上层对象的返回值赋值
                        Method setter = thisLevelField.getSetter();
                        MethodUtil.invoke(object, new Object[]{param}, setter);
                        return true;
                    } catch (Exception e) {
                        //如果出现异常，则说明赋值出现错误，返回false
                        e.printStackTrace();
                        return false;
                    }
                }
            } else {
                //如果没有上层对象，判断层级
                if (LEVEL != 1) {
                    //如果层级不为1，则尝试查询上层，如果查询不到则返回null
                    LevelCacheField<R, ?> upperLevelCacheField = findUpperLevelCacheField(this);
                    if (upperLevelCacheField != null) {
                        //如果查询到了，赋值并重新查询
                        this.upperLevelField = upperLevelCacheField;
                        return thisLevelFieldSetter(object, param);
                    } else {
                        //没有查询到，直接返回false
                        return false;
                    }
                } else {
                    //层级为1，则本类即为根类，直接赋值并返回赋值结果
                    InvokeResult invokeResult = thisLevelField.objectSetter(object, param);
                    return invokeResult.isSuccess();
                }
            }
        }


        /**
         * 获取字段的实例
         *
         * @param object
         * @return
         */
        @Override
        public InvokeResult fieldValue(R object) {
            Object o = thisLevelFieldGetter(object);
            if (o != null) {
                return InvokeResult.success(o);
            } else {
                return InvokeResult.fail();
            }
        }

        /**
         * 为字段赋值并封装结果
         *
         * @param r
         * @param param
         * @return
         */
        @Override
        public InvokeResult fieldValueSet(R r, Object param) {
            Boolean isSuccess = thisLevelFieldSetter(r, param);
            if (isSuccess) {
                return InvokeResult.emptySuccess();
            } else {
                return InvokeResult.fail();
            }
        }

    }





    /* ———————————————————————————————————— 缓存获取 封装类 —————————————————————————————————— */

    /**
     * 内部类
     * 方法执行的返回值封装类,此类除了重写方法以外的唯一公共接口
     */
    public static class InvokeResult {
        private final boolean success;
        private final Object invoke;

        private InvokeResult(boolean success, Object invoke) {
            this.success = success;
            this.invoke = invoke;
        }

        /* ---- factory ----*/

        static InvokeResult emptySuccess() {
            return new InvokeResult(true, null);
        }

        static InvokeResult success(Object invoke) {
            return new InvokeResult(true, invoke);
        }

        static InvokeResult fail() {
            return new InvokeResult(false, null);
        }

        /* ---- getter ---- */

        public boolean isSuccess() {
            return success;
        }

        public Object getInvoke() {
            return invoke;
        }
    }

    /* ———————————————————————————————————— 缓存获取 方法 —————————————————————————————————— */

    /**
     * 从缓存中获取一个缓存字段
     *
     * @param fieldWhereClass
     * @return
     */
    private static <T> CacheField<T> getCacheField(Class<T> fieldWhereClass, String fieldName) {
        //判断这是单层字段还是多层字段
        String[] split = fieldName.split("\\.");
        if (split.length == 1) {
            //是单层的
            HashMap<String, SingleCacheField> singleCacheFieldHashMap = SINGLE_FIELD_CACHE_MAP.get(fieldWhereClass);
            //如果有此字段的信息，返回获取值，如果没有直接返回null
            SingleCacheField singleCacheField = Optional.ofNullable(singleCacheFieldHashMap).map(m -> m.get(fieldName)).orElse(null);
            return singleCacheField;
        } else {
            //如果长度不为1，则认为是多层对象
            HashMap<String, LevelCacheField> levelCacheFieldHashMap = LEVEL_FIELD_CACHE_MAP.get(fieldWhereClass);
            //如果有此字段的信息，返回获取值，如果没有直接返回null
            LevelCacheField levelCacheField = Optional.ofNullable(levelCacheFieldHashMap).map(m -> m.get(fieldName)).orElse(null);
            return levelCacheField;
        }
    }



    /* ———————————————————————————————————— 缓存异常 ———————————————————————————————————— */

    /**
     * 多层级对象根类不匹配异常
     */
    private static class NotSameRootException extends RuntimeException {
        NotSameRootException(Class myRoot, Class inRoot) {
            super("多层级对象的根类不同！当前根类：[" + myRoot + "] 无法设置根类为[" + inRoot + "]的多层级对象！");
        }
    }

    /**
     * 多层级字段上层无返回值异常
     */
    private static class UpperLevelNoResultException extends RuntimeException {
        UpperLevelNoResultException(LevelCacheField upper) {
            super("上层对象没有返回值：" + upper);
        }
    }


    /**
     * 构造私有化
     */
    private FieldUtils() {
    }

}
