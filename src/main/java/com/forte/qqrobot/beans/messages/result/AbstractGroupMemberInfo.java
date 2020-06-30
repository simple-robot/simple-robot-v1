/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupMemberInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * @see GroupMemberInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupMemberInfo extends AbstractInfoResult implements GroupMemberInfo {
    private String code;
    private String qq;
    private String nickname;
    private String remark;
    private SexType sex;
    private String city;
    private Long joinTime;
    private Long lastTime;
    private PowerType powerType;
    private String exTitle;
    private String levelName;
    private Boolean black;
    private Boolean allowChangeNick;
    private Long exTitleTime;
    private String headImgUrl;
    private Long banTime;

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

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQq() {
        return qq;
    }

    @Override
    public String getQQ(){
        return qq;
    }

    public void setQq(String qq) {
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
    public PowerType getPowerType() {
        return powerType;
    }

    public void setPowerType(PowerType powerType) {
        this.powerType = powerType;
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

    public Boolean getBlack() {
        return black;
    }

    @Override
    public Boolean isBlack() {
        return black;
    }

    public void setBlack(Boolean black) {
        this.black = black;
    }

    public Boolean getAllowChangeNick() {
        return allowChangeNick;
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
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    @Override
    public Long getBanTime() {
        return banTime;
    }

    public void setBanTime(Long banTime) {
        this.banTime = banTime;
    }

    @Override
    public String toString() {
        return "GroupMemberInfo{" +
                "code='" + getCode() + '\'' +
                ", qq='" + getQQ() + '\'' +
                ", nickname='" + getNickname() + '\'' +
                ", remark='" + getRemark() + '\'' +
                ", card='" + getCard() + '\'' +
                ", sex=" + getSex() +
                ", city='" + getCity() + '\'' +
                ", joinTime=" + getJoinTime() +
                ", lastTime=" + getLastTime() +
                ", powerType=" + getPowerType() +
                ", exTitle='" + getExTitle() + '\'' +
                ", levelName='" + getLevelName() + '\'' +
                ", black=" + getBlack() +
                ", allowChangeNick=" + getAllowChangeNick() +
                ", exTitleTime=" + getExTitleTime() +
                ", headImgUrl='" + getHeadImgUrl() + '\'' +
                ", banTime=" + getBanTime() +
                "} " + super.toString();
    }
}
