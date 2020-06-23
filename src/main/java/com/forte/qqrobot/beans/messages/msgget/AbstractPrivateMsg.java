package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.PrivateMsgType;

/**
 * PrivateMsg接口的抽象类
 * @see PrivateMsg
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractPrivateMsg extends AbstractMsgGet implements PrivateMsg {

    private String qq;
    private PrivateMsgType type;
    private String thisCode;
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
    public PrivateMsgType getType() {
        return type;
    }

    public void setType(PrivateMsgType type) {
        this.type = type;
    }

    /**
     * 此消息获取的时候，代表的是哪个账号获取到的消息。
     *
     * @return 接收到此消息的账号。
     */
    @Override
    public String getThisCode() {
        return thisCode;
    }

    /**
     * 可以获取昵称
     *
     * @return nickname
     */
    @Override
    public String getNickname() {
        return nick;
    }

    /**
     * 获取备注信息，例如群备注，或者好友备注。
     *
     * @return 备注信息
     */
    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setThisCode(String thisCode) {
        this.thisCode = thisCode;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PrivateMsg{" +
                "qq='" + qq + '\'' +
                ", type=" + type +
                ", thisCode='" + thisCode + '\'' +
                ", nick='" + nick + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}
