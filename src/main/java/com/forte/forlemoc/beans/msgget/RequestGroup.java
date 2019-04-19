package com.forte.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.GroupAddRequest;

/**
 * 302 请求-群添加
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:21
 * @since JDK1.8
 **/
public class RequestGroup implements GroupAddRequest {

    /*
    302 请求-群添加
subType（子类型，1/他人申请入群 2/自己(即登录号)受邀入群），
sendTime，fromGroup，fromQQ，msg（附言），responseFlag（反馈标识-处理请求用）
     */
    /** 请求编码 */
    private Integer act;
    /** 子类型，1/他人申请入群 2/自己(即登录号)受邀入群 */
    private Integer subType;
    /** 申请时间 */
    private Long sendTime;
    /** 申请群组 */
    private String fromGroup;
    /** 申请qq */
    private String fromQQ;
    /** 附言 */
    private String msg;
    /** 反馈标识-处理请求用 */
    private String responseFlag;
    /** 错误码 */
    private Integer error;

    /* ———————— setter & getter ———————— */

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

    /**
     * 获取群号
     */
    @Override
    public String getGroup() {
        return fromGroup;
    }

    /**
     * 获取QQ号
     */
    @Override
    public String getQQ() {
        return fromQQ;
    }

    /**
     * 获取ID，如果没有此参数推荐使用UUID等来代替
     */
    @Override
    public String getId() {
        return act+"";
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 请求类消息的标识
     */
    @Override
    public String getFlag() {
        return responseFlag;
    }

    /**
     * 获取到的时间, 代表某一时间的秒值。注意是秒值！如果类型不对请自行转化
     */
    @Override
    public long getTime() {
        return sendTime;
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
