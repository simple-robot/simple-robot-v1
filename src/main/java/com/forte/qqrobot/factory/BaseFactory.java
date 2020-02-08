package com.forte.qqrobot.factory;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.exception.EnumFactoryException;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;
import com.forte.qqrobot.exception.RobotDevException;
import com.forte.qqrobot.utils.EnumValues;
import com.forte.utils.reflect.EnumUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

/**
 *
 * 创建枚举实例的基础类
 * 所有的实现类均应当作为单例使用，此类实现类没有多例的必要性。<br>
 * 实现类需要自行实现对外窗口。<br>
 *
 * 一般对外窗口叫做register，第一个参数为枚举名称，其余参数为构造所需参数。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseFactory<E extends Enum<E>> {

    /*
     * E代表枚举类型
     */
    protected BaseFactory(){
    }

    /**
     * 获取枚举类型
     */
    protected abstract Class<E> enumType();

    /**
     * 返回此枚举需要的构造参数列表
     */
    protected abstract Class<?>[] constructorTypes();

    /**
     * 接受一个数值参数，返回一个枚举数组。
     * <br>
     * 主要用于在获取values的时候将结果转化为数组用。
     * <br>
     * 可以考虑不再强制需要实现，不过还是手动实现的效率高一点。我猜的。
     */
    protected abstract IntFunction<E[]> toArrayFunction();

    /**
     * 创建一个新的枚举对象。<br>
     * 请注意不要出现冲突的名称
     * @param name              枚举名称
     * @param params            参数列表
     * @return 新建的枚举实例对象
     */
    protected E registerEnum(String name, Object... params) throws EnumInstantiationRequireException, EnumInstantiationException {
        // 判断是否可以新增
        Class<E> eType = enumType();
        try {
            throwOrPass(name, params);
        }catch (Throwable e){
            // 如果有异常，使用此异常统一抛出
            throw new EnumInstantiationRequireException(eType, e);
        }

        // 实例化
        try {
            return EnumUtils.newEnum(eType, name, constructorTypes(), params);
        }catch (Throwable e){
            // 实例化过程如果出现异常，统一使用此异常抛出
            throw new EnumInstantiationException(eType, e);
        }
    }

    /**
     * 获取全部values值
     * @return  MsgGetTypes的全部value值。包括额外添加的
     */
    public E[] values(){
        return EnumValues.values(enumType(), toArrayFunction());
    }

    /**
     * 根据名称获取枚举实例。包括额外添加的。
     * @param name  枚举名称
     * @return      枚举实例
     */
    public E valueOf(String name){
        return E.valueOf(enumType(), name);
    }

    /**
     * 根据名称列表转化为数组类型
     * @param names 名称列表
     * @return  数据类型的枚举
     */
    public E[] valueByArray(String... names){
        if(names == null || names.length == 0){
            return toArrayFunction().apply(0);
        }else{
            return Arrays.stream(names).map(e -> E.valueOf(enumType(), e)).toArray(toArrayFunction());
        }
    }

    /**
     * 判断规则是否匹配
     * @param name      枚举名称
     * @param params    参数列表
     */
    protected void throwOrPass(String name, Object[] params) throws Exception {
        // not null
        Objects.requireNonNull(name);
        Objects.requireNonNull(params);

        // 1.判断name是否已经存在
        try{
            MsgGetTypes.valueOf(name);
            throw new RobotDevException(1, "can not create new "+ enumType() +" for '"+ name +"': has already existed.");
        }catch (IllegalArgumentException ignore){
            // 如果出现这个异常，一般来讲代表不存在这个值，则说明不存在此值，可以添加。
        }

        // 判断完成后使用实现类型判断
        requireCanUse(name, params);

    }

    /**
     * 字工厂自主实现的参数权限判断。提供新枚举实例的枚举名称与参数列表。
     * @param name          名称
     * @param params        参数列表
     */
    protected abstract void requireCanUse(String name, Object[] params) throws Exception;





}
