package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;

/**
 * GroupNote 对应抽象类
 * @see GroupNote
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupNote extends AbstractResultInner implements GroupNote {

    private String id;

    private String msg;

    private String faceMsg;

    private String title;

    private Long time;

    private Integer readNum;

    private Boolean showEditCard;

    private String qq;

    private String typeId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getFaceMsg() {
        return faceMsg;
    }

    public void setFaceMsg(String faceMsg) {
        this.faceMsg = faceMsg;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Boolean isShowEditCard() {
        return showEditCard;
    }

    public void setShowEditCard(Boolean showEditCard) {
        this.showEditCard = showEditCard;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQQ() {
        return qq;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }

    @Override
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "GroupNote{" +
                "id='" + getId() + '\'' +
                ", msg='" + getMsg() + '\'' +
                ", faceMsg='" + getFaceMsg() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", time=" + getTime() +
                ", readNum=" + getReadNum() +
                ", showEditCard=" + isShowEditCard() +
                ", qq='" + getQQ() + '\'' +
                ", typeId='" + getTypeId() + '\'' +
                "} " + super.toString();
    }
}
