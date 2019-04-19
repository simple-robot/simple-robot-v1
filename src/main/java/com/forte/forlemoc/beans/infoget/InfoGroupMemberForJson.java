package com.forte.forlemoc.beans.infoget;

/**
 * 25303,以json串方式返回群成员信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:28
 * @since JDK1.8
 **/
public class InfoGroupMemberForJson implements InfoGet {
    /*
    25303,以json串方式返回群成员信息
    groupid,QQID,nocache
    返回json串字段：
    error, act, return, groupid, QQID, username,
    nick, sex, age, area, jointime, lastsent,
    level_name, permission, unfriendly,
    title, titleExpiretime, nickcanchange (各字段具体含义详见本帖19楼说明)
     */

    /** 编号 */
    private static final Integer act = 25303;
    /** 群号 */
    private String groupid;
    /** qq号 */
    private String QQID;
    /** 大概是是否缓存？ */
    private String nocache;




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

    public String getNocache() {
        return nocache;
    }

    public void setNocache(String nocache) {
        this.nocache = nocache;
    }
}
