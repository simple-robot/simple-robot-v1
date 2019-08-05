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

    @Override
    public String toString() {
        return "GroupMsg{" +
                "qq='" + getQQ() + '\'' +
                ", group='" + getGroup() + '\'' +
                ", type=" + getType() +
                "} " + super.toString();
    }
}
