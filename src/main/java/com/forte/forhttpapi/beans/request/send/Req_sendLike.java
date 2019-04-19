package com.forte.forhttpapi.beans.request.send;

/**
 * 「发送名片赞」
 * 需要权限110
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:32
 * @since JDK1.8
 **/
public class Req_sendLike implements ReqSendBean {

    private final String fun = "sendLike";

    /** QQ */
    private String qq;
    /** 次数 */
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
