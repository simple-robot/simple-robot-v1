package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGet;
import com.forte.qqrobot.beans.types.MsgGetTypes;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.SocketListener;
import com.forte.qqrobot.listener.invoker.ListenerInvoker;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.BaseLocalThreadPool;
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
        onOpened(serverHandshake);
    }

    /**
     * 可重写的连接成功回调, 此时已经加载完初始化监听器
     */
    public void onOpened(ServerHandshake serverHandshake){
    }


    /**
     * 接收消息，不可重写
     * @param s 接收的消息
     */
    @Override
    public final void onMessage(String s) {
        //多线程接收消息
        ResourceDispatchCenter.getThreadPool().execute(() -> onMessaged(s));
    }

    /**
     * 处理接收到的消息
     * @param s
     */
    private final void onMessaged(String s){
        //接收到了消息，获取act编号
        Integer act = JSONObject.parseObject(s).getInteger("act");
        String msg  =  JSONObject.parseObject(s).getString("msg");

        //判断信息类型
        if(act != 0){
            //接收到的消息的封装类
            MsgGet msgGet = (MsgGet) MsgGetTypes.getByAct(act).getBeanForJson(s);
            //组装参数
            Object[] params = getParams(msgGet, msg);
        /*
            获取封装类后，一，分发给监听器
         */
            ListenerInvoker listenerInvoker = ResourceDispatchCenter.getListenerInvoker();
            listenerInvoker.invokeListenerByParams(listeners, params);
        }else{
            //如果act为0则说明这个消息是响应消息
            System.out.println("响应消息：" + s);
        }
    }

    /**
     * 连接关闭
     */
    @Override
    public final void onClose(int i, String s, boolean b) {
        //连接关闭回调
        onClosed(i, s, b);
    }

    /**
     * 可重写的连接关闭回调方法
     */
    public void onClosed(int i, String s, boolean b){
        System.out.println("连接关闭！["+ i +"] " + s);
    }

    /**
     * 出现异常
     * @param e 异常
     */
    @Override
    public final void onError(Exception e) {
        onErrored(e);
        //出现异常后尝试关闭连接
        try{
            this.close();
        }catch (Exception ignore){}
    }

    /**
     * 可以重写的异常处理方法
     * @param e 异常
     */
    public void onErrored(Exception e){
        QQLog.debug("出现异常！");
        e.printStackTrace();
    }


    /**
     * 组装传给监听器的参数
     * @param msgGet    接收到的消息封装类
     * @param msg       信息内容
     * @return  传给监听器的参数
     */
    private final Object[] getParams(MsgGet msgGet, String msg){
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




}
