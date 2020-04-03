package com.forte.qqrobot.server;

import com.forte.qqrobot.server.bean.SenderAPIStatistics;
import com.forte.qqrobot.server.path.ServerContextFactory;
import com.forte.qqrobot.server.path.ServerContextFactoryImpl;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 本地服务器
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class RobotLocalServer {

    /**
     * 开启服务
     * @param port 端口号
     * @param  backlog TCP连接最大并发数, 传 0 或负数表示使用默认值
     * @param encode 编码
     */
    public static HttpServer start(int port, int backlog,
                                     //以下参数为创建QQHttpHandler所需要的
                                     String encode
    ) throws IOException {

        HttpServerProvider provider = HttpServerProvider.provider();
        HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(port), backlog);
        //注册监听地址

        getHandler(encode, new String[]{"GET"}).forEach(httpserver::createContext);

        //启动服务
        httpserver.start();

        return httpserver;
    }

    /**
     * 获取所需的handler
     */
    private static Map<String, HttpHandler> getHandler(String encoding, String[] methods){
        //创建一个工厂
        ServerContextFactory serverContextFactory = new ServerContextFactoryImpl();
        Map<String, HttpHandler> map = new LinkedHashMap<>(1);

        //首先是首页
        map.put("/", serverContextFactory.createHttpHandler(encoding, methods, (s, ex) -> {
            // 设置响应头
            ex.getResponseHeaders().add("Content-Type", "text/html;charset=" + encoding);

            // 设置响应code和内容长度
            try {
                ex.sendResponseHeaders(200, 0L);
            } catch (IOException e) {
                try {
                    ex.sendResponseHeaders(500, 0L);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            }

           //返回需要响应的信息
            InputStream resourceAsStream = RobotLocalServer.class.getClassLoader().getResourceAsStream("server/index.html");

            try {
                return resourceAsStream == null ? "获取失败" : IOUtils.toString(resourceAsStream, encoding);
            } catch (IOException e) {
                return "错误：" + e.getMessage();
            }

        }));

        map.put("/getDatas", serverContextFactory.createHttpHandler(encoding, methods, (s, ex) -> {
            // 设置响应头
            ex.getResponseHeaders().add("Content-Type", "text/html;charset=" + encoding);
                // 设置响应code和内容长度
                try {
                    ex.sendResponseHeaders(200, 0L);
                } catch (IOException e) {
                    try {
                        ex.sendResponseHeaders(500, 0L);
                    } catch (IOException e1) {
                        throw new RuntimeException(e1);
                    }
                }

            //TODO 暂且使用假数据

            SenderAPIStatistics senderAPIStatistics = new SenderAPIStatistics();

                String[] datas = new String[]{
                        senderAPIStatistics.toJson(),
                        senderAPIStatistics.toJson(),
                        senderAPIStatistics.toJson()
                };

            return Arrays.toString(datas);
        }));




        return map;
    }


}
