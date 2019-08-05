package com.forte.qqrobot.server.path;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.function.BiFunction;

/**
 * 服务器监听路径构建工厂
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ServerContextFactory {

    /**
     * 构建监听地址
     * @param encoding  编码
     * @param methods   请求方式
     * @param doHandler 请求响应器
     */
    HttpHandler createHttpHandler(String encoding, String[] methods, BiFunction<String, HttpExchange, String> doHandler);

}
