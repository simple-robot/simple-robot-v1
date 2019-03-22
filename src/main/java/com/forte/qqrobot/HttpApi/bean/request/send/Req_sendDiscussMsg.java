package com.forte.qqrobot.HttpApi.bean.request.send;

/**
 * 「发送讨论组消息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:27
 * @since JDK1.8
 **/
public class Req_sendDiscussMsg implements ReqSendBean {

    private final String fun = "sendDiscussMsg";

    /** 讨论组号 */
    private String group;
    /** 消息内容 */
    private String msg;

    @Override
    public String getFun() {
        return fun;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
