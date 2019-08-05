package com.forte.qqrobot.server.path;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ServerContextFactoryImpl implements ServerContextFactory {
    /**
     * 构建监听地址
     *
     * @param encoding 编码
     * @param methods  请求方式
     */
    @Override
    public HttpHandler createHttpHandler(String encoding, String[] methods, BiFunction<String, HttpExchange, String> doHandler) {

        //构建
        return ex -> {
            //使用线程异步接收消息
            ResourceDispatchCenter.getThreadPool().execute(() -> doHandle(ex, encoding, methods, doHandler));
        };
    }

    //真正的处理方法
    private void doHandle(HttpExchange httpExchange, String encoding, String[] methods, BiFunction<String, HttpExchange, String> doHandler){
        try{
            //获得表单提交数据
            //判断请求方式
            String method = httpExchange.getRequestMethod();

            //判断请求方式
            if(Arrays.stream(methods).anyMatch(m -> m.equalsIgnoreCase(method))){
                //获取接收到的参数
                InputStream requestBody = httpExchange.getRequestBody();
                //编码转义
                String paramsUrl = IOUtils.toString(requestBody, encoding);
                String params = URLDecoder.decode(paramsUrl, encoding);

                //执行自定义处理方法
                String body = doHandler.apply(params, httpExchange);

                // 设置响应头
                //httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=" + ENCODING);

//                // 设置响应code和内容长度
//                httpExchange.sendResponseHeaders(200, 0L);

                //获取首页

                //获取响应输出流
                OutputStream out = httpExchange.getResponseBody();
                // 响应信息
                IOUtils.write(body, out, encoding);
                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
//                httpExchange.close();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
            httpExchange.close();
        }
    }

}
