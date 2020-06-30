/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractGroupHomework.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;

/**
 * GroupHomework对应抽象类
 * @see GroupHomework
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupHomework extends AbstractResultInner implements GroupHomework {

    private String courseId;

    private String courseName;

    private String coursePic;

    private String id;

    private String title;

    private String type;

    private String icon;

    private Boolean needFeedBack;

    private String annoNick;

    private String annoQQ;

    private String status;

    private Long time;

    @Override
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public Boolean isNeedFeedBack() {
        return needFeedBack;
    }

    public void setNeedFeedBack(Boolean needFeedBack) {
        this.needFeedBack = needFeedBack;
    }

    @Override
    public String getAnnoNick() {
        return annoNick;
    }

    public void setAnnoNick(String annoNick) {
        this.annoNick = annoNick;
    }

    @Override
    public String getAnnoQQ() {
        return annoQQ;
    }

    public void setAnnoQQ(String annoQQ) {
        this.annoQQ = annoQQ;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GroupHomework{" +
                "courseId='" + getCourseId() + '\'' +
                ", courseName='" + getCourseName() + '\'' +
                ", coursePic='" + getCoursePic() + '\'' +
                ", id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", type='" + getType() + '\'' +
                ", icon='" + getIcon() + '\'' +
                ", needFeedBack=" + isNeedFeedBack() +
                ", annoNick='" + getAnnoNick() + '\'' +
                ", annoQQ='" + getAnnoQQ() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", time=" + getTime() +
                "} " + super.toString();
    }
}