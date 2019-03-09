package com.forte.qqrobot.beans.msgsend;

/**
 * 123, 置全群禁言
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:46
 * @since JDK1.8
 **/
public class SetAllGroupBanned {
    /*
    123, 置全群禁言
    QQID，groupid，enableban
     */
    /** 设置全群禁言 */
    private static final Integer act = 123;
    /** qq号-不知道有什么用 */
    private String QQID;
    /** qun号 */
    private String groupid;
    /** 是否为全群禁言 */
    private String enableban;



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

    public String getEnableban() {
        return enableban;
    }

    public void setEnableban(String enableban) {
        this.enableban = enableban;
    }
}
