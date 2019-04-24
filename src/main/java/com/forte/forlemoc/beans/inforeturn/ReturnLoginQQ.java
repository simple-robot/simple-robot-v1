package com.forte.forlemoc.beans.inforeturn;

import com.alibaba.fastjson.annotation.JSONField;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;

/**
 * 登录的QQ号
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 11:30
 * @since JDK1.8
 **/
public class ReturnLoginQQ implements LoginQQInfo, InfoReturn {

    /** 返回编码,应该是25301 */
    @JSONField(name = "return")
    private Integer returnCode;

    /*
        返回json串字段：
        error, act, return,LoginQQ
     */
    /** 错误编码 */
    private Integer error;

    /** 应该是登录的qq号 */
    private String LoginQQ;

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

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getLoginQQ() {
        return LoginQQ;
    }

    public void setLoginQQ(String loginQQ) {
        LoginQQ = loginQQ;
    }

    @Override
    public String toString() {
        return "ReturnLoginQQ{" +
                "returnCode=" + returnCode +
                ", error=" + error +
                ", LoginQQ='" + LoginQQ + '\'' +
                '}';
    }

    /**
     * 昵称
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * QQ号
     */
    @Override
    public String getQQ() {
        return LoginQQ;
    }

    /**
     * 头像地址
     */
    @Override
    public String getHeadUrl() {
        return null;
    }

    /**
     * 等级
     */
    @Override
    public Integer getLevel() {
        return null;
    }
}
