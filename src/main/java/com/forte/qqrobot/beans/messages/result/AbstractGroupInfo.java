/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import java.util.Arrays;
import java.util.Map;

/**
 * @see GroupInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupInfo extends AbstractInfoResult implements GroupInfo {

    private Integer level;
    private Integer openType;
    private String type;
    private Integer typeId;
    private String[] adminList;
    private String board;
    private Long createTime;
    private String simpleIntro;
    private String completeIntro;
    private Integer maxMember;
    private Integer memberNum;
    private String name;
    private String ownerQQ;
    private String code;
    private Map<String, String> levelNames;
    private Map<String, String> adminNickList;
    private String pos;
    private Integer searchType;
    private String[] tags;

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String[] getAdminList() {
        return adminList;
    }

    public void setAdminList(String[] adminList) {
        this.adminList = adminList;
    }

    @Override
    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getSimpleIntro() {
        return simpleIntro;
    }

    public void setSimpleIntro(String simpleIntro) {
        this.simpleIntro = simpleIntro;
    }

    @Override
    public String getCompleteIntro() {
        return completeIntro;
    }

    public void setCompleteIntro(String completeIntro) {
        this.completeIntro = completeIntro;
    }

    @Override
    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    @Override
    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOwnerQQ() {
        return ownerQQ;
    }

    public void setOwnerQQ(String ownerQQ) {
        this.ownerQQ = ownerQQ;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Map<String, String> getLevelNames() {
        return levelNames;
    }

    public void setLevelNames(Map<String, String> levelNames) {
        this.levelNames = levelNames;
    }

    @Override
    public Map<String, String> getAdminNickList() {
        return adminNickList;
    }

    public void setAdminNickList(Map<String, String> adminNickList) {
        this.adminNickList = adminNickList;
    }

    @Override
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "level=" + getLevel() +
                ", openType=" + getOpenType() +
                ", type='" + getType() + '\'' +
                ", typeId=" + getTypeId() +
                ", adminList=" + Arrays.toString(getAdminList()) +
                ", board='" + getBoard() + '\'' +
                ", createTime=" + getCreateTime() +
                ", simpleIntro='" + getSimpleIntro() + '\'' +
                ", completeIntro='" + getCompleteIntro() + '\'' +
                ", maxMember=" + getMaxMember() +
                ", memberNum=" + getMemberNum() +
                ", name='" + getName() + '\'' +
                ", ownerQQ='" + getOwnerQQ() + '\'' +
                ", code='" + getCode() + '\'' +
                ", levelNames=" + getLevelNames() +
                ", adminNickList=" + getAdminNickList() +
                ", pos='" + getPos() + '\'' +
                ", searchType=" + getSearchType() +
                ", tags=" + Arrays.toString(getTags()) +
                "} " + super.toString();
    }
}
