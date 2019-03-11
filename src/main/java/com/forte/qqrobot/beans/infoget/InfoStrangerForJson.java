package com.forte.qqrobot.beans.infoget;

/**
 * 25304,以json串方式返回陌生人信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:30
 * @since JDK1.8
 **/
public class InfoStrangerForJson implements InfoGet {
    /*
    25304,以json串方式返回陌生人信息
    QQID,nocache
    返回json串字段：
    error, act, return,QQID,nick,sex,age  (各字段具体含义详见本帖19楼说明)
     */
    /** 编号 */
    private static final Integer act = 25304;
    /** qq号 */
    private String QQID;
    /** 大概是是否缓存？ */
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
