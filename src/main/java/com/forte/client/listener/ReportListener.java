package com.forte.client.listener;

import com.forte.qqrobot.anno.Spare;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.msgget.MsgPrivate;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.listener.MsgPrivateListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DEMO-复读监听器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 11:59
 * @since JDK1.8
 **/
@Spare
public class ReportListener implements MsgGroupListener, MsgPrivateListener {

    /** 复读记录map key为群号，value[0] 为上一次的消息，value[1] 为这个消息是否已经复读*/
    private static Map<String, Object[]> map = new ConcurrentHashMap<>(1);

    /**
     * 接收到了群的消息，复读
     */
    @Override
    public boolean onMessage(MsgGroup msgGroup, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        //如果是at自己，不复读
        if(at){
            return false;
        }
        String msg = msgGroup.getMsg();
//            System.out.println("当前消息：" + msg);
            String fromGroup = msgGroup.getFromGroup();
//            System.out.println("上一次消息：" + Optional.ofNullable(map.get(fromGroup)).map(o -> o[0]+"").orElse("NULL"));
            //如果是群消息，记录内容
            //如果与上次一样，且没有复读，则复读
            Object[] objects = map.get(fromGroup);
            if (objects == null) {
                //如果不存在，记录
                Object[] re = new Object[2];
                re[0] = msg;
                re[1] = false;
                map.put(fromGroup, re);
            } else {
                //如果存在，判断
                String lastMsg = (String) objects[0];
                Boolean isReport = (Boolean) objects[1];
                //如果当前字符串与上次不一样，记录当前字符串并标记没有复读
                if(!msg.equals(lastMsg)){
                    objects[0] = msg;
                    objects[1] = false;
                }else{
//                    System.out.println("又是" + msg);
                    //如果与上次一样，且没有复读，则复读并标记为已复读
                    if(!isReport){
                        sender.sendGroupMsg(fromGroup, msg);
                        objects[1] = true;
                    }
                }
            }
            return true;
        }

    /**
     * 接收到了私聊信息
     */
    @Override
    public boolean onMessage(MsgPrivate msgPrivate, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        //私聊超级智能AI

        String msg = msgPrivate.getMsg();
        String rem = "#\\(\\(您\\)\\)#";

        String remsg = msg
                .replaceAll("虵", "江")
                .replaceAll("我有女朋友吗","没有")
                .replaceAll("吗" , "")
                .replaceAll("\\?","!")
                .replaceAll("？","!")
                .replaceAll(rem ,"你")
                .replaceAll("我",rem)
                .replaceAll("你","我")
                .replaceAll(rem ,"你")
                ;

        String fromQQ = msgPrivate.getFromQQ();
        sender.sendMsgPrivate(fromQQ, remsg);
        return true;
    }
}
