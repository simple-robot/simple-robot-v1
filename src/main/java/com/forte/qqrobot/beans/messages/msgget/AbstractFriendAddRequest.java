package com.forte.qqrobot.beans.messages.msgget;

/**
 * FriendAddRequest对应抽象类
 * @see FriendAddRequest
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractFriendAddRequest extends AbstractEventGet implements FriendAddRequest {


    private String qq;

    private String msg;

    private String flag;

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
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FriendAddRequest{" +
                "qq='" + getQQ() + '\'' +
                ", msg='" + getMsg() + '\'' +
                ", flag='" + getFlag() + '\'' +
                "} " + super.toString();
    }
}
