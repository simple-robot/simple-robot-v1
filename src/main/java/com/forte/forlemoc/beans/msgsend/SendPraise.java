package com.forte.forlemoc.beans.msgsend;

/**
 * 110, 发送赞
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:36
 * @since JDK1.8
 **/
public class SendPraise implements MsgSend {

    /*
    110, 发送赞
    QQID
     */
    /** 发送赞编码 */
    private static final Integer act = 110;
    /** qq号 */
    private String QQID;


    public Integer getAct() {
        return act;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }
}
