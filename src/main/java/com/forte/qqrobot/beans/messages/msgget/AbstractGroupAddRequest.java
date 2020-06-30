/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupAddRequest.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;

/**
 * GroupAddRequest 对应抽象类
 * @see GroupAddRequest
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupAddRequest extends AbstractEventGet implements GroupAddRequest {

    private String group;

    private String qq;

    private String msg;

    private String flag;

    private GroupAddRequestType requestType;

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQQ() {
        return qq;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public GroupAddRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(GroupAddRequestType groupRequestType) {
        this.requestType = groupRequestType;
    }

    @Override
    public String toString() {
        return "GroupAddRequest{" +
                "group='" + getGroup() + '\'' +
                ", qq='" + getQQ() + '\'' +
                ", msg='" + getMsg() + '\'' +
                ", flag='" + getFlag() + '\'' +
                ", requestType=" + getRequestType() +
                "} " + super.toString();
    }
}
