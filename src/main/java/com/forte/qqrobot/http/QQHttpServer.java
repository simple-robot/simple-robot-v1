package com.forte.qqrobot.http;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.net.InetSocketAddress;
import java.util.function.Function;

/**
 * 应用于HTTP API交互的server服务器端
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/30 10:54
 * @since JDK1.8
 **/
public class QQHttpServer {



    /**
     * 开启服务
     * @param port 端口号
     * @param listenerPath 监听地址
     * @param  backlog TCP连接最大并发数, 传 0 或负数表示使用默认值
      */
    public void start(int port, String listenerPath, int backlog, String encode, Function<String, Resp> msgConsumer, String[] methods){
        try{
            HttpServerProvider provider = HttpServerProvider.provider();
            HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(port), backlog);

            //注册监听地址
            httpserver.createContext(listenerPath, new QQHttpHandler(encode, msgConsumer, methods));

            //启动服务
            httpserver.start();
        }catch (Exception e){
            //出现异常，打印
            e.printStackTrace();
        }

    }



}
