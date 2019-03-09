package com.forte.qqrobot.beans.msgsend;

/**
 * 128, 置群成员专属头衔
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 12:00
 * @since JDK1.8
 **/
public class SetGroupMemberSpecialTitle {
    /*
    128, 置群成员专属头衔
    groupid，QQID，duration，newspecialtitle
     */

    /** 编号 */
    private static final Integer act = 128;
    /** qq号 */
    private String QQID;
    /** 群号 */
    private String groupid;
    /** 设置时间，单位大概是秒 */
    private Long duration;
    /** 专属头衔 */
    private String newspecialtitle;


    public Integer getAct() {
        return act;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getNewspecialtitle() {
        return newspecialtitle;
    }

    public void setNewspecialtitle(String newspecialtitle) {
        this.newspecialtitle = newspecialtitle;
    }
}

