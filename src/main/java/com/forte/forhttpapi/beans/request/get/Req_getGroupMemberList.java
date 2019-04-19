package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getGroupMemberList;

/**
 * 「取群成员列表」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:57
 * @since JDK1.8
 **/
public class Req_getGroupMemberList implements ReqGetBean<Resp_getGroupMemberList> {

    private final String fun = "getGroupMemberList";
    /** 群号 */
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

    @Override
    public Class<Resp_getGroupMemberList> getResponseType() {
        return Resp_getGroupMemberList.class;
    }
}
