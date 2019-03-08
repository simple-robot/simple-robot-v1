package com.forte.qqrobot.beans.msgget;

/**
 * 301 请求-好友添加
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:17
 * @since JDK1.8
 **/
public class RequestFriend {
    /*
    301 请求-好友添加
    subType，sendTime，fromQQ，msg（附言），responseFlag（反馈标识-处理请求用）
     */

    /** 请求编码 */
    private Integer act;
    /** 子类型 */
    private Integer subType;
    /** 送信时间 */
    private Long snedTime;
    /** 来自QQ */
    private String fromQQ;
    /** 附言 */
    private String msg;
    /** 反馈标识-处理请求用 */
    private String responseFlag;
    /** 错误码 */
    private Integer error;

    /* ———————— getter & setter ———————— */

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Long getSnedTime() {
        return snedTime;
    }

    public void setSnedTime(Long snedTime) {
        this.snedTime = snedTime;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseFlag() {
        return responseFlag;
    }

    public void setResponseFlag(String responseFlag) {
        this.responseFlag = responseFlag;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
