package com.forte.qqrobot.beans.messages.msgget;

/**
 * DiscussMsg对应的抽象类
 * @see DiscussMsg
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractDiscussMsg extends AbstractMsgGet implements DiscussMsg {

    private String group;

    private String qq;

    private String nick;

    private String remark;

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
        return "DiscussMsg{" +
                "group='" + group + '\'' +
                ", qq='" + qq + '\'' +
                ", nick='" + nick + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}
