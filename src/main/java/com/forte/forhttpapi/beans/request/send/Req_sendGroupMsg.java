package com.forte.forhttpapi.beans.request.send;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:31
 * @since JDK1.8
 **/
public class Req_sendGroupMsg implements ReqSendBean {

    private final String fun = "sendGroupMsg";

    /** 群号 */
    private String group;
    /** 信息 */
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
