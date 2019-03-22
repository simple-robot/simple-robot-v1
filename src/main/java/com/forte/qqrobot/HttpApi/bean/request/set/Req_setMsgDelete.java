package com.forte.qqrobot.HttpApi.bean.request.set;

/**
 * 「撤回消息」
 * 需要权限180
 * 需要使用 酷Q Pro
 * 可以撤回群成员消息，但是需要群管理权限
 * 可以撤回自己发送消息，但只能撤回两分钟内发送的内容
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:08
 * @since JDK1.8
 **/
public class Req_setMsgDelete implements ReqBanBean {
    private final String fun = "setMsgDelete";

    /** 消息ID，值为提交事件中提交的参数msgID */
    private Integer msgID;

    @Override
    public String getFun() {
        return fun;
    }

    public Integer getMsgID() {
        return msgID;
    }

    public void setMsgID(Integer msgID) {
        this.msgID = msgID;
    }
}
