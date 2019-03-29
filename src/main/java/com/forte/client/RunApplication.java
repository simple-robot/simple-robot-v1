package com.forte.client;

import com.forte.client.utils.ConstantData;
import com.forte.qqrobot.RobotApplication;
import com.forte.qqrobot.LinkConfiguration;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;

/**
 * DEMO-如何使用
 * 继承RobotApplication类，
 * 实现两个抽象方法，并在{{@link #before}方法中进行配置
 * 创建main方法并将本类实例放入父类run 方法中执行
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:16
 * @since JDK1.8
 **/
public class RunApplication extends RobotApplication {

    /**
     * 主程序执行
     */
    public static void main(String[] args) {
        run(new RunApplication());
    }


    /**
     * socket连接之前
     * @param configuration 连接配置文件
     */
    @Override
    public void before(LinkConfiguration configuration) {
        //服务器地址
        configuration.setLinkIp( ConstantData.LEMOC_IP );
        //注册初始化监听器
        configuration.scannerInitListener("com.forte.client.listener");
//        扫描加载普通监听器
        configuration.scannerListener("com.forte.client.listener");

        //配置http api插件信息
        configuration.setHTTP_API_ip("139.199.116.5");
        configuration.setHTTP_API_port(8877);

    }

    /**
     * socket连接之后
     */
    @Override
    public void after(QQWebSocketMsgSender sender) {
    }
}
