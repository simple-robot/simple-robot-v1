package com.forte.qqrobot.socket;

import com.forte.qqrobot.beans.msgsend.MsgSend;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import org.apache.http.protocol.HTTP;

/**
 * 消息发送器整合器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/30 10:31
 * @since JDK1.8
 **/
public class MsgSender {

    /** socket连接 */
    public final QQWebSocketMsgSender SOCKET_MSG_SENDER;
    /** 当不使用LEMOC socket时，使用此对象 */
    private static final QQWebSocketMsgSender EMPTY_SOCKET_MSG_SENDER = new DefaultSocketSender(null);

    /** http请求 */
    public final QQHttpMsgSender HTTP_MSG_SENDER;
    /** 当不使用HTTP API时，使用此对象 */
    private static final QQHttpMsgSender EMPTY_HTTP_MSG_SENDER = new DefaultHttpSender();

    /** 使用此连接管理器的是哪个监听方法 */
    private final ListenerMethod LISTENER_METHOD;


    /**
     * socket连接是否可用
     */
    public boolean socketAble(){
        return SOCKET_MSG_SENDER != null;
    }

    /**
     * http连接是否可用
     */
    public boolean httpAble(){
        return HTTP_MSG_SENDER.equals(EMPTY_HTTP_MSG_SENDER);
    }

    /**
     * 是否存在监听器函数
     * @return
     */
    public boolean hasMethod(){
        return LISTENER_METHOD != null;
    }


    //**************************************
    //*             阻塞机制
    //**************************************

    /**
     * 开启或者关闭阻塞状态
     * @param enable
     */
    private void block(boolean enable){

    }

    /**
     * 开启阻塞
     */
    public void inBlock(){
        block(true);
    }

    /**
     * 取消阻塞
     */
    public void unBlock(){
        block(false);
    }


    //**************************************
    //*             工厂方法
    //**************************************

    public static MsgSender build(QQWebSocketMsgSender socketMsgSender, QQHttpMsgSender httpMsgSender, ListenerMethod listenerMethod){
        return new MsgSender(socketMsgSender, httpMsgSender, listenerMethod);
    }

    public static MsgSender build(QQWebSocketMsgSender socketMsgSender, QQHttpMsgSender httpMsgSender){
        return new MsgSender(socketMsgSender, httpMsgSender, null);
    }

    public static MsgSender build(QQWebSocketMsgSender socketMsgSender, ListenerMethod listenerMethod){
        return new MsgSender(socketMsgSender, null, listenerMethod);
    }

    public static MsgSender build(QQWebSocketMsgSender socketMsgSender){
        return new MsgSender(socketMsgSender, null, null);
    }

    public static MsgSender build(QQHttpMsgSender httpMsgSender, ListenerMethod listenerMethod){
        return new MsgSender(null, httpMsgSender, listenerMethod);
    }

    public static MsgSender build(QQHttpMsgSender httpMsgSender){
        return new MsgSender(null, httpMsgSender, null);
    }

    /**
     * 构造
     */
    private MsgSender(QQWebSocketMsgSender socketMsgSender, QQHttpMsgSender httpMsgSender, ListenerMethod listenerMethod){
        this.SOCKET_MSG_SENDER  = socketMsgSender == null ? EMPTY_SOCKET_MSG_SENDER : socketMsgSender;
        this.HTTP_MSG_SENDER    = httpMsgSender == null ? EMPTY_HTTP_MSG_SENDER : httpMsgSender;
        this.LISTENER_METHOD    = listenerMethod;
    }
}
