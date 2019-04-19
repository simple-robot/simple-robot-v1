package com.forte.forlemoc.beans.msgsend;

/**
 * 125, 置群匿名设置
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:52
 * @since JDK1.8
 **/
public class SetGroupAno implements MsgSend {
    /*
    125, 置群匿名设置
    groupid，enableanomymous
     */
    /** 设置群匿名编码 */
    private static final Integer act = 125;
    /** 群号 */
    private String groupid;
    /** 是否允许群匿名 */
    private Boolean enableanomymous;


    public Integer getAct() {
        return act;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Boolean getEnableanomymous() {
        return enableanomymous;
    }

    public void setEnableanomymous(Boolean enableanomymous) {
        this.enableanomymous = enableanomymous;
    }
}
