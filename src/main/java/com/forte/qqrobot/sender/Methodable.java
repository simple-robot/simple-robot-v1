package com.forte.qqrobot.sender;

import com.forte.qqrobot.exception.NoSuchBlockNameException;

/**
 * 为送信器提供检测监听函数是否存在的方法
 * 当送信器存在监听函数的时候，送信器应当提供对应监听函数的相关阻断方法
 * 假若没有监听函数也应该提供部分阻断信息获取方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
interface Methodable {

    //**************** 阻断相关 ****************//
    //**************************************
    //*             阻塞机制 默认值均为替换
    //**************************************

    /** 阻断中，是否替换的默认值-false */
    boolean DEFAULT_APPEND = false;

    //**************** 普通阻塞 ****************//

    //**************************************
    //* 所有与开启阻塞相关的，需要将监听函数作为参数的阻断方法
    //* 都需要使用getPlugByMethod方法来获取
    //* 基本上来讲，有返回值的使用真实阻断器，无返回值的使用判断
    //**************************************

    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     */
    void onBlockOnlyThis(boolean append);

    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     * 默认替换
     */
    void onBlockOnlyThis();

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     */
    void onBlockByThisName(boolean append);
    
    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     * 默认替换
     */
    void onBlockByThisName();

    /**
     * 根据组名来使某个分组进入阻断状态
     */
    void onBlockByName(String name, boolean append);

    /**
     * 根据组名来使某个分组进入阻断状态
     * 默认替换
     */
    void onBlockByName(String name);

    /**
     * 取消普通阻塞-即清空阻塞函数容器
     * 此方法使用真实的阻断器
     */
    void unBlock();

    /**
     * 移除全局阻塞
     */
    void unGlobalBlock();

    /**
     * 取消全部阻塞
     * 此方法使用真实的阻断器
     */
    void unAllBlock();


    //**************** 全局阻塞 ****************//

    /**
     * 根据一个名称更新全局阻塞
     */
    void onGlobalBlockByName(String name);

    /**
     * 根据阻断名称的索引来更新全局阻塞
     */
    void onGlobalBlockByNameIndex(int index) throws NoSuchBlockNameException;

    /**
     * 根据第一个阻断名称来更新全剧阻塞
     */
    void onGlobalBlockByFirstName();


    //**************** 获取阻断器部分信息 ****************//

    /**
     * 根据组名判断自己所在的组是否全部在阻断状态中
     */
    boolean isAllOnBlockByName();

    /**
     * 根据组名判断自己所在的组是否有任意在阻断状态中
     */
    boolean isAnyOnBlockByName();

    /**
     * 根据组名判断自己所在的组是否全部没有在阻断状态中
     */
    boolean isNoneOnBlockByName();

    /**
     * 判断自己是否存在于阻断队列
     */
    boolean isOnBlock();

    /**
     * 判断自己是否作为单独的阻断被阻断了
     */
    boolean isOnlyThisOnBlock();

    //**************** 获取阻断状态的两个方法使用真实阻断器 ****************//

    /**
     * 获取当前处于全局阻断状态下的阻断组名
     *
     * @return 阻断组名
     */
    String getOnGlobalBlockName();

    /**
     * 获取当前处于普通阻断状态下的阻断组名列表
     *
     * @return 处于普通阻断状态下的阻断组名列表
     */
    String[] getOnNormalBlockNameArray();



}
