package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;

/**
 * GroupLink的抽象类
 * @see GroupLink
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupLink extends AbstractResultInner implements GroupLink {

    private String url;

    private String picUrl;

    private Long time;

    private String title;

    private String qq;

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public String toString() {
        return "GroupLink{" +
                "url='" + getUrl() + '\'' +
                ", picUrl='" + getPicUrl() + '\'' +
                ", time=" + getTime() +
                ", title='" + getTitle() + '\'' +
                ", qq='" + getQQ() + '\'' +
                "} " + super.toString();
    }
}
