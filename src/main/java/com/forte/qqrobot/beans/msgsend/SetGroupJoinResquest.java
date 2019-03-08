package com.forte.qqrobot.beans.msgsend;

/**
 * 151, 置群添加请求
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:24
 * @since JDK1.8
 **/
public class SetGroupJoinResquest {
    /*
    151, 置群添加请求
    requesttype，responseoperation，reason，responseflag
    */
    /** 编号 */
    private final Integer act = 151;

    private String requesttype;
    private String responseoperation;
    private String reason;
    private String responseflag;



    public Integer getAct() {
        return act;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public String getResponseoperation() {
        return responseoperation;
    }

    public void setResponseoperation(String responseoperation) {
        this.responseoperation = responseoperation;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponseflag() {
        return responseflag;
    }

    public void setResponseflag(String responseflag) {
        this.responseflag = responseflag;
    }
}
