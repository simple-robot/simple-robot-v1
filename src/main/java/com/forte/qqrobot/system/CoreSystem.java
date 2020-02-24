package com.forte.qqrobot.system;


import com.forte.qqrobot.ResourceDispatchCenter;

import java.util.Properties;

/**
 * 核心中类似于System的类, 也类似于一种工具类或者静态资源类。会逐步取代{@link ResourceDispatchCenter}中的部分功能。
 * @author ForteScarlet
 * @version  1.7.0
 */
public final class CoreSystem {

    /** 当前程序的RUN_TIME对象 */
    private static final Runtime RUN_TIME;
    /** 当前可用的cpu核心数量 */
    private static final int CPU_CORE;

    // 加载资源
    static {
        // 首先初始化Runtime参数
        RUN_TIME = Runtime.getRuntime();
        CPU_CORE = RUN_TIME.availableProcessors();

    }

    /**
     * 获取程序可用的CPU核心数量
     * @return cpu核心数量
     */
    public static int getCpuCore(){
        return CPU_CORE;
    }

    /**
     * <pre> 根据核心数量和阻塞系数计算一个线程池所需要的最佳线程数量。
     * <pre> 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
     * <pre> 阻塞系数代表整个任务下来，线程有百分之多少的时间是处于阻塞状态的。
     * @param blockingFactor 阻塞系数，0~1之间, 不可等于1
     * @return 线程数量
     */
    public static int getBestPoolSize(double blockingFactor){
        if(blockingFactor < 0 || blockingFactor >= 1){
            throw new IllegalArgumentException("block factor must in [0, 1), and cannot be 1, but: " + blockingFactor);
        }
        // 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
        return (int) (CPU_CORE / (1 - blockingFactor));
    }



    /**
     * @see System#getProperties()
     * @return System properties.
     */
    public static Properties getProperties(){
        return System.getProperties();
    }



}
