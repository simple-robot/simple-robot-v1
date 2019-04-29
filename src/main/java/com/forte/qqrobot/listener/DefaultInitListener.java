package com.forte.qqrobot.listener;


import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 默认存在的初始化监听器，用于获取登录的qq号信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/20 10:55
 * @since JDK1.8
 **/
public class DefaultInitListener implements InitListener {
    /**
     * 初始化方法获取登录的QQ号和QQ昵称
     * 由于初始化方法是异步进行执行的，所以可能会存在时间误差
     */
    @Override
    public void init(CQCodeUtil cqCodeUtil, MsgSender sender) {
    }
}
