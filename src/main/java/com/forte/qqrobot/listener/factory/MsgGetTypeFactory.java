package com.forte.qqrobot.listener.factory;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.exception.RobotDevException;

import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * 针对于 {@link com.forte.qqrobot.beans.messages.types.MsgGetTypes} 的工厂，用于创建并注册额外的监听类型
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MsgGetTypeFactory extends BaseFactory<MsgGetTypes> {

    /** 构造所需要的参数类型列表 */
    private static final Class<?>[] CONSTRUCTOR_TYPES = { Class.class };

    /** {@link MsgGetTypes} 的class对象 */
    private static final Class<MsgGetTypes> ENUM_TYPE = MsgGetTypes.class;

    /** 数组构建器 */
    private static final IntFunction<MsgGetTypes[]> TO_ARRAY_FUNCTION = MsgGetTypes[]::new;

    /** 单例，唯一实例 */
    private static final MsgGetTypeFactory FACTORY = new MsgGetTypeFactory();

    /**
     * 一个禁止被使用的构造
     */
    private MsgGetTypeFactory(){
        if(FACTORY != null)
            throw new RuntimeException("no,nonononono,You don't need more examples");
    }

    /**
     * 获取一个实例
     */
    public static MsgGetTypeFactory getInstance() {
        return FACTORY;
    }

    @Override
    protected Class<MsgGetTypes> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<MsgGetTypes[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }

    /**
     * 参数权限判断
     * @param name          名称
     * @param params        参数列表
     */
    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数只可能存在一个，即class<? extends MsgGet>
        Class<? extends MsgGet> listenType = (Class<? extends MsgGet>) params[0];

        // 2.类型不可以是MsgGet本身。
        if(listenType.equals(MsgGet.class)){
            throw new RobotDevException("监听类型不可以是MsgGet本身。");
        }

        // 3.类型也没有冲突
        boolean listenTypeExist = Arrays.stream(values()).filter(t -> t.getBeanClass() != null).anyMatch(t -> t.getBeanClass().equals(listenType));
        if(listenTypeExist){
            throw new RobotDevException("已经存在对类型 "+ listenType +" 进行监听的MsgGetType类型值。");
        }

    }


    /**
     * 获取一个MsgGetType的新枚举
     */
    public MsgGetTypes register(String name, Class<? extends MsgGet> listenType) throws NoSuchMethodException, IllegalAccessException {
        // 执行父类中的注册方法
        return super.registerEnum(name, listenType);
    }

    /**
     * 获取一个MsgGetType的新枚举 - 静态窗口
     */
    public static MsgGetTypes registerType(String name, Class<? extends MsgGet> listenType) throws NoSuchMethodException, IllegalAccessException {
        return getInstance().register(name, listenType);
    }


}
