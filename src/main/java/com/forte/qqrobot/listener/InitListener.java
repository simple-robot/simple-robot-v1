package com.forte.qqrobot.listener;


import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 连接初始化监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 15:13
 * @since JDK1.8
 **/
@Deprecated
public interface InitListener {

    /**
     * 初始化方法
     */
    void init(CQCodeUtil cqCodeUtil, MsgSender sender);

}
