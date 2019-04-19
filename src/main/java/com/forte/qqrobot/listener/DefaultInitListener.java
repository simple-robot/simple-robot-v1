package com.forte.qqrobot.listener;

import com.forte.qqrobot.SocketResourceDispatchCenter;
import com.forte.forhttpapi.beans.response.Resp_getLoginQQInfo;
import com.forte.qqrobot.configuration.LinkConfiguration;
import com.forte.qqrobot.beans.lemoc.inforeturn.ReturnLoginNick;
import com.forte.qqrobot.beans.lemoc.inforeturn.ReturnLoginQQ;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.socket.MsgSender;
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
        LinkConfiguration linkConfiguration = SocketResourceDispatchCenter.getLinkConfiguration();

        Resp_getLoginQQInfo.LoginQQInfo loginQQInfo = sender.HTTP_MSG_SENDER.getLoginQQInfo().map(Resp_getLoginQQInfo::getResult).orElse(null);
        //如果HTTP请求到了，保存
        if(loginQQInfo != null){
            String qq = loginQQInfo.getQq();
            String nick = loginQQInfo.getNick();
            Integer level = loginQQInfo.getLevel();
            String headimg = loginQQInfo.getHeadimg();
            //保存
            linkConfiguration.setLoginQQInfo(loginQQInfo);
            linkConfiguration.setLocalQQCode(qq);
            linkConfiguration.setLocalQQNick(nick);
            QQLog.info("获取本机QQ号：" + qq);
            QQLog.info("获取本机QQ昵称：" + nick);
            QQLog.info("获取本机QQ等级："+ level);
            QQLog.info("获取本机QQ头像地址："+ headimg);
        }else{
        //如果http请求不到，使用socket保存
        //获取登录的QQ号
        ReturnLoginQQ loginQQ = sender.SOCKET_MSG_SENDER.getLoginQQ();
        //获取登录的qq昵称
        ReturnLoginNick loginNick = sender.SOCKET_MSG_SENDER.getLoginNick();
            //设置并保存登录的QQ号和昵称
            linkConfiguration.setLocalQQCode(loginQQ.getLoginQQ());
            linkConfiguration.setLocalQQNick(loginNick.getLoginNick());
        }


    }
}
