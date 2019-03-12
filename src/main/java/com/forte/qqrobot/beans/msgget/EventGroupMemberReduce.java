package com.forte.qqrobot.beans.msgget;

/**
 * 群成员减少事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:01
 * @since JDK1.8
 **/
public class EventGroupMemberReduce implements EventGet {

    /*
    102 群事件-群成员减少
subType（子类型，1/群员离开 2/群员被踢 3/自己(即登录号)被踢），
sendTime，fromGroup，fromQQ（操作者QQ-仅subType为2、3时存在），beingOperateQQ
     */

    /** 事件编码 */
    private Integer act;
    /** （子类型，1/群员离开 2/群员被踢 3/自己(即登录号)被踢） */
    private Integer subType;
    /** 发生时间 */
    private Long sendTime;
    /** 事件群 */
    private String fromGroup;
    /** （操作者QQ-仅subType为2、3时存在） */
    private String fromQQ;
    /** 当事人qq */
    private String beingOperateQQ;
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

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
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
