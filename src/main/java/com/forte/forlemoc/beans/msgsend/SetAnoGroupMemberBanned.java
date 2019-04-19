package com.forte.forlemoc.beans.msgsend;

/**
 * 124, 置匿名群员禁言
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:50
 * @since JDK1.8
 **/
public class SetAnoGroupMemberBanned implements MsgSend {
    /*
    124, 置匿名群员禁言
    groupid，duration，anomymous
     */
    /** 设置匿名群员禁言编码 */
    private static final Integer act = 124;
    /** 群id */
    private String groupid;
    /** 禁言时间，秒为单位 */
    private Long duration;
    /** 匿名名称 */
    private String anomymous;


    public Integer getAct() {
        return act;
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

    public String getAnomymous() {
        return anomymous;
    }

    public void setAnomymous(String anomymous) {
        this.anomymous = anomymous;
    }
}
