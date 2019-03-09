package com.forte.qqrobot.beans.msgsend;

/**
 * 101, 发送群消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:30
 * @since JDK1.8
 **/
public class SendGroupMsg {
    /*
    101, 发送群消息
    groupid,msg
     */
    /** 群消息编号 */
    private static final Integer act = 101;
    /** 群号 */
    private String groupid;
    /** 信息 */
    private String msg;



    public Integer getAct() {
        return act;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
