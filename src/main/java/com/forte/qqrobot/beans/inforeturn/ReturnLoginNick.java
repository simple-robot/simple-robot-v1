package com.forte.qqrobot.beans.inforeturn;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 25302,获取登录昵称
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 11:18
 * @since JDK1.8
 **/
public class ReturnLoginNick implements InfoReturn {

    /** 返回编码，应该是25302 */
    @JSONField(name = "return")
    private Integer returnCode;

    /*
    返回json串字段：
    error, act, return,LoginNick
     */
    /** 错误码 */
    private Integer error;
    /** 登录昵称 */
    private String LoginNick;


    public Integer getReturnCode() {
        return returnCode;
    }

    public Integer getReturn() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getLoginNick() {
        return LoginNick;
    }

    public void setLoginNick(String loginNick) {
        LoginNick = loginNick;
    }
}
