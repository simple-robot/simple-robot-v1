/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractLoginQQInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

/**
 * @see LoginQQInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractLoginQQInfo extends AbstractInfoResult implements LoginQQInfo {
    private String name;
    private String qq;
    private String headUrl;
    private Integer level;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    @Override
    public String getQQ(){
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LoginQQInfo{" +
                "name='" + getName() + '\'' +
                ", qq='" + getQQ() + '\'' +
                ", headUrl='" + getHeadUrl() + '\'' +
                ", level=" + getLevel() +
                "} " + super.toString();
    }
}
