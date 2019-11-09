package com.forte.qqrobot.anno.factory;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;
import com.forte.qqrobot.exception.RobotDevException;
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
     *
     */
    protected abstract IntFunction<E[]> toArrayFunction();

    /**
     * 创建一个新的MsgGetType枚举对象。<br>
     * 请注意不要出现冲突的名称
     * @param name              枚举名称
     * @param params            参数列表
     * @return
     */
    protected E registerEnum(String name, Object... params) throws NoSuchMethodException, IllegalAccessException {
        // 判断是否可以新增
        try {
            throwOrPass(name, params);
        }catch (Exception e){
            // 如果有异常，使用此异常统一抛出
            throw new EnumInstantiationRequireException(enumType(), e);
        }

        // 实例化
        try {
            return EnumUtils.newEnum(enumType(), name, constructorTypes(), params);
        }catch (Exception e){
            // 实例化过程如果出现异常，统一使用此异常抛出
            throw new EnumInstantiationException(enumType(), e);
        }
    }

    /**
     * 获取全部values值
     * @return  MsgGetTypes的全部value值。包括额外添加的
     */
    public E[] values(){
        return EnumUtils.values(enumType(), toArrayFunction());
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
    protected void throwOrPass(String name, Object[] params){
        // not null
        Objects.requireNonNull(name);
        Objects.requireNonNull(params);

        // 1.判断name是否已经存在
        try{
            MsgGetTypes.valueOf(name);
            throw new RobotDevException("can not create new "+ enumType() +" for '"+ name +"': has already existed.");
        }catch (IllegalArgumentException ignore){
            // 如果出现这个异常，一般来讲代表不存在这个值，则说明不存在此值，可以添加。
        }

        // 判断完成后使用实现类型判断
        requireCanUse(name, params);

    }

    /**
     * 根据名称与监听类型来判断是否存在重复类型。
     * 1.名称不可重复<br>
     * 2.类型不能是MsgGet本身。
     * 3.理论上也不能出现相同的监听类型
     * @param name          名称
     * @param params        参数列表
     */
    protected abstract void requireCanUse(String name, Object[] params);





}
