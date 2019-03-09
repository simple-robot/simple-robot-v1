package com.forte.qqrobot.beans.msgsend;

/**
 * 150, 置好友添加请求
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 12:09
 * @since JDK1.8
 **/
public class SetFriendAddRequest {
    /*
    150, 置好友添加请求
    responseoperation，remark，responseflag
     */

    private static final Integer act = 150;

    private String responseoperation;
    private String remark;
    private String responseflag;



    public Integer getAct() {
        return act;
    }

    public String getResponseoperation() {
        return responseoperation;
    }

    public void setResponseoperation(String responseoperation) {
        this.responseoperation = responseoperation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResponseflag() {
        return responseflag;
    }

    public void setResponseflag(String responseflag) {
        this.responseflag = responseflag;
    }
}
