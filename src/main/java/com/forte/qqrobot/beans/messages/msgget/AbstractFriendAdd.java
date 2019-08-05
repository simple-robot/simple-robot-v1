package com.forte.qqrobot.beans.messages.msgget;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractFriendAdd extends AbstractEventGet implements FriendAdd {

    private String qq;

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
    public String toString() {
        return "FriendAdd{" +
                "qq='" + getQQ() + '\'' +
                "} " + super.toString();
    }
}
