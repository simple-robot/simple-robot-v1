package com.forte.qqrobot.beans.infoget;

/**
 * 131, 取陌生人信息（注，info返回的是base64字节流，需要经过处理之后获取信息，如果需要以json串方式获取群成员信息请见25304）
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 12:06
 * @since JDK1.8
 **/
public class InfoStranger implements InfoGet {
    /*
    131, 取陌生人信息（注，info返回的是base64字节流，需要经过处理之后获取信息，如果需要以json串方式获取群成员信息请见25304）
    QQID，nocache
    返回json串字段：
    error, act, return,info
     */
    private static final Integer act = 131;
    private String QQID;
    private String nocache;



    public Integer getAct() {
        return act;
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
