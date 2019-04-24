package com.forte.forhttpapi;

import com.forte.qqrobot.BaseConfiguration;

/**
 * HTTP API连接使用的配置类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/4 18:17
 * @since JDK1.8
 **/
public class HttpConfiguration extends BaseConfiguration {


    /**
     * 服务器请求地址，默认为/coolq
     */
    private String serverPath = "/coolq";

    /**
     * 服务器端口地址，默认为15514
     */
    private int port = 15514;

    /**
     * 接收的请求方式，默认为 post
     */
    private String[] method = {"post"};


    /* —————————————— getter && setter ———————————— */

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String[] getMethod() {
        return method;
    }

    public void setMethod(String[] method) {
        this.method = method;
    }
}
