package com.forte.qqrobot.beans.msgsend;

/**
 * 103, 发送讨论组消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:32
 * @since JDK1.8
 **/
public class SendDisGroupMsg implements MsgSend {
    /*
    103, 发送讨论组消息
    discussid,msg
     */
    private static final Integer act = 103;
    /** 讨论组id */
    private String discussid;
    /** 消息 */
    private String msg;



    public Integer getAct() {
        return act;
    }

    public String getDiscussid() {
        return discussid;
    }

    public void setDiscussid(String discussid) {
        this.discussid = discussid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
