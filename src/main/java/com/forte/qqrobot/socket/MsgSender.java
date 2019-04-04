package com.forte.qqrobot.socket;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.exception.NoSuchBlockNameException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerPlug;

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
    //*             阻塞机制 默认值均为替换
    //**************************************

    private static final boolean DEFAULT_APPEND = false;

    //**************** 普通阻塞 ****************//


    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     */
    public void onBlockOnlyThis(boolean append){
        getPlug().onBlockByMethod(this.LISTENER_METHOD, append);
    }

    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     * 默认替换
     */
    public void onBlockOnlyThis(){
        onBlockOnlyThis(DEFAULT_APPEND);
    }

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     */
    public void onBlockByThisName(boolean append){
        getPlug().onBlockByName(this.LISTENER_METHOD, append);
    }

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     * 默认替换
     */
    public void onBlockByThisName(){
        onBlockByThisName(DEFAULT_APPEND);
    }

    /**
     * 根据组名来使某个分组进入阻断状态
     */
    public void onBlockByName(String name, boolean append){
        getPlug().onBlock(name, append);
    }

    /**
     * 根据组名来使某个分组进入阻断状态
     * 默认替换
     */
    public void onBlockByName(String name){
        getPlug().onBlock(name, DEFAULT_APPEND);
    }

    /**
     * 取消普通阻塞-即清空阻塞函数容器
     */
    public void unBlock(){
        //获取阻断器
        getPlug().unBlock();
    }

    /**
     * 取消全部阻塞
     */
    public void unAllBlock(){
        ListenerPlug plug = getPlug();
        plug.unGlobalBlock();
        plug.unBlock();
    }


    //**************** 全局阻塞 ****************//

    /**
     * 根据一个名称更新全局阻塞
     */
    public void onGlobalBlockByName(String name){
        //获取阻断器
        getPlug().onGlobalBlock(name);
    }

    /**
     * 根据阻断名称的索引来更新全局阻塞
     */
    public void onGlobalBlockByNameIndex(int index) throws NoSuchBlockNameException {
        //获取阻断器
        getPlug().onGlobalBlock(this.LISTENER_METHOD, index);
    }

    /**
     * 根据第一个阻断名称来更新全剧阻塞
     */
    public void onGlobalBlockByFirstName(){
        //获取阻断器
        getPlug().onGlobalBlock(this.LISTENER_METHOD);
    }

    /**
     * 移除全局阻塞
     */
    public void unGlobalBlock(){
        getPlug().unGlobalBlock();
    }
        
    
    //**************** 获取阻断器部分信息 ****************//

    /**
     * 根据组名判断自己所在的组是否全部在阻断状态中
     */
    public boolean isAllOnBlockByName(){
        return getPlug().isAllOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 根据组名判断自己所在的组是否有任意在阻断状态中
     */
    public boolean isAnyOnBlockByName(){
        return getPlug().isAnyOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 根据组名判断自己所在的组是否全部没有在阻断状态中
     */
    public boolean isNoneOnBlockByName(){
        return getPlug().isNoneOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 判断自己是否存在于阻断队列
     */
    public boolean isOnBlock(){
        return getPlug().isOnNormalBlockByThis(this.LISTENER_METHOD);
    }

    /**
     * 判断自己是否作为单独的阻断被阻断了
     */
    public boolean isOnlyThisOnBlock(){
        return getPlug().osOnNormalBlockByOnlyThis(this.LISTENER_METHOD);
    }

    /**
     * 获取当前处于全局阻断状态下的阻断组名
     * @return 阻断组名
     */
    public String getOnGlobalBlockName(){
        return getPlug().getGlobalBlockName();
    }

    /**
     * 获取当前处于普通阻断状态下的阻断组名列表
     * @return 处于普通阻断状态下的阻断组名列表
     */
    public String[] getOnNormalBlockNameArray(){
        return getPlug().getNormalBlockNameArray();
    }

    

    /**
     * 获取阻断器
     */
    private ListenerPlug getPlug(){
        return ResourceDispatchCenter.getListenerPlug();
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
