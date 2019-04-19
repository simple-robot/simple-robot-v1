package com.forte.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.FriendAddRequest;

/**
 * 301 请求-好友添加
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:17
 * @since JDK1.8
 **/
public class RequestFriend implements FriendAddRequest {
    /*
    301 请求-好友添加
    subType，sendTime，fromQQ，msg（附言），responseFlag（反馈标识-处理请求用）
     */

    /** 请求编码 */
    private Integer act;
    /** 子类型 */
    private Integer subType;
    /** 送信时间 */
    private Long sendTime;
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

    public Long getSendTime() {
        return sendTime;
    }

    public void setSnedTime(Long snedTime) {
        this.sendTime = snedTime;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    /**
     * 请求人QQ
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
     * 请求的时候应该有标识一类的东西
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
