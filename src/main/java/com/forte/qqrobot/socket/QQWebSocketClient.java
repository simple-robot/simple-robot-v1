package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.beans.types.MsgGetTypes;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * socket客户端连接
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:08
 * @since JDK1.8
 **/
public class QQWebSocketClient extends WebSocketClient {

    /** 送信者 */
    private QQWebSocketMsgSender sender;

    /**
     * 构造
     * @param serverURI
     */
    public QQWebSocketClient(URI serverURI) {
        super(serverURI);
    }

    /**
     * 连接成功
     * @param serverHandshake
     */
    @Override
    public final void onOpen(ServerHandshake serverHandshake) {
        System.out.println("已成功连接到服务器");
    }

    /**
     * 接收消息
     * @param s
     */
    @Override
    public final void onMessage(String s) {
        System.out.println("接收到了消息！" + "["+ JSONObject.parseObject(s) +"]");
        //简单复读一下
        doReport(s);

        //接收到了消息，获取act编号
        Integer act = JSONObject.parseObject(s).getInteger("act");
        //消息的封装类
        Object beanForJson = MsgGetTypes.getByAct(act).getBeanForJson(s);

        /*
            获取封装类后，一，分发给监听器
         */


    }

    /**
     * 连接关闭
     * @param i
     * @param s
     * @param b
     */
    @Override
    public final void onClose(int i, String s, boolean b) {
        System.out.println("连接关闭！["+ i +"] " + s);
    }

    /**
     * 出现异常
     * @param e
     */
    @Override
    public final void onError(Exception e) {
        System.out.println("出现异常！");
        e.printStackTrace();
    }






















































    /** 复读记录map key为群号，value[0] 为上一次的消息，value[1] 为这个消息是否已经复读*/
    private static Map<String, Object[]> map = new ConcurrentHashMap<>(1);
    /**
     * 复读
     * @param s
     */
    public void doReport(String s) {
        JSONObject getMsg = JSONObject.parseObject(s);
        Integer type = getMsg.getInteger("act");
        String msg = getMsg.getString("msg");
        System.out.println(type);
        if (type == 2) {
            System.out.println("当前消息：" + msg);
            String fromGroup = getMsg.getString("fromGroup");
            System.out.println("上一次消息：" + Optional.ofNullable(map.get(fromGroup)).map(o -> o[0]+"").orElse("NULL"));
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
               String lastMsg = (String)objects[0];
               Boolean isReport = (Boolean)objects[1];
               //如果当前字符串与上次不一样，记录当前字符串并标记没有复读
                if(!msg.equals(lastMsg)){
                    objects[0] = msg;
                    objects[1] = false;
                }else{
                    System.out.println("又是" + msg);
                    //如果与上次一样，且没有复读，则复读并标记为已复读
                    if(!(Boolean)objects[1]){
                        sendMsgToGroup(msg, fromGroup);
                        objects[1] = true;
                    }
                }

            }
        }else if(type == 21){
            //私聊超级智能AI

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

            String fromQQ = getMsg.getString("fromQQ");

            sendMsgToPrivate(remsg, fromQQ);
        }

    }


    /**
     * 向群组发送消息
     * @param msg
     * @param group
     */
    public void sendMsgToGroup(String msg, String group){
        Map<String, String> map = new HashMap<>(3);

        map.put("act", "101");
        map.put("groupid", group);
        map.put("msg", msg);

        //发送消息
        send(JSON.toJSONString(map));
    }

    /**
     * 私聊消息
     * @param msg
     * @param user
     */
    public void sendMsgToPrivate(String msg, String user){
        Map<String, String> map = new HashMap<>(3);

        map.put("act", "106");
        map.put("QQID", user);
        map.put("msg", msg);

        String remap = JSON.toJSONString(map);

        //发送消息
        send(remap);
    }

}
