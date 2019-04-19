package com.forte.forlemoc.beans.infoget;

/**
 * 130, 取群成员信息（注，info返回的是base64字节流，需要经过处理之后获取信息，如果需要以json串方式获取群成员信息请见25303）
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 12:03
 * @since JDK1.8
 **/
public class InfoGroupMember implements InfoGet {
    /*
    130, 取群成员信息（注，info返回的是base64字节流，需要经过处理之后获取信息，如果需要以json串方式获取群成员信息请见25303）
    groupid，QQID，nocache
    返回json串字段：
    error, act, return,info
     */

    private static final Integer act = 130;
    private String groupid;
    private String QQID;
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
