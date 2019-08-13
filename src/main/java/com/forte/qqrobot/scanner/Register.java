package com.forte.qqrobot.scanner;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.sender.MsgSender;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 注册器接口，定义一系列对各种功能进行注册的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Register {

    /** 注册监听函数 */
    void registerListener();

    /** 注册定时任务 */
    void registerTimeTask(MsgSender sender);

    /** 注册定时任务 */
    void registerTimeTask(Supplier<MsgSender> senderSupplier);

    /** 进行依赖注入 */
    void registerDependCenter();

    /** 不需要@Beans注解的依赖注入
     *  @param beans 需要提供通用Beans注解对象
     * */
    void registerDependCenterWithoutAnnotation(Beans beans);

    /* —————————— 以上为一些必要的操作，以下为自定义执行 —————————— */

    /**
     * 执行一些其他的，可能是自定义的任务
     * @param filter    过滤器，根据需求获取到你所需要的class类型，不会为空
     * @param task      你要执行的任务。参数为过滤好的Class数组
     */
    <T> T performingTasks(Predicate<? super Class<?>> filter, Function<Class<?>[], T> task);


    default <T> T performingTasks(Function<Class<?>[], T> task){
        return performingTasks(c -> true, task);
    }

    /**
     * 无返回值的执行一个任务
     */
    default void performingTasks(Predicate<? super Class<?>> filter, Consumer<Class<?>[]> task){
        performingTasks(filter, c -> {
            task.accept(c);
            return null;
        });
    }

    /**
     * 无返回值的执行一个任务
     */
    default void performingTasks(Consumer<Class<?>[]> task){
        performingTasks(c -> true, c -> {
            task.accept(c);
            return null;
        });
    }


}
