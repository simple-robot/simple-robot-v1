package com.forte.forlemoc.beans.msgsend;

/**
 * 120, 置群员移除
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:38
 * @since JDK1.8
 **/
public class SetGroupMemberRemove implements MsgSend {
    /*
    120, 置群员移除
    QQID，groupid，rejectaddrequest
     */

    /** 群踢人编码 */
    private static final Integer act = 120;
    /** 移除qq号 */
    private String QQID;
    /** 群号 */
    private String groupid;
    /** 是否拒绝添加请求 */
    private Boolean rejectaddrequest;


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

    public Boolean getRejectaddrequest() {
        return rejectaddrequest;
    }

    public void setRejectaddrequest(Boolean rejectaddrequest) {
        this.rejectaddrequest = rejectaddrequest;
    }
}
