package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * GroupMember 的抽象类
 * @see GroupMember
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupMember extends AbstractResultInner implements GroupMember {

    private String group;

    private String qq;

    private String nickname;

    private String remark;

    private SexType sex;

    private String city;

    private Long joinTime;

    private Long lastTime;

    private PowerType power;

    private String exTitle;

    private String levelName;

    private Boolean black;

    private Boolean allowChangeNick;

    private Long exTitleTime;

    private String headUrl;

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getBlack() {
        return black;
    }

    public Boolean getAllowChangeNick() {
        return allowChangeNick;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String getQQ() {
        return qq;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }

    @Override
    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public PowerType getPower() {
        return power;
    }

    public void setPower(PowerType power) {
        this.power = power;
    }

    @Override
    public String getExTitle() {
        return exTitle;
    }

    public void setExTitle(String exTitle) {
        this.exTitle = exTitle;
    }

    @Override
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public Boolean isBlack() {
        return black;
    }

    public void setBlack(Boolean black) {
        this.black = black;
    }

    @Override
    public Boolean isAllowChangeNick() {
        return allowChangeNick;
    }


    public void setAllowChangeNick(Boolean allowChangeNick) {
        this.allowChangeNick = allowChangeNick;
    }

    @Override
    public Long getExTitleTime() {
        return exTitleTime;
    }

    public void setExTitleTime(Long exTitleTime) {
        this.exTitleTime = exTitleTime;
    }

    @Override
    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "group='" + getGroup() + '\'' +
                ", qq='" + getQQ() + '\'' +
                ", nickname='" + getNickname() + '\'' +
                ", remark='" + getRemark() + '\'' +
                ", sex=" + getSex() +
                ", city='" + getCity() + '\'' +
                ", joinTime=" + getJoinTime() +
                ", lastTime=" + getLastTime() +
                ", power=" + getPower() +
                ", exTitle='" + getExTitle() + '\'' +
                ", levelName='" + getLevelName() + '\'' +
                ", black=" + isBlack() +
                ", allowChangeNick=" + isAllowChangeNick() +
                ", exTitleTime=" + getExTitleTime() +
                ", headUrl='" + getHeadUrl() + '\'' +
                "} " + super.toString();
    }
}
