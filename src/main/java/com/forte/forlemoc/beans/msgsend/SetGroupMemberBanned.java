package com.forte.forlemoc.beans.msgsend;

/**
 * 121, 置群员禁言
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:41
 * @since JDK1.8
 **/
public class SetGroupMemberBanned implements MsgSend {

    /*
    121, 置群员禁言
    QQID，groupid，duration
     */
    /** 禁言编码 */
    private static final Integer act = 121;
    /** qq号 */
    private String QQID;
    /** 群号 */
    private String groupid;
    /** 禁言时间，单位为秒 */
    private Long duration;



    public Integer getAct() {
        return act;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
