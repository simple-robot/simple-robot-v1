package com.forte;

import com.forte.config.Conf;
import com.forte.qqrobot.BaseConfiguration;

/**
 *
 * 配置
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TestConfig extends BaseConfiguration<TestConfig> {

    @Conf("serverIp")
    private String serverIp;

    @Conf("serverPort")
    private Integer serverPort;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public String toString() {
        return "TestConfig{" +
                "serverIp='" + serverIp + '\'' +
                ", serverPort=" + serverPort +
                "} " + super.toString();
    }
}
