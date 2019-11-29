package com.forte.qqrobot.factory;

import com.forte.qqrobot.beans.types.CQCodeTypes;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.utils.ObjectsPlus;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.IntFunction;

/**
 *
 * 为{@link CQCodeTypes} 创建新实例的工厂。
 * 此工厂相对于其他工厂较为特殊，需要保证参数验证通过之后才可以进行创建。
 * 且考虑到多线程的问题，注册窗口将会标记线程同步。
 * 一些通用的注释我也不写了...懒得写了
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CQCodeTypeFactory extends BaseFactory<CQCodeTypes> {

    private static final Class<CQCodeTypes> ENUM_TYPE = CQCodeTypes.class;
    // 构造参数列表
    // String function, String[] keys, String[] ignoreAbleKeys , String[] valuesRegex , int sort
    private static final Class<?>[] CONSTRUCTOR_TYPES = {String.class, String[].class, String[].class, String[].class, int.class};
    private static final IntFunction<CQCodeTypes[]> TO_ARRAY_FUNCTION = CQCodeTypes[]::new;
    private static final CQCodeTypeFactory SINGLE = new CQCodeTypeFactory();
    private CQCodeTypeFactory(){}
    public static CQCodeTypeFactory getInstance() {
        return SINGLE;
    }

    @Override
    protected Class<CQCodeTypes> enumType() {
        return ENUM_TYPE;
    }

    @Override
    protected Class<?>[] constructorTypes() {
        return CONSTRUCTOR_TYPES;
    }

    @Override
    protected IntFunction<CQCodeTypes[]> toArrayFunction() {
        return TO_ARRAY_FUNCTION;
    }

    /**
     * 注册一个新的CQCodeType实例
     * CQCodeType机制特殊，需要保证线程安全性。且所有的参数均不可为null
     * @param name           枚举名称
     * @param function       CQ码function类型
     * @param keys           CQ码全部参数列表。<br>
     *                       例如
     *                       <br>
     *                       <code>
     *                           ["id", "url", "image"]
     *                       </code>
     * @param ignoreAbleKeys 基于keys参数，列举可以被忽略的参数列表，顺序保持一致。<br>
     *                       例如
     *                       <br>
     *                       <code>
     *                       ["url", "image"]
     *                       </code>
     * @param valuesRegex   对应keys参数的，每一个参数的参数值的规则匹配正则。<br>
     *                      例如
     *                      <br>
     *                      <code>
     *                          ["\\d+", "https?://.+", ".+"]
     *                      </code>
     * @param sort          各个类型的排序序号，没什么硬性规则，主要用于实现排序接口
     * @return
     */
    public synchronized CQCodeTypes register(String name, String function, String[] keys, String[] ignoreAbleKeys, String[] valuesRegex, int sort){

        try {
            // 创建实例
            CQCodeTypes cqCodeTypes = super.registerEnum(name, function, keys, ignoreAbleKeys, valuesRegex, sort);
            // 通过CQCodeTypes本身注册新实例
            CQCodeTypes.register(cqCodeTypes);
            return cqCodeTypes;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // 抛出新异常
            throw new RobotRuntimeException(e);
        }
    }

    /**
     * 注册一个新的CQCodeType实例
     * CQCodeType机制特殊，需要保证:
     * <li>
     *     <
     * </li>
     * 线程安全性。且所有的参数均不可为null
     * @param name           枚举名称
     * @param function       CQ码function类型
     * @param keys           CQ码全部参数列表。<br>
     *                       例如
     *                       <br>
     *                       <code>
     *                           ["id", "url", "image"]
     *                       </code>
     * @param ignoreAbleKeys 基于keys参数，列举可以被忽略的参数列表，顺序保持一致。<br>
     *                       例如
     *                       <br>
     *                       <code>
     *                       ["url", "image"]
     *                       </code>
     * @param valuesRegex   对应keys参数的，每一个参数的参数值的规则匹配正则。<br>
     *                      例如
     *                      <br>
     *                      <code>
     *                          ["\\d+", "https?://.+", ".+"]
     *                      </code>
     * @param sort          各个类型的排序序号，没什么硬性规则，主要用于实现排序接口
     * @return
     */
    public static CQCodeTypes registerType(String name, String function, String[] keys, String[] ignoreAbleKeys, String[] valuesRegex, int sort){
        return getInstance().register(name, function, keys, ignoreAbleKeys, valuesRegex, sort);
    }

    /**
     * 参数权限判断
     * @param name          名称
     * @param params        参数列表
     */
    @Override
    protected void requireCanUse(String name, Object[] params) {
        // 参数类型判断
        // 参数有5个：
        // String function, String[] keys, String[] ignoreAbleKeys , String[] valuesRegex , int sort
        // @param function          cq码类型
        // @param keys              参数列表
        // @param ignoreAbleKeys    可以忽略的参数列表
        // @param valuesRegex       对应参数的参数值匹配正则
        // @param sort              类型的排序序号，没什么硬性要求
        String function = (String) params[0];
        String[] keys = (String[]) params[1];
        String[] ignoreAbleKeys = (String[]) params[2];
        String[] valuesRegex = (String[]) params[3];
        int sort = (int) params[4];

        // 全部不可为null
        ObjectsPlus.allNonNull(function, keys, ignoreAbleKeys, valuesRegex);

        // 判断keys的数量与valuesRegex的数量、keys的数量与ignoreAbleKeys的数量是否匹配
        // 参数列表与可忽略参数列表
        if(keys.length < ignoreAbleKeys.length){
            // 要是key的数量比可忽略的key还少
            // 那哪儿行啊
            throw new IllegalArgumentException("参数列表数量少于可忽略参数列表数量！\n" +
                    "keys       ("+keys.length+"): " + Arrays.toString(keys) + "\n" +
                    "ignore keys("+ignoreAbleKeys.length+"): " + Arrays.toString(ignoreAbleKeys) );
        }

        // 忽略参数列表中的值应当包含在keys中
        // 双方排序，排序后进行判断
//        Arrays.sort(keys, Comparator.comparing(k -> k));

//        Arrays.sort(ignoreAbleKeys, Comparator.comparing(k -> k));

        // 将忽略列表拼装为正则匹配并进行匹配
        // ([id])?([keys])?
        StringJoiner keyJoiner = new StringJoiner("][", "[", "]");
        // 排序后添加至joiner
        Arrays.stream(keys).sorted().forEach(keyJoiner::add);
        String keyMatcher = keyJoiner.toString();

        // 遍历ignore列表并判断
        for (String ignoreAbleKey : ignoreAbleKeys) {
            if(!keyMatcher.contains("[" + ignoreAbleKey + "]")){
                throw new IllegalArgumentException("可忽略参数列表中存在未知参数! \n" +
                        "参数列表:    " + Arrays.toString(keys) + "\n" +
                        "未知忽略参数: " + ignoreAbleKey
                        );
            }
        }



        // 参数列表与正则匹配列表
        if(keys.length != valuesRegex.length){
            // 参数匹配正则的数量与参数数量不同
            // 那也不行啊
            throw new IllegalArgumentException("参数列表数量与参数值正则匹配列表数量不同！\n" +
                    "keys       ("+keys.length+"): " + Arrays.toString(keys) + "\n" +
                    "regex keys ("+valuesRegex.length+"): " + Arrays.toString(valuesRegex) );

        }

        // 判断是否存在equalsID
        if(CQCodeTypes.containsID(function, keys)){
            // 如果是等值的，抛出异常
            throw new IllegalArgumentException(
                    "已经存在此CQ码！\n"
                            + "function: " + function + '\n'
                            + "keys:     " + Arrays.toString(keys)
            );
        }

    }
}
