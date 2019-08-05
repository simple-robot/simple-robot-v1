package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;

/**
 * Share对应的抽象类
 * @see Share
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AbstractShare extends AbstractResultInner implements Share {

    private String busid;

    private Long createTime;

    private Long modiflyTime;

    private Integer DLTimes;

    private String name;

    private String filePath;

    private Long size;

    private String localName;

    private String nick;

    private String qq;

    @Override
    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getModiflyTime() {
        return modiflyTime;
    }

    public void setModiflyTime(Long modiflyTime) {
        this.modiflyTime = modiflyTime;
    }

    @Override
    public Integer getDLTimes() {
        return DLTimes;
    }

    public void setDLTimes(Integer DLTimes) {
        this.DLTimes = DLTimes;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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
        return "Share{" +
                "busid='" + getBusid() + '\'' +
                ", createTime=" + getCreateTime() +
                ", modiflyTime=" + getModiflyTime() +
                ", DLTimes=" + getDLTimes() +
                ", name='" + getName() + '\'' +
                ", filePath='" + getFilePath() + '\'' +
                ", size=" + getSize() +
                ", localName='" + getLocalName() + '\'' +
                ", nick='" + getNick() + '\'' +
                ", qq='" + getQQ() + '\'' +
                "} " + super.toString();
    }
}
