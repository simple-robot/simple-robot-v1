package com.forte.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.GroupAdminChange;
import com.forte.qqrobot.beans.messages.types.GroupAdminChangeType;

/**
 * 群事件-管理员变动
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:55
 * @since JDK1.8
 **/
public class EventGroupAdmin implements GroupAdminChange {
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

    /* —————————— 统一接口 —————————— */

    /**
     * 来自的群
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
        return beingOperateQQ;
    }

    /**
     * 被操作者的QQ号
     */
    @Override
    public String getBeOperatedQQ() {
        return null;
    }

    /**
     * 获取管理员变动类型
     */
    @Override
    public GroupAdminChangeType getType() {
        return subType == 1 ? GroupAdminChangeType.CANCEL_ADMIN : GroupAdminChangeType.BECOME_ADMIN;
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
