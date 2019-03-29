package com.forte.qqrobot.beans.HttpApi.request.set;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;

/**
 * 「置讨论组退出」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:35
 * @since JDK1.8
 **/
public class Req_setDiscussLeave implements ReqBean {

    private final String fun = "setDiscussLeave";

    /** 讨论组号 */
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
