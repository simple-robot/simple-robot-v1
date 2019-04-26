package com.forte.forlemoc.socket;


import com.alibaba.fastjson.JSONObject;
import com.forte.forlemoc.SocketResourceDispatchCenter;
import com.forte.forlemoc.beans.inforeturn.InfoReturn;
import com.forte.forlemoc.types.InfoReturnTypes;
import com.forte.forlemoc.types.LemocMsgGetTypes;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.forhttpapi.http.QQHttpMsgSender;
import com.forte.qqrobot.listener.DefaultInitListener;
import com.forte.qqrobot.listener.InitListener;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.socket.MsgSender;
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

    /** socket送信器 */
//    private QQWebSocketMsgSender socketSender;

    /** http送信器 */
//    private QQHttpMsgSender httpSender;

    /** 监听函数管理器 */
    private ListenerManager manager;

    /** 初始化监听器 */
    private Set<InitListener> initListeners;

    /**
     * 构造
     * @param serverURI 父类{@link WebSocketClient}所需参数，用于连接
     */
    public QQWebSocketClient(URI serverURI, ListenerManager manager, Set<InitListener> initListeners) {
        super(serverURI);
        this.manager = manager;
        this.initListeners = initListeners;
    }

    /**
     * 连接成功
     */
    @Override
    public final void onOpen(ServerHandshake serverHandshake) {
        onOpened(serverHandshake);
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();

        //初始化监听器暂时不属于ListenerMethod
        //先执行默认的初始化监听器
        ResourceDispatchCenter.getThreadPool().execute(() -> new DefaultInitListener().init(cqCodeUtil, createNoMethodSender()));
        //连接成功后，调用全部的初始化监听器，在新线程种进行初始化且并行执行
        ResourceDispatchCenter.getThreadPool().execute(() -> initListeners.forEach(l -> l.init(cqCodeUtil, createNoMethodSender())));
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
        //由于是socket连接，是LEMOC插件，接收消息必然存在act参数
        //接收到了消息，获取act编号
        Integer act = JSONObject.parseObject(s).getInteger("act");
        String msg  =  JSONObject.parseObject(s).getString("msg");

        //判断信息类型
        if(act != 0){
            //接收到的消息的封装类
            MsgGet msgGet = (MsgGet) LemocMsgGetTypes.getByAct(act).getBeanForJson(s);
            //组装参数
            Object[] params = getParams(msgGet, msg);
            boolean at = (boolean) params[2];
            /*
                获取封装类后，一，分发给监听器
            */
            manager.onMsg(msgGet);
        }else{
            //如果act为0则说明这个消息是响应消息
            JSONObject returnInfoJson = JSONObject.parseObject(s);
            //获取返回码
            Integer returnCode = returnInfoJson.getInteger("return");
            Class<? extends InfoReturn> typeClass = InfoReturnTypes.getInfoReturnTypesByReturn(returnCode).getReturnClass();

            //封装为对象
            InfoReturn infoReturnBean = returnInfoJson.toJavaObject(typeClass);
            //更新对象
            SocketResourceDispatchCenter.getQQWebSocketInfoReturnManager().update(returnCode, infoReturnBean);

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
        }catch (Exception ignored){}
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
        String localQQCode = SocketResourceDispatchCenter.getLinkConfiguration().getLocalQQCode();
        boolean at = cqCodeUtil.isAt(msg, localQQCode);
        //组装参数
        //* 组装参数不再携带QQWebSocketSender对象和QQHttpSender对象，而是交给Manager创建         *
        return new Object[]{msgGet, cqCodes, at, cqCodeUtil};
    }

    /**
     * 创建
     * @return
     */
    public MsgSender createNoMethodSender(){
        return MsgSender.build(QQWebSocketMsgSender.build(this), QQHttpMsgSender.build());
    }




}
