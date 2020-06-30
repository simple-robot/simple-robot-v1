/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupMsg.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.GroupMsgType;

/**
 * GroupMsg 对应抽象类
 * @see GroupMsg
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupMsg extends AbstractMsgGet implements GroupMsg {

    private String qq;

    private String group;

    private GroupMsgType type;

    private String nick;
    private String remark;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String getQQ() {
        return qq;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public GroupMsgType getType() {
        return type;
    }

    public void setType(GroupMsgType type) {
        this.type = type;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "GroupMsg{" +
                "qq='" + qq + '\'' +
                ", group='" + group + '\'' +
                ", type=" + type +
                ", nick='" + nick + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}
