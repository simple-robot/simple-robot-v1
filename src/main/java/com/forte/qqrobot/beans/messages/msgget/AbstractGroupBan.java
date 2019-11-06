package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.GroupBanType;

/**
 *
 * 群禁言事件对应抽象类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AbstractGroupBan extends AbstractEventGet implements GroupBan {

    private GroupBanType banType;
    private String group;
    private String operatorQQ;
    private String beOperatedQQ;
    private Long time;

    @Override
    public GroupBanType getBanType() {
        return banType;
    }

    public void setBanType(GroupBanType banType) {
        this.banType = banType;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getOperatorQQ() {
        return operatorQQ;
    }

    public void setOperatorQQ(String operatorQQ) {
        this.operatorQQ = operatorQQ;
    }

    @Override
    public String getBeOperatedQQ() {
        return beOperatedQQ;
    }

    @Override
    public Long time() {
        return time;
    }

    public void setBeOperatedQQ(String beOperatedQQ) {
        this.beOperatedQQ = beOperatedQQ;
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public void setTime(Long time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "GroupBan{" +
                "banType=" + banType +
                ", group='" + group + '\'' +
                ", operatorQQ='" + operatorQQ + '\'' +
                ", beOperatedQQ='" + beOperatedQQ + '\'' +
                ", time=" + time +
                "} " + super.toString();
    }
}
