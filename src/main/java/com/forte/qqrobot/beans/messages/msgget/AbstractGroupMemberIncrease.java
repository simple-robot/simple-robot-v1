/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupMemberIncrease.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.IncreaseType;

/**
 * GroupMemberIncrease 对应抽象类
 * @see GroupMemberIncrease
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupMemberIncrease extends AbstractEventGet implements GroupMemberIncrease {

    private String group;

    private IncreaseType type;

    private String operatorQQ;

    private String beOperatedQQ;

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public IncreaseType getType() {
        return type;
    }

    public void setType(IncreaseType type) {
        this.type = type;
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
    public String toString() {
        return "GroupMemberIncrease{" +
                "group='" + getGroup() + '\'' +
                ", type=" + getType() +
                ", operatorQQ='" + getOperatorQQ() + '\'' +
                ", beOperatedQQ='" + getBeOperatedQQ() + '\'' +
                "} " + super.toString();
    }
}
