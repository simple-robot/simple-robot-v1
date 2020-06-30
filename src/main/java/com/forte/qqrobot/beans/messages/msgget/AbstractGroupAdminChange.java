/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupAdminChange.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.GroupAdminChangeType;

/**
 * GroupAdminChange 对应抽象类
 * @see GroupAdminChange
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupAdminChange extends AbstractEventGet implements GroupAdminChange {

    private String group;

    private String qq;

    private String operatorQQ;

    private String beOperatedQQ;

    private GroupAdminChangeType type;

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
    public String getOperatorQQ() {
        return operatorQQ;
    }

    public void setOperatorQQ(String operatorQQ) {
        this.operatorQQ = operatorQQ;
    }

    @Override
    public String getBeOperatedQQ() {
        return beOperatedQQ;
    }

    public void setBeOperatedQQ(String beOperatedQQ) {
        this.beOperatedQQ = beOperatedQQ;
    }

    @Override
    public GroupAdminChangeType getType() {
        return type;
    }

    public void setType(GroupAdminChangeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GroupAdminChange{" +
                "group='" + getGroup() + '\'' +
                ", qq='" + getQQ() + '\'' +
                ", operatorQQ='" + getOperatorQQ() + '\'' +
                ", beOperatedQQ='" + getBeOperatedQQ() + '\'' +
                ", type=" + getType() +
                "} " + super.toString();
    }
}
