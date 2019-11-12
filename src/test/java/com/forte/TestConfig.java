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

    @Conf("setverIp")
    private String setverIp;

    @Conf("serverPort")
    private Integer serverPort;

    public String getSetverIp() {
        return setverIp;
    }

    public void setSetverIp(String setverIp) {
        this.setverIp = setverIp;
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
                "setverIp='" + setverIp + '\'' +
                ", serverPort=" + serverPort +
                "} " + super.toString();
    }
}
