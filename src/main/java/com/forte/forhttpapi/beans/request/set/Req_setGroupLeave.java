package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * 「置群退出」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:55
 * @since JDK1.8
 **/
public class Req_setGroupLeave implements ReqBean {

    private final String fun = "setGroupLeave";

    /** 群号 */
    private String group;
    /** 操作类型，true/解散本群(群主) false/退出本群(管理、群成员) */
    private Boolean disband;


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

    public Boolean getDisband() {
        return disband;
    }

    public void setDisband(Boolean disband) {
        this.disband = disband;
    }
}
