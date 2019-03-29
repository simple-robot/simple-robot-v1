package com.forte.qqrobot.beans.HttpApi.request.set;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;

/**
 * 「置匿名群员禁言」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:44
 * @since JDK1.8
 **/
public class Req_setGroupAnonymousBan implements ReqBanBean {

    private final String fun = "setGroupAnonymousBan";

    /** 群号 */
    private String group;
    /** 匿名标识，即提交的参数fromAnonymous */
    private String anonymous;
    /** 禁言时间，单位：秒，不支持解禁 */
    private Long time;


    @Override
    public String getFun() {
        return fun;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time > MAX_BAN_TIME ? MAX_BAN_TIME : (time < MIN_BAN_TIME ? MIN_BAN_TIME : time);
    }
}
