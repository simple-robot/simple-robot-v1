package com.forte.qqrobot.beans.HttpApi.request.set;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;

/**
 * 「置群签到」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:00
 * @since JDK1.8
 **/
public class Req_setGroupSign implements ReqBean {

    private final String fun = "setGroupSign";

    /** 群号，值为0时表示签到所有群 */
    private String group;

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
}
