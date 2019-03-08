package com.forte.client;

import com.forte.qqrobot.RobotApplication;
import com.forte.qqrobot.config.LinkConfiguration;
import com.forte.qqrobot.socket.QQWebSocketClient;
import com.forte.qqrobot.socket.QQWebSocketManager;
import com.forte.qqrobot.socket.QQWebSocketMsgCreater;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.SchedulerException;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:16
 * @since JDK1.8
 **/
public class RunApplication extends RobotApplication {

    /**
     * 主程序执行
     * @param args
     * @throws SchedulerException
     */
    public static void main(String[] args) {
        run(new RunApplication());
    }

    /**
     * socket连接之前
     */
    @Override
    public void beforeLink(LinkConfiguration configuration) {
        configuration.setSocketClient(MyRobotApplication.class);
        configuration.setLinkIp("139.199.116.5");
    }

    /**
     * socket连接之后
     */
    @Override
    public void afterLink(QQWebSocketManager manager) {
        QQWebSocketClient mainSocket = manager.getMainSocket();
//        mainSocket.sendMsgToPrivate("连接了\r\n换个行", "1149159218");
//        for(int i = 170;i>=0;i--){
//            String s = CQCodeUtil.getCQCode_face(i+"");
//            System.out.println(s);
//            mainSocket.sendMsgToPrivate(s, "511371712");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//            }
//        }
//        System.out.println("发完了");
        mainSocket.send(QQWebSocketMsgCreater.getResponseJson_sendGroupMsg("581250423", "嘤嘤嘤！"));


        //创建定时任务
//        try {
//            new TimeTasks().run();
//        } catch (SchedulerException ignore) {
//        }
    }


}
