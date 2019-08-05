package com.forte.qqrobot.beans.messages.msgget;

import java.util.StringJoiner;

/**
 * DiscussMsg对应的抽象类
 * @see DiscussMsg
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractDiscussMsg extends AbstractMsgGet implements DiscussMsg {

    private String group;

    private String qq;

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getQQ() {
        return qq;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }


    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "DiscussMsg{" +
                "group='" + getGroup() + '\'' +
                ", qq='" + getQQ() + '\'' +
                "} " + super.toString();
    }
}
