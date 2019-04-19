package com.forte.forlemoc.beans.msgsend;

/**
 * 106, 发送私聊消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:34
 * @since JDK1.8
 **/
public class SendMsgPrivate implements MsgSend {
    /*
    106, 发送私聊消息
    QQID,msg
     */
    /** 私聊送信编码 */
    private static final Integer act = 106;
    /** qq号 */
    private String QQID;
    /** 消息内容 */
    private String msg;


    public Integer getAct() {
        return act;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
