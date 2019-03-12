package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGet;
import com.forte.qqrobot.beans.types.MsgGetTypes;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.listener.invoker.ListenerInvoker;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Set;

/**
 * socket客户端连接
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:08
 * @since JDK1.8
 **/
public class QQWebSocketClient extends WebSocketClient {

    /** 送信者 */
    private QQWebSocketMsgSender sender;

    /** 监听器列表 */
    private Set<SocketListener> listeners;

    /** 初始化监听器 */
    private Set<InitListener> initListeners;

    /**
     * 构造
     * @param serverURI 父类{@link WebSocketClient}所需参数，用于连接
     * @param listeners 监听器列表
     */
    public QQWebSocketClient(URI serverURI, Set<SocketListener> listeners, Set<InitListener> initListeners) {
        super(serverURI);
        this.sender = QQWebSocketMsgSender.of(this);
        this.listeners = listeners;
        this.initListeners = initListeners;
    }

    /**
     * 连接成功
     */
    @Override
    public final void onOpen(ServerHandshake serverHandshake) {
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();
        //连接成功后，调用全部的初始化监听器
        initListeners.forEach(l -> l.init(cqCodeUtil, sender));
    }

    /**
     * 接收消息，不可重写
     * @param s 接收的消息
     */
    @Override
    public final void onMessage(String s) {
        //接收到了消息，获取act编号
        Integer act = JSONObject.parseObject(s).getInteger("act");
        String msg  =  JSONObject.parseObject(s).getString("msg");
        //接收到的消息的封装类
        MsgGet msgGet = (MsgGet) MsgGetTypes.getByAct(act).getBeanForJson(s);

        //组装参数
        Object[] params = getParams(msgGet, msg);

        /*
            获取封装类后，一，分发给监听器
         */
        ListenerInvoker listenerInvoker = ResourceDispatchCenter.getListenerInvoker();
        listenerInvoker.invokeListenerByParams(listeners, params);

    }

    /**
     * 连接关闭
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("连接关闭！["+ i +"] " + s);
    }

    /**
     * 出现异常
     * @param e 异常
     */
    @Override
    public void onError(Exception e) {
        System.out.println("出现异常！");
        e.printStackTrace();
    }


    /**
     * 组装传给监听器的参数
     * @param msgGet    接收到的消息封装类
     * @param msg       信息内容
     * @return  传给监听器的参数
     */
    private Object[] getParams(MsgGet msgGet, String msg){
        //配置参数
        //获取cqCodeUtil
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();
        //获取全部CQ码
        CQCode[] cqCodes = cqCodeUtil.getCQCodeFromMsg(msg).toArray(new CQCode[0]);
        //判断是否at自己
        //获取本机QQ号
        String localQQCode = ResourceDispatchCenter.getLinkConfiguration().getLocalQQCode();
        boolean at = cqCodeUtil.isAt(msg, localQQCode);
        //组装参数
        return new Object[]{msgGet, cqCodes, at, cqCodeUtil, sender};
    }















































//    /** 复读记录map key为群号，value[0] 为上一次的消息，value[1] 为这个消息是否已经复读*/
//    private static Map<String, Object[]> map = new ConcurrentHashMap<>(1);
//    /**
//     * 复读
//     * @param s
//     */
//    public void doReport(String s) {
//        Integer type = JSON.parseObject(s).getInteger("act");
//        String msg = JSON.parseObject(s).getString("msg");
//        System.out.println(type);
//        if (type == 2) {
//            System.out.println("当前消息：" + msg);
//            String fromGroup = JSON.parseObject(s).getString("fromGroup");
//            System.out.println("上一次消息：" + Optional.ofNullable(map.get(fromGroup)).map(o -> o[0]+"").orElse("NULL"));
//            //如果是群消息，记录内容
//            //如果与上次一样，且没有复读，则复读
//            Object[] objects = map.get(fromGroup);
//            if (objects == null) {
//                //如果不存在，记录
//                Object[] re = new Object[2];
//                re[0] = msg;
//                re[1] = false;
//                map.put(fromGroup, re);
//            } else {
//                //如果存在，判断
//                String lastMsg = (String) objects[0];
//                Boolean isReport = (Boolean) objects[1];
//                //如果当前字符串与上次不一样，记录当前字符串并标记没有复读
//                if(!msg.equals(lastMsg)){
//                    objects[0] = msg;
//                    objects[1] = false;
//                }else{
//                    System.out.println("又是" + msg);
//                    //如果与上次一样，且没有复读，则复读并标记为已复读
//                    if(!isReport){
//                        System.out.println(fromGroup + ":" + msg);
//                        sender.sendGroupMsg(msg, fromGroup);
//                        System.out.println("send~");
//                        objects[1] = true;
//                    }
//                }
//
//            }
//        }else if(type == 21){
//            //私聊超级智能AI
//
//            String rem = "#\\(\\(您\\)\\)#";
//
//            String remsg = msg
//                    .replaceAll("虵", "江")
//                    .replaceAll("我有女朋友吗","没有")
//                    .replaceAll("吗" , "")
//                    .replaceAll("\\?","!")
//                    .replaceAll("？","!")
//                    .replaceAll(rem ,"你")
//                    .replaceAll("我",rem)
//                    .replaceAll("你","我")
//                    .replaceAll(rem ,"你")
//                    ;
//
//            String fromQQ = JSON.parseObject(s).getString("fromQQ");
//
//            sender.sendMsgPrivate(remsg, fromQQ);
//        }
//    }



}
