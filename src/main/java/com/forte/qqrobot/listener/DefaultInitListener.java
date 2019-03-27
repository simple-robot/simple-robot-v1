package com.forte.qqrobot.listener;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.inforeturn.ReturnLoginNick;
import com.forte.qqrobot.beans.inforeturn.ReturnLoginQQ;
import com.forte.qqrobot.LinkConfiguration;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
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
    public void init(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        //获取登录的QQ号
        ReturnLoginQQ loginQQ = sender.getLoginQQ();

        //获取登录的qq昵称
        ReturnLoginNick loginNick = sender.getLoginNick();

        //设置并保存登录的QQ号和昵称
        LinkConfiguration linkConfiguration = ResourceDispatchCenter.getLinkConfiguration();
        linkConfiguration.setLocalQQCode(loginQQ.getLoginQQ());
        QQLog.info("获取本机QQ号：" + loginQQ.getLoginQQ());
        linkConfiguration.setLocalQQNick(loginNick.getLoginNick());
        QQLog.info("获取本机QQ昵称：" + loginNick.getLoginNick());


    }
}
