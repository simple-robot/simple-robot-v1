package com.forte.qqrobot.beans.inforeturn;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 25304,以json串方式返回陌生人信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 11:37
 * @since JDK1.8
 **/
public class ReturnStranger implements InfoReturn {

    /** 返回编码，应该是25304 */
    @JSONField(name = "return")
    private Integer returnCode;

    /*
     返回json串字段：
        error, act, return,QQID,nick,sex,age
     */
    /** 错误编码 */
    private Integer error;
    /** 陌生人的qq号 */
    private String QQID;
    /** 昵称 */
    private String nick;
    /** 性别 */
    private Integer sex;
    /** 年龄 */
    private Integer age;

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getReturn() {
        return returnCode;
    }

    public void setReturn(Integer returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ReturnStranger{" +
                "returnCode=" + returnCode +
                ", error=" + error +
                ", QQID='" + QQID + '\'' +
                ", nick='" + nick + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }
}
