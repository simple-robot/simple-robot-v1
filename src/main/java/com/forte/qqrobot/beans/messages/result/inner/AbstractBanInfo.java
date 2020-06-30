/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractBanInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;

/**
 * BanInfo对应的抽象类
 * @see BanInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractBanInfo extends AbstractResultInner implements BanInfo {

    private String qq;

    private String nickName;

    private Boolean manager;

    private Long lastTime;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getManager() {
        return manager;
    }

    @Override
    public Boolean isManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "BanInfo{" +
                "qq='" + qq + '\'' +
                ", nickName='" + getNickName() + '\'' +
                ", isManager=" + isManager() +
                ", lastTime=" + getLastTime() +
                "} " + super.toString();
    }
}
