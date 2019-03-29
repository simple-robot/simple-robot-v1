package com.forte.qqrobot.beans.HttpApi.request.send;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:33
 * @since JDK1.8
 **/
public class Req_sendPrivateMsg implements ReqSendBean {

    private final String fun = "sendPrivateMsg";

    /** QQ */
    private String qq;
    /** 消息内容 */
    private String msg;

    @Override
    public String getFun() {
        return fun;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
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
}
