package com.forte.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.beans.messages.types.IncreaseType;

/**
 * 群事件-群成员增加
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:06
 * @since JDK1.8
 **/
public class EventGroupMemberJoin implements GroupMemberIncrease, EventGet {
    /*
    103 群事件-群成员增加
subType（子类型，1/管理员已同意 2/管理员邀请）
，sendTime，fromGroup，fromQQ（操作者QQ-即管理员QQ)，beingOperateQQ
     */

    /** 事件编码 */
    private Integer act;
    /** 子类型 */
    private Integer subType;
    /** 发生时间 */
    private Long sendTime;
    /** 事件群 */
    private String fromGroup;
    /** （操作者QQ-即管理员QQ) */
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

    /* —————————— 统一接口 ———————— */

    /**
     * 获取类型
     */
    @Override
    public IncreaseType getType() {
        return subType == 1 ? IncreaseType.AGREE : IncreaseType.INVITE;
    }

    /**
     * 群号
     */
    @Override
    public String getGroup() {
        return fromGroup;
    }

    /**
     * 操作者的QQ号
     */
    @Override
    public String getOperatorQQ() {
        return fromQQ;
    }

    /**
     * 被操作者的QQ号
     */
    @Override
    public String getBeOperatedQQ() {
        return beingOperateQQ;
    }

    /**
     * 获取ID，如果没有此参数推荐使用UUID等来代替
     */
    @Override
    public String getId() {
        return act+"";
    }

    /**
     * 获取到的时间, 代表某一时间的秒值。注意是秒值！如果类型不对请自行转化
     */
    @Override
    public long getTime() {
        return sendTime;
    }

    /**
     * 如果还会出现其他的参数，请自行实现获取方式<br>
     * 如果其他参数不多，你可以试着在方法中使用if_else进行分别；<br>
     * 如果参数较多，我建议你使用Map等键值对来储存参数并获取
     *
     * @param key
     */
    @Override
    public Object getOtherParam(String key) {
        return key.equals("error") ? error : null;
    }
}
