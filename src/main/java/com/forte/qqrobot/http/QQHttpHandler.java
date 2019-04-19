package com.forte.qqrobot.http;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.function.Function;

/**
 * qq请求处理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/4 18:11
 * @since JDK1.8
 **/
public class QQHttpHandler implements HttpHandler {

    private final String ENCODING;

    /** 接收到消息JSON字符串后的消息处理类 */
    private final Function<String, Resp> JSON_PARAM_CONSUMER;

    /** 可以接受的请求方式 */
    private final String[] METHODS;

    public QQHttpHandler(String encode, Function<String, Resp> msgConsumer, String[] methods){
        this.ENCODING = encode;
        this.JSON_PARAM_CONSUMER = msgConsumer;
        this.METHODS = methods;
    }

    /**
     * 消息接收器
     */
    @Override
    public void handle(HttpExchange httpExchange) {
        //使用线程异步接收消息
        ResourceDispatchCenter.getThreadPool().execute(() -> doHandle(httpExchange));
    }

    /**
     * 接收到消息的逻辑
     */
    private void doHandle(HttpExchange httpExchange){
        try{
            //获得表单提交数据
            //判断请求方式
            String method = httpExchange.getRequestMethod();

            //判断请求方式
            if(Arrays.stream(this.METHODS).anyMatch(m -> m.toLowerCase().equals(method.toLowerCase()))){
                //获取接收到的参数
                InputStream requestBody = httpExchange.getRequestBody();
                //编码转义
                String paramsUrl = IOUtils.toString(requestBody, ENCODING);
                String params = URLDecoder.decode(paramsUrl, ENCODING);


                //将获取到的请求参数放入, 获得响应消息
                Resp apply = JSON_PARAM_CONSUMER.apply(params);
                int headerLeft = apply.getHeaderLeft();
                long headerRight = apply.getHeaderRight();
                String body = apply.getBody();

                // 设置响应头
                //httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=" + ENCODING);

                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(headerLeft, headerRight);

                //获取响应输出流
                OutputStream out = httpExchange.getResponseBody();
                // 响应信息
                IOUtils.write(body, out, ENCODING);

                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
//                httpExchange.close();
            }
        }catch (Exception e){
            try {
                e.printStackTrace();
                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(500, 0);
                OutputStream out = httpExchange.getResponseBody();
                // 响应信息
                IOUtils.write("error", out, ENCODING);
                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
//                httpExchange.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally{
            // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
            httpExchange.close();
        }
    }

}
