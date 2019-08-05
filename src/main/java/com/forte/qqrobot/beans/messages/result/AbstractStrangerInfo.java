package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * @see StrangerInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractStrangerInfo extends AbstractInfoResult implements StrangerInfo {

    private String qq;
    private SexType sex;
    private Integer age;
    private String headUrl;
    private Integer level;
    private String name;

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
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    @Override
    public String headUrl(){
        return getHeadUrl();
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StrangerInfo{" +
                "qq='" + getQQ() + '\'' +
                ", sex=" + getSex() +
                ", age=" + getAge() +
                ", headUrl='" + headUrl() + '\'' +
                ", level=" + getLevel() +
                ", name='" + getName() + '\'' +
                "} " + super.toString();
    }
}
