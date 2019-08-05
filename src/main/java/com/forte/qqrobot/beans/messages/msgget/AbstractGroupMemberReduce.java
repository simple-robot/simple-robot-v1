package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.ReduceType;

/**
 * GroupMemberReduce对应抽象类
 * @see GroupMemberReduce
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupMemberReduce extends AbstractEventGet implements GroupMemberReduce {

    private String group;

    private ReduceType type;

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
    public ReduceType getType() {
        return type;
    }

    public void setType(ReduceType type) {
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
        return "GroupMemberReduce{" +
                "group='" + getGroup() + '\'' +
                ", type=" + getType() +
                ", operatorQQ='" + getOperatorQQ() + '\'' +
                ", beOperatedQQ='" + getBeOperatedQQ() + '\'' +
                "} " + super.toString();
    }
}
