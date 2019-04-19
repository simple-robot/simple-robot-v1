package com.forte.forlemoc.beans.msgsend;

/**
 * 126, 置群成员名片
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:53
 * @since JDK1.8
 **/
public class SetGroupMemberCard implements MsgSend {
    /*
        126, 置群成员名片
    groupid，QQID，newcard
     */
    /** 设置群成员名片 */
    private static final Integer act = 126;
    /** 群号 */
    private String groupid;
    /** qq号 */
    private String QQID;
    /** 新名片 */
    private String newcard;



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

    public String getNewcard() {
        return newcard;
    }

    public void setNewcard(String newcard) {
        this.newcard = newcard;
    }
}
