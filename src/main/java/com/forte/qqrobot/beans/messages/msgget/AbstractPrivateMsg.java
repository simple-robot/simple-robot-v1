package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.PrivateMsgType;

/**
 * PrivateMsg接口的抽象类
 * @see PrivateMsg
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AbstractPrivateMsg extends AbstractMsgGet implements PrivateMsg {

    private String qq;

    private PrivateMsgType type;


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
    public PrivateMsgType getType() {
        return type;
    }

    public void setType(PrivateMsgType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PrivateMsg{" +
                "qq='" + getQQ() + '\'' +
                ", type=" + getType() +
                "} " + super.toString();
    }
}
