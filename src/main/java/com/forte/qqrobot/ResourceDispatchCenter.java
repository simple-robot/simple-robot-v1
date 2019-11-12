package com.forte.qqrobot;

import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.listener.invoker.plug.ListenerPlug;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.timetask.TimeTaskManager;
import com.forte.qqrobot.utils.BaseLocalThreadPool;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.SingleFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 资源调度中心抽象类，基本是静态方法，开发者可以实现此类并增加方法
 * <br>主要用于框架内部，用于获取一些功能性类的单例对象
 * <br>获取前需要保证在初始化方法 Application 中已经储存过
 * <br>用户一般使用的是依赖资源: {@link com.forte.qqrobot.depend.DependCenter}
 * 所有的资源几乎都在这里
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 14:42
 * @since JDK1.8
 **/
public abstract class ResourceDispatchCenter {

    /** 资源调度中心使用的单例工厂 */
    private static final SingleFactory resourceSingleFactory = SingleFactory.build(ResourceDispatchCenter.class);

    /** 保存配置类的类型。仅保存注入的第一个 */
    private static Class<? extends BaseConfiguration> configType = null;

    /**
     * 记录一个单例对象
     * @param bean  单例对象
     * @param <T>   单例对象的类型
     */
    protected static <T> void save(T bean){
        resourceSingleFactory.set(bean);
        if(configType == null && (bean instanceof BaseConfiguration)){
            configType = (Class<BaseConfiguration>) bean.getClass();
        }
    }

    /**
     * 储存一个CQCodeUtil对象到单例工厂
     * @param cqCodeUtil    单例对象
     */
    static void saveCQCodeUtil(CQCodeUtil cqCodeUtil){
        //将CQCodeUtil放入单例工厂
        save(cqCodeUtil);
    }

    /**
     * 储存一个监听函数管理器
     * @param listenerManager 单例对象
     */
    public static void saveListenerManager(ListenerManager listenerManager){
        save(listenerManager);
    }

    /**
     * 储存一个监听器分类器
     * @param listenerFilter 单例对象
     */
    static void saveListenerFilter(ListenerFilter listenerFilter){
        save(listenerFilter);
    }

    /**
     * 储存一个监听函数扫描器
     * @param listenerMethodScanner 监听函数扫描器
     */
    static void saveListenerMethodScanner(ListenerMethodScanner listenerMethodScanner){
        save(listenerMethodScanner);
    }

    /**
     * 保存一个配置类对象
     * <br> 对应的get方法需要在对应的子类中自行书写
     * @param configuration 配置类对象
     */
    protected static <T extends BaseConfiguration> void saveConfiguration(T configuration){
        save(configuration);
    }

    /**
     * 储存一个监听函数阻断器
     * @param listenerPlug 监听函数阻断器
     */
    static void savePlug(Plug listenerPlug){
        save(listenerPlug);
    }

    /**
     * 储存一个定时任务管理器
     * @param timeTaskManager 定时任务管理器
     */
    static void saveTimeTaskManager(TimeTaskManager timeTaskManager){
        save(timeTaskManager);
    }

    /**
     * 保存一个StdSchedulerFactory定时任务工厂
     * @param stdSchedulerFactory 定时任务工厂
     */
    static void saveStdSchedulerFactory(StdSchedulerFactory stdSchedulerFactory){
        save(stdSchedulerFactory);
    }

    /**
     * 保存一个依赖管理中心
     * @param dependCenter 依赖管理中心
     */
    static void saveDependCenter(DependCenter dependCenter){
        save(dependCenter);
    }


    //**************** get ****************//


    /**
     * 获取一个指定类型的单例对象-如果储存过的话
     * @param beanClass 指定类型
     * @param <T>       类型
     * @return          单例对象
     */
    protected static <T> T get(Class<T> beanClass){
        return resourceSingleFactory.get(beanClass);
    }

    /**
     * 获取基础配置类
     */
    public static BaseConfiguration getBaseConfigration(){
        return get(configType);
    }

    /**
     * 获得一个CQCodeUtil单例对象
     * @return  CQCodeUtil单例对象
     */
    public static CQCodeUtil getCQCodeUtil(){
        return get(CQCodeUtil.class);
    }

    /**
     * 获取一个ListenerInvoker单例对象
     * @return ListenerInvoker单例对象
     */
    public static ListenerManager getListenerManager(){
        return get(ListenerManager.class);
    }

    /**
     * 获取一个ListenerFilter单例对象
     * @return ListenerFilter单例对象
     */
    public static ListenerFilter getListenerFilter(){
        return get(ListenerFilter.class);
    }


    /**
     * 获取一个ListenerMethodScanner单例对象
     * @return ListenerMethodScanner单例对象
     */
    public static ListenerMethodScanner getListenerMethodScanner(){
        return get(ListenerMethodScanner.class);
    }

    /**
     * 获取一个ListenerPlug单例对象
     * @return ListenerPlug单例对象
     */
    public static Plug getPlug(){
        return get(ListenerPlug.class);
    }

    /**
     * 获取一个TimeTaskManager单例对象
     * @return TimeTaskManager单例对象
     */
    public static TimeTaskManager getTimeTaskManager(){
        return get(TimeTaskManager.class);
    }

    /**
     * 获取一个StdSchedulerFactory单例对象
     * @return  StdSchedulerFactory单例对象
     */
    public static StdSchedulerFactory getStdSchedulerFactory(){
        return get(StdSchedulerFactory.class);
    }

    /**
     * 获取一个DependCenter单例对象
     * @return  DependCenter单例对象
     */
    public static DependCenter getDependCenter(){
        return get(DependCenter.class);
    }

    //**************** 线程池相关 ****************//


    /**
     * 获取线程池的名称
     */
    private final static String THREAD_POOL_NAME = "QQ_ROBOT_ONMESSAGE_THREAD_POOL";

    /**
     * 获取线程池
     * @return  线程池单例
     */
    public static Executor getThreadPool(){
        return BaseLocalThreadPool.getThreadPool(THREAD_POOL_NAME);
    }

    /**
     * 获取指定名称的线程池
     * @return  线程池单例
     */
    public static Executor getThreadPool(String threadPoolName){
        return BaseLocalThreadPool.getThreadPool(threadPoolName);
    }

    static void saveThreadPool(String threadPoolName){
        BaseLocalThreadPool.getThreadPool(threadPoolName);
    }

    static void saveThreadPool(){
        BaseLocalThreadPool.getThreadPool(THREAD_POOL_NAME);
    }

    static void saveThreadPool(String threadPoolName, BaseLocalThreadPool.PoolConfig config){
        BaseLocalThreadPool.getThreadPool(threadPoolName, config);
    }

    static void saveThreadPool(BaseLocalThreadPool.PoolConfig config){
        BaseLocalThreadPool.getThreadPool(THREAD_POOL_NAME, config);
    }

    //**************** 其他API ****************//

    /**
     * 重置资源-即清空所有资源
     */
    public static void reset(){
        resourceSingleFactory.clear();
    }



}
