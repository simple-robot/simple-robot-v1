package com.forte.qqrobot.beans.msgget;

/**
 * 群事件-管理员变动
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:55
 * @since JDK1.8
 **/
public class EventGroupAdmin {
    /*
    101 群事件-管理员变动
    subType（1/被取消管理员 2/被设置管理员），sendTime，fromGroup，beingOperateQQ
     */

    /** 事件编码 */
    private Integer act;
    /** 子类型 */
    private Integer subType;
    /** 送信时间 */
    private Long sendTime;
    /** 事件群 */
    private String fromGroup;
    /** 发生变动的用户qq号 */
    private String beingOperateQQ;
    /** 错误码 */
    private Integer error;

    /* —————————— getter & settter ———————— */

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

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getBeingOperateQQ() {
        return beingOperateQQ;
    }

    public void setBeingOperateQQ(String beingOperateQQ) {
        this.beingOperateQQ = beingOperateQQ;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
