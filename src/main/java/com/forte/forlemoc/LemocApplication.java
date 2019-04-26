package com.forte.forlemoc;

import com.forte.forlemoc.socket.*;
import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.mysql.cj.xdevapi.Client;

/**
 * LEMOC 连接抽象类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:10
 * @since JDK1.8
 **/
public class LemocApplication extends BaseApplication<LinkConfiguration> {

    /**
     * 初始化
     */
//    public void init(){
//        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
//        TypeUtils.compatibleWithJavaBean = true;
//        //资源初始化
//        resourceInit();
//        //线程工厂初始化
//        threadPoolInit();
//    }



    /**
     * 资源初始化
     */
    @Override
    protected void resourceInit(){
        //将连接时的配置对象加入资源调度中心
        SocketResourceDispatchCenter.saveLinkConfiguration(new LinkConfiguration());
        //将QQWebSocketMsgCreator放入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketMsgCreator(new QQJSONMsgCreator());

        //将QQWebSocketInfoReturnManager放入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketInfoReturnManager(new QQWebSocketInfoReturnManager());
    }

    /**
     * 开发者实现的开始连接的方法
     */
    @Override
    protected void start(ListenerManager manager) {
        // 连接socket
        linkSocket(manager);

    }

    /**
     * 获取Config的方法实例
     * @return
     */
    @Override
    protected LinkConfiguration getConfiguration() {
        return SocketResourceDispatchCenter.getLinkConfiguration();
    }

    /**
     * 连接socket
     */
    private QQWebSocketClient linkSocket(ListenerManager manager){
        //连接配置对象
        LinkConfiguration configuration = getConfiguration();
        //主要的socket连接，如果无法结果精准获取请求后的返回值的话，后期会同时连接多个socket
        //由于这里用到了config对象，需要
        return QQWebSocketLinker.link(configuration.getSocketClient(), manager, configuration.getLinkUrl());
    }





//    /**
//     * 执行用主程序
//     */
//    public static void run(LemocApp lemocUser) {
//        LemocApplication lemocApp = new LemocApplication();
//
//        //初始化方法
//        lemocApp.init();
//
//        //连接之前
//        LinkConfiguration linkConfiguration = SocketResourceDispatchCenter.getLinkConfiguration();
//        lemocUser.before(linkConfiguration);
//        //连接之后，如果没有进行扫描，则默认扫描启动类同级包且排除此启动类
//        String packageName = lemocUser.getClass().getPackage().getName();
//
//        //如果没有扫描过，扫描本包全部
//        linkConfiguration.scannerIfNotScanned(packageName, c -> !c.equals(lemocUser.getClass()));
//
//        //连接socket
//        QQWebSocketClient client = lemocApp.linkSocket();
//
//        //获取CQCodeUtil实例
//        CQCodeUtil cqCodeUtil = SocketResourceDispatchCenter.getCQCodeUtil();
//
//        //连接之后
//        lemocUser.after(cqCodeUtil, QQWebSocketMsgSender.build(client));
//    }

}
