package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * 「置群匿名设置」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:43
 * @since JDK1.8
 **/
public class Req_setGroupAnonymous implements ReqBean {

    private final String fun = "setGroupAnonymous";

    /** 群号 */
    private String group;
    /** 操作类型，True/允许匿名聊天，False/禁止匿名聊天 */
    private Boolean open;


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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
